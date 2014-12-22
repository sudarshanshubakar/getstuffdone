package controllers.sprint;

import java.util.List;

import javax.inject.Inject;

import org.mongodb.morphia.query.Query;

import models.Sprint;
import models.Task;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import app.data.dao.SprintDAO;
import app.data.dao.TaskDAO;
import authentication.authenticator.GoogleAuthenticator;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class SprintController extends Controller {

	@Inject
	SprintDAO sprintDAO;

	@Inject
	TaskDAO taskDAO;
	
	@Security.Authenticated(GoogleAuthenticator.class)
	public Result create() {
		Form<Sprint> form = Form.form(Sprint.class).bindFromRequest();
		System.out.println("sprint form == " + form.data().toString());
		Sprint sprint = form.get();
		// sprint.setSprintId(getSequence());

		sprintDAO.save(sprint);

		ObjectNode result = Json.newObject();
		result.put("message", "Sprint " + sprint.name + " created successfully");
		result.put("sprintId", sprint.getEntityId());
		return ok(result);
	}

	@Security.Authenticated(GoogleAuthenticator.class)
	public Result findAll() {
		List<Sprint> sprints = sprintDAO.find().asList();
		return ok(Json.toJson(sprints));

	}

	@Security.Authenticated(GoogleAuthenticator.class)
	public Result find(Long id) {
		Sprint result = sprintDAO.findOne("entityId", id);
		if (result != null)
			return ok(Json.toJson(result));
		else {
			return notFound();
		}
	}
	
	@Security.Authenticated(GoogleAuthenticator.class)
	public Result sprintTasks(Long id) {
		Sprint sprintFromDb = sprintDAO.findOne("entityId", id);
		
		if (sprintFromDb.tasks != null)
			return ok(Json.toJson(sprintFromDb));
		else {
			return notFound();
		}
	}
	
	@Security.Authenticated(GoogleAuthenticator.class)
	public Result addSprintTasks(Long id) {
		Sprint sprintFromDb = sprintDAO.findOne("entityId", id);
		Form<Sprint> form = Form.form(Sprint.class).bindFromRequest();
		System.out.println("sprint form == " + form.data().toString());
		Sprint inputSprint = form.get();
		if (sprintFromDb.tasks != null) {
			sprintFromDb.tasks.addAll(inputSprint.tasks);
		} else {
			sprintFromDb.tasks = inputSprint.tasks;
		}
		sprintDAO.save(sprintFromDb);
		ObjectNode result = Json.newObject();
		result.put("message", "Tasks added to Sprint \""+sprintFromDb.name+"\" successfully.");
		result.put("sprintId", sprintFromDb.getEntityId());
		return ok(result);
	}
}
