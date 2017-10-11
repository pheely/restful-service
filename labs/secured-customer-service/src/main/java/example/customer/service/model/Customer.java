package example.customer.service.model;

import lombok.Data;

public @Data class Customer {
	private long id;
	private String name;
	private int age;
	private double salary;

	public Customer(){
		id=0;
	}
	
	public Customer(long id, String name, int age, double salary){
		this.id = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
	}
}
