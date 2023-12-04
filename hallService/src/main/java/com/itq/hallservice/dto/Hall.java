package com.itq.hallservice.dto;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "hall")
public class Hall {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "idHall")
	private int idHall;

	@NotNull
	@Column(name = "idMuseum")
	private int idMuseum;

	@NotNull
	@Size(min = 0, max = 50)
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "availability")
	private boolean availability;

	public Hall() {
	}

	public Hall(int idHall, int idMuseum, String name, boolean availability) {
		this.idHall = idHall;
		this.idMuseum = idMuseum;
		this.name = name;
		this.availability = availability;
	}

	public int getIdHall() {
		return idHall;
	}

	public void setIdHall(int idHall) {
		this.idHall = idHall;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public int getIdMuseum() {
		return idMuseum;
	}

	public void setIdMuseum(int idMuseum) {
		this.idMuseum = idMuseum;
	}

}
