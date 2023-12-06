package com.itq.userservice.dto;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "user")
public class UserInsert {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull (message = "idUser cannot be null")
	@Column(name = "idUser")
	private int idUser;
	
	@NotNull
	@Column(name = "idMuseum")
	private int idMuseum;
	
	@NotNull
	@Size(min = 0, max = 25)
	@Column(name = "name")
	private String name;
	
	@NotNull
	@Size(min = 0, max = 25)
	@Column(name = "lastName")
	private String lastName;
	
	@NotNull
	@Size(min = 10, max = 15)
	@Column(name = "phoneNumber")
	private String phoneNumber;
	
	@NotNull
	@Min(15)
	@Max(110)
	@Column(name = "age")
	private int age;
	
	@NotNull
	@Size(min = 0, max = 50)
	@Column(name = "email")
	private String email;
	
	@NotNull
	@Column(name = "connected")
	private boolean connected;
	
	public UserInsert() {}
	
	public UserInsert(int idMuseum, String name, String lastName, String phoneNumber, int age, String email, boolean connected) {
		this.idMuseum = idMuseum;
		this.name = name;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.age = age;
		this.email = email;
		this.connected = connected;
	}
	

	public int getIdMuseum() {
		return idMuseum;
	}
	
	public void setIdMuseum(int idMuseum) {
		this.idMuseum = idMuseum;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isConnected() {
		return connected;
	}
	
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	
}

