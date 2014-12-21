package controllers.task;

import java.util.List;

import javax.inject.Inject;

import models.Task;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import app.data.dao.TaskDAO;
import authentication.authenticator.GoogleAuthenticator;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class TaskController extends Controller {

	private enum OP {
		UPDATED("updated"), CREATED("created");
		private String opString;

		private OP(String opString) {
			this.opString = opString;
		}
	}

	@Inject
	TaskDAO taskDAO;

	@Security.Authenticated(GoogleAuthenticator.class)
	public Result create() {
		Form<Task> form = Form.form(Task.class).bindFromRequest();
		System.out.println("task form == " + form.data().toString());
		System.out.println("task form errors == " + form.errorsAsJson());
		Task task = form.get();
		task.status = "open";
		taskDAO.save(task);
		return result(task, OP.CREATED);
	}

	@Security.Authenticated(GoogleAuthenticator.class)
	public Result update(Long id) {
		Form<Task> form = Form.form(Task.class).bindFromRequest();
		Task task = form.get();
		Task savedTask = taskDAO.findOne("entityId", id);
		BeanUtils.copyProperties(task, savedTask);
		taskDAO.save(savedTask);
		return result(task, OP.UPDATED);
	}

	private Result result(Task task, OP op) {
		ObjectNode result = Json.newObject();
		result.put("message", "Task " + task.name + " " + op.opString + " successfully");
		result.put("taskId", task.getEntityId());
		return ok(result);
	}

	@Security.Authenticated(GoogleAuthenticator.class)
	public Result findOpenTasks() {
		Query<Task> query = taskDAO.createQuery();
		List<Task> tasks = taskDAO.find(query.field("status").equal("open")).asList();
		return ok(Json.toJson(tasks));
	}

}
