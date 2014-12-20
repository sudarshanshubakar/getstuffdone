package controllers;

import javax.inject.Inject;

import models.Task;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import app.data.dao.TaskDAO;
import authentication.authenticator.GoogleAuthenticator;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class TaskController extends Controller {
	
	@Inject
	TaskDAO taskDAO;
	
	private static void setResponse() {
		response().setContentType("application/json");
		response().setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
	}
	
	
	@Security.Authenticated(GoogleAuthenticator.class)
	public Result create() {
		Form<Task> form = Form.form(Task.class).bindFromRequest();
		System.out.println("task form == " + form.data().toString());
		System.out.println("task form errors == " + form.errorsAsJson());
		Task task = form.get();

		taskDAO.save(task);

		ObjectNode result = Json.newObject();
		result.put("message", "Task " + task.name + " created successfully");
		result.put("taskId", task.getEntityId());
		return ok(result);
	}
	
	@Security.Authenticated(GoogleAuthenticator.class)
	public static Result findOpenTasks() {
		setResponse();

		
		
		
		
//		Task task1 = new Task((long)1, "My Proj client side");
//		Task task2 = new Task((long)2, "My Proj server side");
//		Task task3 = new Task((long)3, "My Proj Mongo schema");
//		Task task4 = new Task((long)4, "My Proj authentication");
//		Task task5 = new Task((long)5, "My Proj cleanup");
//		Task task6 = new Task((long)6, "Pay Electricity bill");
//		Task task7 = new Task((long)7, "Go for a walk");
//		List<Task> tasks = new ArrayList<Task>();
//		tasks.add(task1);
//		tasks.add(task2);
//		tasks.add(task3);
//		tasks.add(task4);
//		tasks.add(task5);
//		tasks.add(task6);
//		tasks.add(task7);
//		
//		return ok(Json.toJson(tasks));
		return ok();
	}
	
}
