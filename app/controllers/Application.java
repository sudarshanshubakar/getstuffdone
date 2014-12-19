package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.defaultpage;
import views.html.index;

public class Application extends Controller {
    public static Result index() {
    	response().setContentType("text/html");
        return ok(index.render());
    }
    
    public static Result showDefaultPage() {
    	response().setContentType("text/html");
        return ok(defaultpage.render());
    }
}
