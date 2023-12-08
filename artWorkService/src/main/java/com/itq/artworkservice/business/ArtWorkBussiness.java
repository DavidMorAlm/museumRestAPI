package com.itq.artworkservice.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.itq.artworkservice.dao.ArtWorkDAO;
import com.itq.artworkservice.dto.ArtWork;
import com.itq.artworkservice.dto.ArtWorkInsert;
import com.itq.artworkservice.dto.ArtWorkUpdate;
import com.itq.artworkservice.exception.ArtWorkNotFoundException;
import com.itq.artworkservice.service.ArtWorkServiceController;

import java.util.List;

@Service
public class ArtWorkBussiness {

    @Autowired
    private ArtWorkDAO artWorkDao;

    private RestTemplate restTemplate = new RestTemplate();
    private String HALL_SERVICE_URL = "http://localhost:8081/halls";

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtWorkServiceController.class);


    public List<ArtWork> getAllArtWorks() throws Exception {
        List<ArtWork> artWorks = artWorkDao.getAllArtWorks();
        if (artWorks.isEmpty()) {
            throw new ArtWorkNotFoundException("NO ARTWORKS WERE FOUND");
        }
        return artWorkDao.getAllArtWorks();
    }

    public ArtWork insertArtWork(ArtWorkInsert artWork) throws Exception{
        return artWorkDao.insertArtWork(artWork);
    }

    public ArtWork getArtWorkQR(int idArtWork) throws Exception {
        return artWorkDao.getArtWorkByID(idArtWork, true);
    }

    public ArtWork updateArtWork(ArtWorkUpdate artWork) throws Exception {
        LOGGER.info("LOOKING FOR A HALL WITH ID: " + artWork.getIdHall() + " TO PUT");
        restTemplate.getForEntity(HALL_SERVICE_URL + "/" + artWork.getIdHall(), String.class);
		return artWorkDao.updateArtWork(artWork);
	}

    public ArtWork deleteArtWork(int idArtWork) throws Exception {
        return artWorkDao.deleteArtWork(idArtWork);
    }

    public List<ArtWork> getArtWorkQuery(String author, String technique, String name) throws Exception {
        return artWorkDao.getArtWorkQuery(author, technique, name);
    }

}