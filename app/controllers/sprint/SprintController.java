package controllers.sprint;

import java.net.UnknownHostException;

import javax.inject.Inject;

import models.Sprint;

import org.mongodb.morphia.Key;

import app.data.dao.SprintDAO;
import authentication.authenticator.GoogleAuthenticator;

import com.fasterxml.jackson.databind.node.ObjectNode;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class SprintController extends Controller {
	
	@Inject
	SprintDAO sprintDAO;
	
	@Security.Authenticated(GoogleAuthenticator.class)
	public Result create() throws UnknownHostException {
	    Form<Sprint> form = Form.form(Sprint.class).bindFromRequest();
	    System.out.println("sprint form == "+form.data().toString());
	    Sprint sprint = form.get();

	    Key<Sprint> savedSprint = sprintDAO.save(sprint);
	    
	    ObjectNode result = Json.newObject();
	    result.put("message", "Sprint "+sprint.name+" created successfylly");
	    result.put("id", savedSprint.getId().toString());
	    return ok(result);
	}
}
