package controllers.sprint;

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

import com.fasterxml.jackson.databind.node.ObjectNode;

public class SprintController extends Controller {

	@Inject
	SprintDAO sprintDAO;

	@Security.Authenticated(GoogleAuthenticator.class)
	public Result create() {
		Form<Sprint> form = Form.form(Sprint.class).bindFromRequest();
		System.out.println("sprint form == " + form.data().toString());
		Sprint sprint = form.get();
//		sprint.setSprintId(getSequence());

		sprintDAO.save(sprint);

		ObjectNode result = Json.newObject();
		result.put("message", "Sprint " + sprint.name + " created successfully");
		result.put("sprintId", sprint.getEntityId());
		return ok(result);
	}

//	private long getSequence() {
//		List<Sequence> sequences = seqDAO.find().asList();
//		Sequence sequence = null;
//		if (sequences.size() == 0) {
//			sequence = new Sequence();
//			seqDAO.save(sequence);
//		} else {
//			sequence = sequences.get(0);
//		}
//		long id = sequence.current;
//		seqDAO.save(sequence.increment());
//		return id;
//	}

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
}
