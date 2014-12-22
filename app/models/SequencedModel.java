package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class SequencedModel {
	private Long entityId = (long) 0;
	@Id private ObjectId id;
	public void setEntityId(long id) {
		if(entityId != null)
			entityId = id;
	}
	public long getEntityId() {
		return entityId;
	}
	public ObjectId getId() {
		return id;
	}
}
