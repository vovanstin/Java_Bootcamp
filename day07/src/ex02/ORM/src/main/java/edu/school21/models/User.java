package edu.school21.models;

import edu.school21.annotation.OrmColumn;
import edu.school21.annotation.OrmColumnId;
import edu.school21.annotation.OrmEntity;

@OrmEntity(table = "simple_user")
public class User {
	@OrmColumnId
	private Long id;
	@OrmColumn(name = "first_name", length = 10)
	private String firstName;
	@OrmColumn(name = "last_name", length = 10)
	private String lastName;
	@OrmColumn(name = "age")
	private Integer age;
	
	public User(Long id, String firstName, String lastName, Integer age) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}

	public User() {
	}

	public Long getId() {
		return id;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Integer getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", LastName='" + lastName + '\'' +
				", age=" + age +
				'}';
	}
}
