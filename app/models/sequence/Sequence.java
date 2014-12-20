package models.sequence;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Sequence {
	
	@Id private ObjectId Id;

	public long current;
	
	public Sequence increment() {
		current++;
		return this;
	}
}
