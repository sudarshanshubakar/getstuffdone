package models;

public class SequencedModel {
	private Long entityId = (long) 0;
	public void setEntityId(long id) {
		if(entityId != null)
			entityId = id;
	}
	public long getEntityId() {
		return entityId;
	}
}
