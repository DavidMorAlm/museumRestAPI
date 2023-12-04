package com.itq.hallservice.service;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itq.hallservice.business.HallBussiness;
import com.itq.hallservice.dto.Hall;
import com.itq.hallservice.exception.*;

@RestController
public class HallServiceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HallServiceController.class);

	@Autowired
	private HallBussiness hallBussiness;


	@GetMapping("/halls")
	public ResponseEntity<List<Hall>> getAllHalls() throws Exception {

		List<Hall> halls = hallBussiness.getAllHalls();
		return new ResponseEntity<List<Hall>>(halls, HttpStatus.OK);

	}

	@GetMapping("/halls/{idHall}")
	public ResponseEntity<Hall> getHallById(@PathVariable(value = "idHall") int idHall) throws Exception {

		try {
			
			Hall hall = hallBussiness.getHallById(idHall);
			return new ResponseEntity<Hall>(hall, HttpStatus.OK);

		} catch (HallNotFoundException e) {

			return new ResponseEntity<Hall>(HttpStatus.NOT_FOUND);

		}

	}

}
