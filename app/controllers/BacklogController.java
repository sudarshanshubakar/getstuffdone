package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Task;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import authentication.authenticator.GoogleAuthenticator;

public class BacklogController extends Controller {
	private static void setResponse() {
		response().setContentType("application/json");
		response().setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
	}
	
	@Security.Authenticated(GoogleAuthenticator.class)
	public static Result findOpen() {
		setResponse();
		Task task1 = new Task((long)1, "My Proj client side");
		Task task2 = new Task((long)2, "My Proj server side");
		Task task3 = new Task((long)3, "My Proj Mongo schema");
		Task task4 = new Task((long)4, "My Proj authentication");
		Task task5 = new Task((long)5, "My Proj cleanup");
		Task task6 = new Task((long)6, "Pay Electricity bill");
		Task task7 = new Task((long)7, "Go for a walk");
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);
		tasks.add(task4);
		tasks.add(task5);
		tasks.add(task6);
		tasks.add(task7);
		
		return ok(Json.toJson(tasks));
		
	}
	
}
