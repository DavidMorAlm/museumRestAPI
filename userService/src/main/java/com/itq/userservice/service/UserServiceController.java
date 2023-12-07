package com.itq.userservice.service;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.itq.userservice.business.UserBusiness;
import com.itq.userservice.dto.Ack;
import com.itq.userservice.dto.User;
import com.itq.userservice.dto.UserInsert;
import com.itq.userservice.dto.UserUpdate;
import com.itq.userservice.exception.DuplicatedUserException;
import com.itq.userservice.exception.InvalidRequestException;
import com.itq.userservice.exception.MuseumNotFoundException;
import com.itq.userservice.exception.UserNotFoundException;

@RestController
public class UserServiceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceController.class);

	@Autowired
	private UserBusiness userBusiness;


	@GetMapping("/user")
	public ResponseEntity<?> getUsers(@RequestParam(value = "idUser", required = false) Integer idUser) {

		try{
			if (idUser == null) {
					
				List<User> user = userBusiness.getAllUsers();
				
				if(user.isEmpty()) {
					
					LOGGER.info("No USERS found");
					return new ResponseEntity<>(new Ack(204,"No USERS found"), HttpStatus.OK);
					
				}
				else {
					
					LOGGER.info("FOR QUERING A SPECIFIC USER, USE /user?idUser=12");
					return new ResponseEntity<>(user, HttpStatus.OK);
					
				}
			}
			else {
				
				User user = userBusiness.getUserByID(idUser);
				return new ResponseEntity<>(user, HttpStatus.OK);

			}
		}
		catch (UserNotFoundException e) {
			
			LOGGER.error("ERROR GETING USERS, USER NOT FOUND IN THE DATA BASE", e);
			return new ResponseEntity<>(new Ack(404, "404,ERROR GETING USERS, USER NOT FOUND IN THE DATA BASE"), HttpStatus.NOT_FOUND);
			
		}
		catch (InvalidRequestException e) {
				
			LOGGER.error("ERROR GETING USERS, VERIFY URL", e);
			return new ResponseEntity<>(new Ack(404, "404,ERROR GETING USERS, VERIFY URL"), HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {

			LOGGER.error("ERROR GETTING USERS", e);
			return new ResponseEntity<>(new Ack(500, "INTERNAL SERVER ERROR"),HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> insertUser(@Valid @RequestBody UserInsert user) {

		try {

			User insertedUser = userBusiness.insertUser(user);
			return new ResponseEntity<>(insertedUser, HttpStatus.CREATED);
		
		} catch (MuseumNotFoundException e) {
			
			LOGGER.error("ERROR INSERTING USER, MUSEUM " +user.getIdMuseum() +" NOT FOUND IN THE DATA BASE", e);
			return new ResponseEntity<>(new Ack(404, "ERROR INSERTING USER, MUSEUM " +user.getIdMuseum() +" NOT FOUND IN THE DATA BASE"), HttpStatus.NOT_FOUND);

		} catch (DuplicatedUserException e){

			LOGGER.error("ERROR INSERTING USER, USER: " +user.getName() +" " +user.getLastName() +" ALREADY EXISTS IN THE MUSEUM " +user.getIdMuseum(), e);
			return new ResponseEntity<>(new Ack(409, "ERROR INSERTING USER, USER: " +user.getName() +" " +user.getLastName() +" ALREADY EXISTS IN THE MUSEUM \" +user.getIdMuseum()"), HttpStatus.CONFLICT);

		}
		catch (Exception e) {

			LOGGER.error("ERROR INSERTING USER", e);
			return new ResponseEntity<>(new Ack(500, "ERROR INSERTING USER"), HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PutMapping(value = "/user", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdate user) {

		try {

			return new ResponseEntity<>(userBusiness.updateUser(user), HttpStatus.OK);

		} catch (UserNotFoundException e) {

			LOGGER.error("ERROR UPDATING USER, USER " +user.getIdUser() +" NOT FOUND IN THE DATA BASE", e);
			return new ResponseEntity<>(new Ack(404, "ERROR UPDATING USER, USER " +user.getIdUser() +" NOT FOUND IN THE DATA BASE"), HttpStatus.NOT_FOUND);

		} 
		catch (Exception e) {

			LOGGER.error("ERROR UPDATING USER", e);
			return new ResponseEntity<>(new Ack(500, "ERROR UPDATING USER"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/user")
	public ResponseEntity<?> deleteUser(@RequestParam(value = "idUser", required = true) Integer idUser) {
		
		try {

			User user = userBusiness.getUserByID(idUser);
			userBusiness.deleteUser(idUser);
			return new ResponseEntity<>(new Ack(200, "USER: " + user.getName() +" " +user.getLastName() + " DELETED"), HttpStatus.OK);

		} catch (UserNotFoundException e) {

			LOGGER.error("ERROR DELETING USER, USER " +idUser +" NOT FOUND IN THE DATA BASE", e);
			return new ResponseEntity<>(new Ack(404, "ERROR DELETING USER, USER " +idUser +" NOT FOUND IN THE DATA BASE"), HttpStatus.NOT_FOUND);

		} catch (Exception e) {

			LOGGER.error("ERROR DELETING USER", e);
			return new ResponseEntity<>(new Ack(500, "ERROR DELETING USER"), HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}