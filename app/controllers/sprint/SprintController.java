package controllers.sprint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import models.Sprint;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import app.data.dao.SprintDAO;
import authentication.authenticator.GoogleAuthenticator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SprintController extends Controller {

	@Inject
	SprintDAO sprintDAO;

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
		Sprint result = sprintDAO.findOne("entityId", id);
		if (result != null)
			return ok(Json.toJson(result));
		else {
			return notFound();
		}
	}
	
	public Result addSprintTasks(Long id) {
		Sprint sprintFromDb = sprintDAO.findOne("entityId", id);
//		Form<Sprint> form = Form.form(Sprint.class).bindFromRequest();
//		System.out.println("sprint form == " + form.data().toString());
//		Sprint inputSprint = form.get();
		System.out.println("request  == "+request().body().asJson().get("taskIds").  .getNodeType());
		Iterator<JsonNode> elements = request().body().asJson().get("taskIds").elements();
		
//		Long[] ids = request().body().asJson().get("taskIds");
//		List<Long> taskIdList = getTasksForSprint(sprintFromDb);
//		taskIdList.addAll(getTasksForSprint(inputSprint));
//		sprintFromDb.taskIds = taskIdList.toArray(new Long[] {});
//		sprintDAO.save(sprintFromDb);
		return ok("Tasks added to Sprint "+sprintFromDb+" successfully.");
	}

	private List<Long> getTasksForSprint(Sprint sprint) {
		List<Long> taskIdList = new ArrayList<Long>();
		if (sprint != null) {
			for (Long taskId : sprint.taskIds) {
				taskIdList.add(taskId);
			}
		}
		return taskIdList;
	}
	
	
}
