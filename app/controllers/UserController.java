package controllers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import play.api.Play;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.RequestBody;
import play.mvc.Result;
import play.mvc.Security;
import authentication.authenticator.GoogleAuthenticator;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.typesafe.config.ConfigObject;

public class UserController extends Controller {
	private static final HttpTransport TRANSPORT = new NetHttpTransport();
	private static final JacksonFactory JSON_FACTORY = new JacksonFactory();
	private static GoogleClientSecrets clientSecrets;
	static {
		try {
			clientSecrets = readClientSecretsConfig();
		} catch (IOException e) {
			throw new Error("No client_secrets.json found", e);
		}
	}
	private static final String CLIENT_ID = clientSecrets.getWeb().getClientId();
	private static final String CLIENT_SECRET = clientSecrets.getWeb().getClientSecret();

	public static Result storeToken() throws IOException {
		response().setContentType("application/json");
		Status sessionStatus = checkSession();
		if (sessionStatus == null) {

			String oneTimeCode = extractOneTimeCode();

			GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(TRANSPORT, JSON_FACTORY,
					CLIENT_ID, CLIENT_SECRET, oneTimeCode, "postmessage").execute();

			Person person = getGooglePersonProfile(oneTimeCode, tokenResponse.toString());
			String domain = person.getDomain();
			System.out.println("DOMAIN == "+domain);
			if("gslab.com".equalsIgnoreCase(domain)) {
				ObjectNode result = formResultNode(person);
	//			System.out.println("person == " + person.toPrettyString());
				session().put("X-AUTH-TOKEN", tokenResponse.toString());
				session().put("email", person.getEmails().get(0).getValue());
				session().put("firstName", person.getName().getGivenName());
				sessionStatus = ok(result);
			} else {
				sessionStatus = unauthorized("Allowed only for gslab.com.");
			}
		} 
		return sessionStatus;

	}
	@Security.Authenticated(GoogleAuthenticator.class)
	public static Result removeToken() throws IOException {
		session().remove("X-AUTH-TOKEN");
		return ok();
	}

	private static ObjectNode formResultNode(Person personProfile) {
		String firstName = personProfile.getName().getGivenName();
		String email = personProfile.getEmails().get(0).getValue();
		ObjectNode result = Json.newObject();
		ObjectNode person = Json.newObject();
		person.put("firstName", firstName);
		person.put("email", email);
		result.put("message", "Signed in "+email+" successfully.");
		result.put("person", person);
		return result;
	}

	private static Person getGooglePersonProfile(String oneTimeCode, String token) throws IOException {

		GoogleCredential credential = new GoogleCredential.Builder().setJsonFactory(JSON_FACTORY)
				.setTransport(TRANSPORT).setClientSecrets(CLIENT_ID, CLIENT_SECRET).build()
				.setFromTokenResponse(JSON_FACTORY.fromString(token, GoogleTokenResponse.class));
		// Create a new authorized API client.
		Plus service = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential).setApplicationName("My Projects").build();
		Person person = service.people().get("me").execute();
		return person;
	}

	private static GoogleClientSecrets readClientSecretsConfig() throws IOException {
		String clientJsonString = getGoogleClientConfig();
		Reader reader = new StringReader(clientJsonString);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);
		return clientSecrets;
	}

	private static String extractOneTimeCode() throws IOException, UnsupportedEncodingException {
		ByteArrayOutputStream reqOutputStream = getContent(request().body());
		String oneTimeCode = new String(reqOutputStream.toByteArray(), "UTF-8");
		return oneTimeCode;
	}

	private static Status checkSession() {
		String tokenData = session("X-AUTH-TOKEN");
		if (tokenData != null) {
			ObjectNode result = Json.newObject();
			ObjectNode person = Json.newObject();
			person.put("firstName", session("firstName"));
			person.put("email", session("email"));
			result.put("message", "Current user is already connected.");
			result.put("person", person);
			
			return ok(result);
		} else {
			return null;
		}
	}

	private static String getGoogleClientConfig() {
		ConfigObject clientJson = Play.current().configuration().getObject("client_json").get();
		String clientJsonString = Json.toJson(clientJson.unwrapped()).toString();
		return clientJsonString;
	}

	/*
	 * Read the content of an InputStream.
	 * 
	 * @param inputStream the InputStream to be read.
	 * 
	 * @return the content of the InputStream as a ByteArrayOutputStream.
	 * 
	 * @throws IOException
	 */
	private static ByteArrayOutputStream getContent(RequestBody input) throws IOException {
		// Read the response into a buffered stream
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.asRaw()
				.asBytes())));
		int readChar;
		while ((readChar = reader.read()) != -1) {
			outputStream.write(readChar);
		}
		reader.close();
		return outputStream;
	}

	public static Result options() {
		response().setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		response().setHeader(ACCESS_CONTROL_ALLOW_METHODS, "POST,OPTIONS,GET");
		response().setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type");
		return ok();
	}
}
