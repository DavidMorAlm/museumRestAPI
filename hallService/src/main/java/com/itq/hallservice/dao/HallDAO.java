package com.itq.hallservice.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.itq.hallservice.dto.Hall;

import com.itq.hallservice.exception.HallNotFoundException;


@Component
public class HallDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(HallDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    

    public List<Hall> getAllHalls() throws Exception{

        String sql = "SELECT * FROM hall";

        LOGGER.info("SHOWING ALL HALLS");

        try {

            return jdbcTemplate.query(sql, new RowMapper<Hall>() {
                @Override
                public Hall mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Hall hall = new Hall();
                    hall.setIdHall(rs.getInt("idHall"));
                    hall.setIdMuseum(rs.getInt("idMuseum"));
                    hall.setName(rs.getString("name"));
                    hall.setAvailability(rs.getBoolean("availability"));

                    return hall;
                }
            });
        } catch (DataAccessException e) {

            LOGGER.error("ERROR GETTING ALL HALLS -> " + e.getMessage());
            throw new DataAccessResourceFailureException("ERROR GETTING ALL HALLS -> " + e.getMessage(), e);

        }

    }

    public List<Integer> getHallsIDs() throws Exception{

        String sql = "SELECT idHall FROM hall";

        try {

            LOGGER.info("GETTING HALLS IDs");

            return jdbcTemplate.query(sql, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Integer idHall = Integer.valueOf(rs.getInt("idHall"));
                    return idHall;
                }
            });

        } catch (DataAccessException e) {

            LOGGER.error("THERE WAS A PROBLEM WHILE QUERING HALLs IDs -> " + e.getMessage());
            throw new DataAccessResourceFailureException("THERE WAS A PROBLEM WHILE QUERING HALLs IDs -> " + e.getMessage(), e);

        }
    }

    public Hall getHallById(int idHall) throws Exception{

        String sql = "SELECT * FROM hall WHERE idHall = ?";

        try {

            LOGGER.info("GETTING HALL BY ID");

            return jdbcTemplate.queryForObject(sql, new RowMapper<Hall>() {
                @Override
                public Hall mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Hall hall = new Hall();
                    hall.setIdHall(rs.getInt("idHall"));
                    hall.setIdMuseum(rs.getInt("idMuseum"));
                    hall.setName(rs.getString("name"));
                    hall.setAvailability(rs.getBoolean("availability"));

                    return hall;
                }
            }, idHall);

        } catch (EmptyResultDataAccessException e) {

            LOGGER.error("NO HALL FOUND FOR ID " + idHall + " -> " + e.getMessage(), e);
            throw new HallNotFoundException("NO HALL FOUND FOR ID " + idHall + " -> " + e.getMessage(), e);

        } catch (DataAccessException e) {

            LOGGER.error("ERROR GETTING HALL WITH ID " + idHall + " -> " + e.getMessage(), e);
            throw new DataAccessResourceFailureException("ERROR GETTING HALL WITH ID " + idHall + " -> " + e.getMessage(), e);

        }
    }

    public boolean existsHallID(int idHall) throws Exception{

        return getHallsIDs().contains(idHall);

    }
    
}
