package com.itq.userservice.dao;

import java.sql.PreparedStatement;
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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.itq.userservice.dto.Ack;
import com.itq.userservice.dto.User;
import com.itq.userservice.dto.UserInsert;
import com.itq.userservice.dto.UserUpdate;
import com.itq.userservice.exception.MuseumNotFoundException;
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
	
	public List<User> getAllUsers(){

        String sql = "SELECT * FROM user";
        LOGGER.info("SHOWING ALL USERS");
        return jdbcTemplate.query(sql,new UserRowMapper());

	}

	public User getUserByID(int idUser) throws Exception {
        
        String sql = "SELECT * FROM user WHERE idUser = ?";
        if(!existsUserID(idUser)){

            LOGGER.error("THE USER " +idUser +" WAS NOT FOUND IN THE DATA BASE");
            throw new UserNotFoundException("THE USER " +idUser +" WAS NOT FOUND IN THE DATA BASE");

        }
        else{

            LOGGER.info("SHOWING USER WITH ID " +idUser);
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), idUser);

        }

	}

    public List<Integer> getUsersIDs() throws UserNotFoundException {

        String sql = "SELECT idUser FROM User";
        LOGGER.info("GETTING USERS IDs");
        return jdbcTemplate.query(sql,new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer idUser = Integer.valueOf(rs.getInt("idUser"));
                return idUser;
            }

        });

    }

    public boolean existsUserID(int idUser) throws UserNotFoundException {

        return getUsersIDs().contains(idUser);

    }

    public User insertUser(UserInsert user) throws Exception {

        String sql = "INSERT INTO user (idMuseum, name, lastName, phoneNumber, email, age, connected) VALUES (?, ?, ?, ?, ?, ?, ?)";

        LOGGER.info("INSERTING USER");

        if(!existMuseumID(user.getIdMuseum())){

            LOGGER.error("THE MUSEUM " +user.getIdMuseum() +" WAS NOT FOUND IN THE DATA BASE");
            throw new MuseumNotFoundException("THE MUSEUM " +user.getIdMuseum() +" WAS NOT FOUND IN THE DATA BASE");

        }
        else{

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[] { "idUser" });
                ps.setInt(1, user.getIdMuseum());
                ps.setString(2, user.getName());
                ps.setString(3, user.getLastName());
                ps.setString(4, user.getPhoneNumber());
                ps.setString(5, user.getEmail());
                ps.setInt(6, user.getAge());
                ps.setBoolean(7, user.isConnected());
                return ps;
            }, keyHolder);

            return new User(user, keyHolder.getKey().intValue());

        }
    }

    private boolean existMuseumID(int idMuseum) {
        return getMuseumIDs().contains(idMuseum);
    }

    public List<Integer> getMuseumIDs() throws MuseumNotFoundException {

        String sql = "SELECT idMuseum FROM Museum";
        LOGGER.info("GETTING MUSEUM IDs");
        return jdbcTemplate.query(sql,new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer idMuseum = Integer.valueOf(rs.getInt("idMuseum"));
                return idMuseum;
            }

        });

    }

    public User updateUser(UserUpdate user) throws Exception {

        String sql = "UPDATE user SET phoneNumber = ? WHERE idUser = ?";

        LOGGER.info("LOOKING FOR User WITH ID: " + user.getIdUser() + " TO UPDATE");
        User userResult = getUserByID(user.getIdUser());

        if (userResult == null) {

            LOGGER.error("USER " + user.getIdUser() + " NOT FOUND");
            throw new UserNotFoundException("USER " + user.getIdUser() + " NOT FOUND");

        } else {

            LOGGER.info("UPDATING USER");
            String msg = "USER FIELDS UPDATED!";

            if (userResult.getPhoneNumber() == user.getPhoneNumber()) {

                LOGGER.debug("NO CHANGES FOR <phoneNumber>, NOT UPDATING");

            } else {

                LOGGER.debug("<phoneNumber> FOUND, UPDATING");
                sql = "UPDATE user SET phoneNumber = ? WHERE idUser = ?";
                jdbcTemplate.update(sql, user.getPhoneNumber(), user.getIdUser());
                msg += " <phoneNumber: " + user.getPhoneNumber() + ">";

            }
            if (userResult.getEmail() == user.getEmail()) {

                LOGGER.debug("NO CHANGES FOR <email>, NOT UPDATING");

            } else {

                LOGGER.debug("NEW <email> FOUND, UPDATING");
                sql = "UPDATE user SET email = ? WHERE idUser = ?";
                jdbcTemplate.update(sql, user.getEmail(), user.getIdUser());
                msg += " <email: " + user.getEmail() + ">";

            }

            LOGGER.info(msg);
            LOGGER.info("QUERING AND RETURNING UPDATED USER");
            return getUserByID(user.getIdUser());
        }
    }

    public User deleteUser(int idUser) throws Exception {

        String sql = "DELETE FROM user WHERE idUser = ?";

        LOGGER.info("LOOKING FOR USER WITH ID: " + idUser + " TO DELETE");
        User userResult = getUserByID(idUser);

        if (userResult == null) {

            LOGGER.error("USER " + idUser + " NOT FOUND");
            throw new UserNotFoundException("USER " + idUser + " NOT FOUND");

        } else {

            LOGGER.info("DELETING USER");
            jdbcTemplate.update(sql, idUser);
            LOGGER.info("USER DELETED");
            return userResult;

        }
    }

}
