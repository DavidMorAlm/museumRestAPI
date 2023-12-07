package com.itq.userservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.itq.userservice.business.UserBusiness;
import com.itq.userservice.dto.Ack;
import com.itq.userservice.dto.User;
import com.itq.userservice.exception.InvalidRequestException;
import com.itq.userservice.exception.UserNotFoundException;

@RestController
public class UserServiceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceController.class);

	@Autowired
	private UserBusiness userBusiness;


	@GetMapping("/user")
	public ResponseEntity<?> getUsers(@RequestParam(value = "idUser", required = false) Integer idUser) {

		if (idUser == null) {
			try {
				
				List<User> user = userBusiness.getAllUsers();
				
				if(user.isEmpty()) {
					
					LOGGER.info("No USERS found");
					return new ResponseEntity<>(new Ack(204,"No USERS found"), HttpStatus.OK);
					
				}
				else {
					
					LOGGER.info("FOR QUERING A SPECIFIC USER, USE /user?idUser=12");
					return new ResponseEntity<>(user, HttpStatus.OK);
					
				}
			}  catch (InvalidRequestException e) {
				
				LOGGER.error("ERROR GETING USERS, VERIFY URL", e);
				return new ResponseEntity<>(new Ack(404, "404,ERROR GETING USERS, VERIFY URL"), HttpStatus.BAD_REQUEST);
				
			} catch (Exception e) {

				LOGGER.error("ERROR GETTING USERS", e);
				return new ResponseEntity<>(new Ack(500, "INTERNAL SERVER ERROR"),HttpStatus.INTERNAL_SERVER_ERROR);

			}
			
		}
		else {
			
			try {
				User user = userBusiness.getUserByID(idUser);
				return new ResponseEntity<>(user, HttpStatus.OK);

			} catch (UserNotFoundException e) {

				LOGGER.error("ERROR GETTING USER", e);
				return new ResponseEntity<>(new Ack(404, e.getMessage()), HttpStatus.NOT_FOUND);

			}catch (InvalidRequestException e) {
				
				LOGGER.error("ERROR GETING USERS, VERIFY URL", e);
				return new ResponseEntity<>(new Ack(404, "404,ERROR GETING USERS, VERIFY URL"), HttpStatus.BAD_REQUEST);
				
			} catch (Exception e) {

				LOGGER.error("ERROR GETTING USER", e);
				return new ResponseEntity<>(new Ack(500, "INTERNAL SERVER ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);

			}				
		}
	}
}
