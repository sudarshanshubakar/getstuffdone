package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Task extends SequencedModel {

	@Id
	private ObjectId id;
	public String name;
	public String description;
	public int priority;
	public String status = "";
	public Task() {
	}
	
}
