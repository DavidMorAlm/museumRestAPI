package com.itq.artworkservice.dto;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class ArtWorkUpdate {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idArtWork;

	@Column(name = "idHall")
	private int idHall;

	@Column(name = "availability")
	private boolean availability;

	public ArtWorkUpdate() {
	}

	public ArtWorkUpdate(int idArtWork, int idHall, boolean availability) {
		this.idArtWork = idArtWork;
		this.idHall = idHall;
		this.availability = availability;
	}

	public int getIdArtWork() {
		return idArtWork;
	}

	public void setIdArtWork(int idArtWork) {
		this.idArtWork = idArtWork;
	}

	public int getIdHall() {
		return idHall;
	}

	public void setIdHall(int idHall) {
		this.idHall = idHall;
	}

	public boolean getAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

}
