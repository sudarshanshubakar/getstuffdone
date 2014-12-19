package controllers;

import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import models.Sprint;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;

import play.GlobalSettings;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import app.data.dao.SprintDAO;
import authentication.authenticator.GoogleAuthenticator;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.MongoClient;

public class SprintController extends Controller{

	
	@Security.Authenticated(GoogleAuthenticator.class)
	public static Result findAll() throws UnknownHostException {
		List<Sprint> sprints = getSprints();
		return ok(Json.toJson(sprints));
		
	}


	private static List<Sprint> getSprints() throws UnknownHostException {
		
	    MongoClient mongo = new MongoClient("localhost");
	    Morphia morphia = new Morphia();
	    Datastore datastore = morphia.createDatastore(mongo, "get_work_done");
		return datastore.find(Sprint.class).asList();
//		Sprint sp1 = new Sprint(1, "Build My_Proj", "Sprint to build the My_Proj application. The idea is to start using the application quickly.");
//		sp1.startDate = "Dec 6 2014";
//		sp1.endDate = "Dec 19 2014";
//		Sprint sp2 = new Sprint(2, "ACC Milestone 1", "Sprint to complete the ACC milestone 1. This involves completing the Project plan, Design documents and Test planning document.");
//		Sprint sp3 = new Sprint(3, "Pay Bills", "Just do it :(");
//		List<Sprint> sprints = new ArrayList<Sprint>();
//		sprints.add(sp1);
//		sprints.add(sp2);
//		sprints.add(sp3);
//		return sprints;
	}
	
	@Security.Authenticated(GoogleAuthenticator.class)
	public static Result find(Long id) throws UnknownHostException {
		Sprint input = new Sprint(id, null);
		List<Sprint> sprints = getSprints();
		Sprint output = null;
		if(sprints.contains(input)) {
			int index = sprints.indexOf(input);
			output = sprints.get(index);
		}
		if(output != null)
			return ok(Json.toJson(output));
		else {
			return notFound();
		}
	}
	
	public static Result options() {
		response().setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		response().setHeader(ACCESS_CONTROL_ALLOW_METHODS, "POST,OPTIONS,GET");
		response().setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type");
		return ok();
	}
	
//	@Security.Authenticated(GoogleAuthenticator.class)
//	public Result create() throws UnknownHostException {
//	    Form<Sprint> form = Form.form(Sprint.class).bindFromRequest();
//	    Sprint sprint = form.get();
////	    ######################
////	    MongoClient mongo = new MongoClient("localhost");
////	    Morphia morphia = new Morphia();
////	    Datastore datastore = morphia.createDatastore(mongo, "get_work_done");
////	    morphia.map(Sprint.class);
//	    Key<Sprint> savedSprint = sprintDAO.save(sprint);
//	    
//	    
////	    #####################
//	    
//	    
//	    
//	    ObjectNode result = Json.newObject();
//	    result.put("message", "Sprint "+sprint.name+" created successfylly");
//	    result.put("id", savedSprint.getId().toString());
//	    return ok(result);
//	}
	
}
