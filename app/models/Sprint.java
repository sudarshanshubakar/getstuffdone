package models;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Sprint extends SequencedModel {
	public String name;
	public String description;
	
	public String startDate;
	public String endDate;
	
	public List<Task> tasks;
	
	public Sprint() {
		super();
	}
}
