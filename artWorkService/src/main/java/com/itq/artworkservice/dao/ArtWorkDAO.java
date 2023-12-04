package com.itq.artworkservice.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Component;

import com.itq.artworkservice.dto.ArtWork;
import com.itq.artworkservice.dto.ArtWorkInsert;
import com.itq.artworkservice.dto.ArtWorkUpdate;
import com.itq.artworkservice.exception.ArtWorkNotFoundException;
import com.itq.artworkservice.exception.EmptyResultFromQueryException;

@Component
public class ArtWorkDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtWorkDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<ArtWork> getAllArtWorks() throws Exception {

        String sql = "SELECT * FROM artwork";

        try {

            LOGGER.info("SHOWING ALL ARTWORKS");

            return jdbcTemplate.query(sql, new RowMapper<ArtWork>() {
                @Override
                public ArtWork mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ArtWork artWork = new ArtWork();
                    artWork.setIdArtWork(rs.getInt("idArtWork"));
                    artWork.setIdHall(rs.getInt("idHall"));
                    artWork.setName(rs.getString("name"));
                    artWork.setAuthor(rs.getString("author"));
                    artWork.setTechnique(rs.getString("technique"));
                    artWork.setDescription(rs.getString("description"));
                    artWork.setYear(rs.getString("year"));
                    artWork.setPermanent(rs.getBoolean("permanent"));
                    artWork.setAvailability(rs.getBoolean("availability"));

                    return artWork;
                }

            });

        } catch (Exception e) {

            throw new Exception("THERE WAS A PROBLEM WHILE QUERING ARTWORKS", e);

        }

    }

    public ArtWork insertArtWork(ArtWorkInsert artWork) throws Exception {

        String sql = "INSERT INTO artwork (idHall, name, author, technique, description, year, permanent, availability) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            LOGGER.info("INSERTING ARTWORK");

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[] { "idArtWork" });
                ps.setInt(1, artWork.getIdHall());
                ps.setString(2, artWork.getName());
                ps.setString(3, artWork.getAuthor());
                ps.setString(4, artWork.getTechnique());
                ps.setString(5, artWork.getDescription());
                ps.setString(6, artWork.getYear());
                ps.setBoolean(7, artWork.getPermanent());
                ps.setBoolean(8, artWork.getAvailability());
                return ps;
            }, keyHolder);

            return new ArtWork(artWork, keyHolder.getKey().intValue());

        } catch (Exception e) {

            throw new Exception("THERE WAS A PROBLEM WHILE INSERTING ARTWORK", e);

        }

    }

    public List<Integer> getArtWorksIDs() throws Exception {

        String sql = "SELECT idArtWork FROM artwork";

        try {

            LOGGER.info("GETTING ARTWORKS IDs");

            return jdbcTemplate.query(sql, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Integer idArtWork = Integer.valueOf(rs.getInt("idArtWork"));
                    return idArtWork;
                }

            });

        } catch (Exception e) {

            throw new Exception("THERE WAS A PROBLEM WHILE QUERING ARTWORKS IDs", e);

        }

    }

    public ArtWork getArtWorkByID(int idArtWork, boolean validate) throws Exception {

        String sql = "SELECT * FROM artwork WHERE idArtWork = ?";

        try {

            if (validate)
                if (!existsArtWorkID(idArtWork)) {
                    throw new ArtWorkNotFoundException(
                            "ID NOT MATCHED, CHECK YOUR INPUT ( idArtWork: " + idArtWork + " )\n");
                }
            ;

            LOGGER.info("GETTING ARTWORK BY ID");

            return jdbcTemplate.queryForObject(sql, new RowMapper<ArtWork>() {
                @Override
                public ArtWork mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ArtWork artWork = new ArtWork();
                    artWork.setIdArtWork(rs.getInt("idArtWork"));
                    artWork.setIdHall(rs.getInt("idHall"));
                    artWork.setName(rs.getString("name"));
                    artWork.setAuthor(rs.getString("author"));
                    artWork.setTechnique(rs.getString("technique"));
                    artWork.setDescription(rs.getString("description"));
                    artWork.setYear(rs.getString("year"));
                    artWork.setPermanent(rs.getBoolean("permanent"));
                    artWork.setAvailability(rs.getBoolean("availability"));

                    return artWork;
                }

            }, idArtWork);

        } catch (ArtWorkNotFoundException e) {

            throw new ArtWorkNotFoundException(e.getMessage());

        } catch (Exception e) {

            throw new Exception("THERE WAS A PROBLEM WHILE QUERING THE ARTWORK", e);

        }

    }

    public ArtWork updateArtWork(ArtWorkUpdate artWork) throws Exception {

        String sql = "UPDATE artwork SET idHall = ? WHERE idArtWork = ?";

        try {

            LOGGER.info("LOOKING FOR ARTWORK WITH ID: " + artWork.getIdArtWork() + " TO UPDATE");
            ArtWork artWorkResult = getArtWorkByID(artWork.getIdArtWork(), true);

            if (artWorkResult == null) {

                LOGGER.error("ARTWORK " + artWork.getIdArtWork() + " NOT FOUND");
                throw new ArtWorkNotFoundException("ARTWORK " + artWork.getIdArtWork() + " NOT FOUND");

            } else {

                LOGGER.info("UPDATING ARTWORK");
                String msg = "ARTWORK FILEDS UPDATED!";

                // Check what fields are going to be updated
                if (artWorkResult.getIdHall() == artWork.getIdHall()) {

                    LOGGER.debug("NO CHANGES FOR <idHall>, NOT UPDATING");

                } else {

                    LOGGER.debug("<idHall> FOUND, UPDATING");
                    sql = "UPDATE artwork SET idHall = ? WHERE idArtWork = ?";
                    jdbcTemplate.update(sql, artWork.getIdHall(), artWork.getIdArtWork());
                    msg += " <idHall: " + artWork.getIdHall() + ">";

                }
                if (artWorkResult.getAvailability() == artWork.getAvailability()) {

                    LOGGER.debug("NO CHANGES FOR <availability>, NOT UPDATING");

                } else {

                    LOGGER.debug("NEW <availability> FOUND, UPDATING");
                    sql = "UPDATE artwork SET availability = ? WHERE idArtWork = ?";
                    jdbcTemplate.update(sql, artWork.getAvailability(), artWork.getIdArtWork());
                    msg += " <availability: " + artWork.getAvailability() + ">";

                }

                LOGGER.info(msg);
                LOGGER.info("QUERING AND RETURNING UPDATED ARTWORK");
                return getArtWorkByID(artWork.getIdArtWork(), false);

            }

        } catch (ArtWorkNotFoundException e) {

            throw new ArtWorkNotFoundException(e.getMessage());

        } /*catch (HallNotFoundException e) {

            throw new HallNotFoundException(e.getMessage());

        } */catch (Exception e) {

            throw new Exception("THERE WAS A PROBLEM WHILE UPDATING THE ARTWORK", e);

        }

    }

    public ArtWork deleteArtWork(int idArtWork) throws Exception {

        String sql = "DELETE FROM artwork WHERE idArtWork = ?";

        try {

            LOGGER.info("LOOKING FOR ARTWORK WITH ID: " + idArtWork + " TO DELETE");
            ArtWork artWorkResult = getArtWorkByID(idArtWork, true);

            if (artWorkResult == null) {

                LOGGER.error("ARTWORK " + idArtWork + " NOT FOUND");
                throw new ArtWorkNotFoundException("ARTWORK ( idArtWork: " + idArtWork + " ) NOT FOUND");

            } else {

                LOGGER.info("DELETING ARTWORK");
                jdbcTemplate.update(sql, idArtWork);
                LOGGER.info("ARTWORK DELETED");
                return artWorkResult;

            }

        } catch (ArtWorkNotFoundException e) {

            throw new ArtWorkNotFoundException(e.getMessage());

        } catch (Exception e) {

            throw new Exception("THERE WAS A PROBLEM WHILE DELETING THE ARTWORK", e);

        }

    }

    public List<ArtWork> getArtWorkQuery(String author, String technique, String name) throws Exception {

        String sql = "SELECT * FROM artwork WHERE ";
        String msg = "GETTING ARTWORK(S) BY QUERY -> ";

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();

        if (author != null) {
            keys.add("author");
            values.add(author);
            msg += " ( author: " + author + " )";
        }

        if (technique != null) {
            keys.add("technique");
            values.add(technique);
            msg += " ( technique: " + technique + " )";
        }

        if (name != null) {
            keys.add("name");
            values.add(name);
            msg += " ( name: " + name + " )";
        }

        LOGGER.info(msg);

        if (keys.size() == 1) {
            sql += keys.get(0) + " = " + "?";
        } else {
            sql += keys.get(0) + " = " + "?";
            for (int i = 1; i < keys.size(); i++) {
                sql += " AND " + keys.get(i) + " = ?";
            }
        }

        try {

            List<ArtWork> artWorks = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArtWork.class), values.toArray());
            
            if (artWorks.isEmpty()) {

                msg = "NO ARTWORKS WERE FOUND FOR THIS PARAMS: ";

                for (String key : keys){
                    // msg += "\t( " + key + ": " + values.get(keys.indexOf(key)) + " )\n";
                    msg += "( " + key + ": " + values.get(keys.indexOf(key)) + " ) ";
                }

                throw new EmptyResultFromQueryException(msg);

            } else {
                return artWorks;
            }

        } catch (EmptyResultFromQueryException e) {

            LOGGER.info(msg);
            throw new EmptyResultFromQueryException(e.getMessage());
        
        } catch (Exception e) {

            throw new Exception("THERE WAS A PROBLEM WHILE QUERING ARTWORKS", e);

        }

    }

    public boolean existsArtWorkID(int idArtWork) throws Exception {

        return getArtWorksIDs().contains(idArtWork);

    }

}
