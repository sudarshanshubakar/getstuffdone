package authentication.authenticator;

import java.io.IOException;

import play.mvc.Http;
import play.mvc.Security.Authenticator;
import authentication.config.GoogleAuthConfig;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;

public class GoogleAuthenticator extends Authenticator {

	private GoogleAuthConfig gac = new GoogleAuthConfig();

	@Override
	public String getUsername(Http.Context context) {
		String token = getTokenFromHeader(context);
		System.out.println("X-AUTH-TOKEN == "+token);
		String username = null;
		if (token != null) {
			try {
				GoogleCredential credential = new GoogleCredential.Builder().setJsonFactory(gac.JSON_FACTORY)
						.setTransport(gac.TRANSPORT).setClientSecrets(gac.CLIENT_ID, gac.CLIENT_SECRET).build()
						.setFromTokenResponse(gac.JSON_FACTORY.fromString(token, GoogleTokenResponse.class));
				Plus service = new Plus.Builder(gac.TRANSPORT, gac.JSON_FACTORY, credential).setApplicationName(
						"My Projects").build();
				Person person = service.people().get("me").execute();
				username = person.getEmails().get(0).getValue();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return username;
	}

	private String getTokenFromHeader(Http.Context ctx) {
		String authTokenHeaderValues = ctx.session().get("X-AUTH-TOKEN");
		System.out.println("X-AUTH-TOKEN" + authTokenHeaderValues);
		if (authTokenHeaderValues != null) {
			System.out.println("found authTokenHeaderValues");
			return authTokenHeaderValues;
		}
		return null;
	}
}
