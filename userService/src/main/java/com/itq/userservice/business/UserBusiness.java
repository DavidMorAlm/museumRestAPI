package com.itq.userservice.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.itq.userservice.dao.UserDAO;
import com.itq.userservice.dto.User;
import com.itq.userservice.service.UserServiceController;

@Service
public class UserBusiness {
	
	@Autowired
	private UserDAO userDAO;

	private RestTemplate restTemplate = new RestTemplate();
    private String USER_SERVICE_URL = "http://localhost:8080/user";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceController.class);

    public List<User> getAllUsers(){
        List<User> user = userDAO.getAllUsers();
        if (user.isEmpty()) {
            LOGGER.debug("No USERS Found");
        }
        return userDAO.getAllUsers();
    }

	public User getUserByID(Integer idUser) throws Exception {
		return userDAO.getUserByID(idUser);
	}

	
}
