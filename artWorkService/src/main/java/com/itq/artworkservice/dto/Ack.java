package com.itq.artworkservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ack {

	@JsonProperty("code")
	private int code;

	@JsonProperty("description")
	private String description;

	public Ack() {
	}

	public Ack(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
