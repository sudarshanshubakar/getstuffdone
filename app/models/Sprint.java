package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Sprint extends SequencedModel {
	@Id private ObjectId id;
	
	public String name;
	public String description;
	
	public String startDate;
	public String endDate;
	
	public Long[] taskIds;
	
	public Sprint() {
		super();
	}
	
	public ObjectId getId() {
		return id;
	}

}
