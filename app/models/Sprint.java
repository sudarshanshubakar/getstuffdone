package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Sprint {
	@Id private ObjectId Id;
	public String name;
	public String description;
	
	public String startDate;
	public String endDate;
	
	public Sprint() {
//		Id = System.currentTimeMillis();
	}
	
	public Sprint(long id, String name) {
//		Id = id;
		this.name = name;
	}
	
	public Sprint(long id, String name, String description) {
//		Id = id;
		this.name = name;
		this.description = description;
	}

	public ObjectId getId() {
		return Id;
	}

	
}
