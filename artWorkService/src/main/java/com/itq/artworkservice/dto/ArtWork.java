package com.itq.artworkservice.dto;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "artwork")
public class ArtWork {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull (message = "idArtWork cannot be null")
	@Column(name = "idArtWork")
	private int idArtWork;

	@NotNull
	@Column(name = "idHall")
	private int idHall;

	@NotNull
	@Size(min = 0, max = 70)
	@Column(name = "name")
	private String name;

	@NotNull
	@Size(min = 0, max = 30, message = "author size must be between 0 and 30")
	@Column(name = "author")
	private String author;

	@NotNull
	@Size(min = 0, max = 50)
	@Column(name = "technique")
	private String technique;

	@NotNull
	@Size(min = 0, max = 100)
	@Column(name = "description")
	private String description;

	@NotNull
	@Size(min = 0, max = 15)
	@Column(name = "year")
	private String year;

	@NotNull
	@Column(name = "permanent")
	private boolean permanent;

	@NotNull
	@Column(name = "availability")
	private boolean availability;

	public ArtWork() {
	}

	public ArtWork(int idArtWork, int idHall, String name, String author, String technique, String description,
			String year, boolean permanent, boolean availability) {
		this.idArtWork = idArtWork;
		this.idHall = idHall;
		this.name = name;
		this.author = author;
		this.technique = technique;
		this.description = description;
		this.year = year;
		this.permanent = permanent;
		this.availability = availability;
	}

	public ArtWork(ArtWorkUpdate artWork) {
		this.idArtWork = artWork.getIdArtWork();
		this.idHall = artWork.getIdHall();
	}

	public ArtWork(ArtWorkInsert newArtWork, int idArtWork){

		this.idArtWork = idArtWork;
		this.idHall = newArtWork.getIdHall();
		this.name = newArtWork.getName();
		this.author = newArtWork.getAuthor();
		this.technique = newArtWork.getTechnique();
		this.description = newArtWork.getDescription();
		this.year = newArtWork.getYear();
		this.permanent = newArtWork.getPermanent();
		this.availability = newArtWork.getAvailability();
		
	}

	public int getIdArtWork() {
		return idArtWork;
	}

	public void setIdArtWork(int idArtWork) {
		this.idArtWork = idArtWork;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTechnique() {
		return technique;
	}

	public void setTechnique(String technique) {
		this.technique = technique;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public boolean getPermanent() {
		return permanent;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	public boolean getAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public int getIdHall() {
		return idHall;
	}

	public void setIdHall(int idHall) {
		this.idHall = idHall;
	}

}
