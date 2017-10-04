package example.restful.model;

import java.util.List;

import lombok.Data;

public @Data class Course {
	private String id;
	private String name;
	private String description;
	private List<String> steps;

	// Needed by Caused by: com.fasterxml.jackson.databind.JsonMappingException:
	// Can not construct instance of com.in28minutes.springboot.model.Course:
	// no suitable constructor found, can not deserialize from Object value
	// (missing default constructor or creator, or perhaps need to add/enable
	// type information?)
	public Course() {

	}

	public Course(String id, String name, String description, List<String> steps) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.steps = steps;
	}
}
