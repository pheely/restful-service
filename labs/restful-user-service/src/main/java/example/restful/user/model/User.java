package example.restful.user.model;

import lombok.Data;

public @Data class User {
	private long id;
	private String name;
	private int age;
	private double salary;

	public User(){
		id=0;
	}
	
	public User(long id, String name, int age, double salary){
		this.id = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
	}
}
