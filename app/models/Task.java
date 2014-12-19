package models;

public class Task {

	private final Long id;
	public String name;
	public String description;
	public int priority;
	public String status;
	public Task(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	
	
}
