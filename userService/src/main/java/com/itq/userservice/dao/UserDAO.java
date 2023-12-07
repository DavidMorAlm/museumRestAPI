package com.itq.userservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.itq.userservice.dto.Ack;
import com.itq.userservice.dto.User;
import com.itq.userservice.exception.UserNotFoundException;

@Component
public class UserDAO {
	

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public class UserRowMapper implements RowMapper<User>{
    	
    	@Override
    	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setIdUser(rs.getInt("idUser"));
            user.setIdMuseum(rs.getInt("idMuseum"));
            user.setName(rs.getString("name"));
            user.setLastName(rs.getString("lastName"));
            user.setPhoneNumber(rs.getString("phoneNumber"));
            user.setAge(rs.getInt("age"));
            user.setPhoneNumber(rs.getString("phoneNumber"));
            user.setEmail(rs.getString("email"));
            user.setConnected(rs.getBoolean("connected"));

            return user;
            
    	}
    }
	
	public List<User> getAllUsers() throws Exception {

        String sql = "SELECT * FROM user";
        
        try {

            LOGGER.info("SHOWING ALL USERS");
            return jdbcTemplate.query(sql,new UserRowMapper());

        } catch (Exception e) {

            throw new Exception("THERE WAS A PROBLEM WHILE QUERING ARTWORKS", e);

        }
	}

	public User getUserByID(int idUser) throws Exception {
        
        String sql = "SELECT * FROM user WHERE idUser = ?";

        try {

            if(!existsUserID(idUser)){

                LOGGER.error("THE USER " +idUser +" WAS NOT FOUND IN THE DATA BASE");
                throw new UserNotFoundException("THE USER " +idUser +" WAS NOT FOUND IN THE DATA BASE");

            }
            else{

                LOGGER.info("SHOWING USER WITH ID " +idUser);
                return jdbcTemplate.queryForObject(sql, new UserRowMapper(), idUser);

            }
        }catch (UserNotFoundException e) {

            throw new UserNotFoundException("THE USER " +idUser +" WAS NOT FOUND IN THE DATA BASE");

        } catch (Exception e) {

            throw new Exception("THERE WAS A PROBLEM WHILE QUERING ARTWORKS", e);

        }
	}

    public List<Integer> getUsersIDs() throws UserNotFoundException {

        String sql = "SELECT idUser FROM User";

        try {

            LOGGER.info("GETTING USERS IDs");
            return jdbcTemplate.query(sql,new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Integer idUser = Integer.valueOf(rs.getInt("idUser"));
                    return idUser;
                }

            });

        } catch (UserNotFoundException e) {

            throw new UserNotFoundException("THERE WAS A PROBLEM WHILE QUERING USERS IDs");

        }

    }

    public boolean existsUserID(int idUser) throws UserNotFoundException {

        return getUsersIDs().contains(idUser);

    }

}
