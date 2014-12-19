package authentication.config;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import play.api.Play;
import play.libs.Json;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.typesafe.config.ConfigObject;

public class GoogleAuthConfig {

	public final GoogleClientSecrets clientSecrets;
	public final String CLIENT_ID;
	public final String CLIENT_SECRET;
	public final JacksonFactory JSON_FACTORY;
	public final HttpTransport TRANSPORT = new NetHttpTransport();

	public GoogleAuthConfig() {
		try {
			JSON_FACTORY = new JacksonFactory();
			clientSecrets = readClientSecretsConfig();
			CLIENT_ID = clientSecrets.getWeb().getClientId();
			CLIENT_SECRET = clientSecrets.getWeb().getClientSecret();
		} catch (IOException e) {
			throw new Error("No client_secrets.json found", e);
		}
	}

	private GoogleClientSecrets readClientSecretsConfig() throws IOException {
		String clientJsonString = getGoogleClientConfig();
		Reader reader = new StringReader(clientJsonString);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);
		return clientSecrets;
	}

	private static String getGoogleClientConfig() {
		ConfigObject clientJson = Play.current().configuration().getObject("client_json").get();
		String clientJsonString = Json.toJson(clientJson.unwrapped()).toString();
		return clientJsonString;
	}

}
