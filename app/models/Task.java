package models;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Task extends SequencedModel {

	public String name;
	public String description;
	public int priority;
	public String status = "";
	public Long sprintId;
	public boolean assigned = false;
	public Task() {
	}
	
}
