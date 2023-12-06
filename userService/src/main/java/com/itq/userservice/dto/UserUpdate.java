package com.itq.userservice.dto;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "user")
public class UserUpdate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull (message = "idUser cannot be null")
	@Column(name = "idUser")
	private int idUser;
	
	@NotNull
	@Size(min = 10, max = 15)
	@Column(name = "phoneNumber")
	private String phoneNumber;
	
	@NotNull
	@Size(min = 0, max = 50)
	@Column(name = "email")
	private String email;

	public UserUpdate() {}
	
	public UserUpdate(int idUser,String phoneNumber, String email) {
		this.idUser = idUser;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}
	
	public int getIdUser() {
		return idUser;
	}
	
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
}
