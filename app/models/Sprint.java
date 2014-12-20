package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Sprint extends SequencedModel {
	@Id private ObjectId id;
	
//	private Long sprintId;
	public String name;
	public String description;
	
	public String startDate;
	public String endDate;
	
	public Sprint() {
//		Id = System.currentTimeMillis();
		super();
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
		return id;
	}

//	public Long getSprintId() {
//		return sprintId;
//	}
//
//	public void setSprintId(Long sprintId) {
//		if (this.sprintId == null)
//			this.sprintId = sprintId;
//	}

}
