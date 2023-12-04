package com.itq.artworkservice.service;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.itq.artworkservice.business.ArtWorkBussiness;
import com.itq.artworkservice.dto.Ack;
import com.itq.artworkservice.dto.ArtWork;
import com.itq.artworkservice.dto.ArtWorkInsert;
import com.itq.artworkservice.dto.ArtWorkUpdate;
import com.itq.artworkservice.exception.ArtWorkNotFoundException;
import com.itq.artworkservice.exception.EmptyResultFromQueryException;
import com.itq.artworkservice.exception.*;

@RestController
public class ArtWorkServiceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArtWorkServiceController.class);

	@Autowired
	private ArtWorkBussiness artWorkBusiness;


	@GetMapping("/artwork")
	public ResponseEntity<?> getArtWorks(@RequestParam(value = "idArtWork", required = false) Integer idArtWork,
			@RequestParam(value = "technique", required = false) String technique,
			@RequestParam(value = "author", required = false) String author,
			@RequestParam(value = "name", required = false) String name) {

		if (idArtWork == null) {

			if (technique != null || author != null || name != null) {

				try {

					List<ArtWork> artWorks = artWorkBusiness.getArtWorkQuery(author, technique, name);
					return new ResponseEntity<>(artWorks, HttpStatus.OK);

				} catch (EmptyResultFromQueryException e) {

					return new ResponseEntity<>(new Ack(200, e.getMessage()),
							HttpStatus.OK);

				} catch (Exception e) {

					LOGGER.error("ERROR GETTING ARTWORKS", e);
					return new ResponseEntity<>(new Ack(500, "INTERNAL SERVER ERROR"),
							HttpStatus.INTERNAL_SERVER_ERROR);

				}

			} else
				try {

					List<ArtWork> artWorks = artWorkBusiness.getAllArtWorks();
					LOGGER.info("FOR QUERING AN SPECIFIC ARTWORK, USE /artwork?idArtWork=1");
					LOGGER.info(
							"FOR AN SPECIFIC QUERY, USE /artwork?technique=oil&author=Picasso&name=Guernica (request at least one param)");
					return new ResponseEntity<>(artWorks, HttpStatus.OK);

				} catch (ArtWorkNotFoundException e) {

					LOGGER.error("ERROR GETTING ARTWORKS", e);
					return new ResponseEntity<>(new Ack(404, "NOT FOUND - There are not ArtWorks"),
							HttpStatus.NOT_FOUND);

				} catch (Exception e) {

					LOGGER.error("ERROR GETTING ARTWORKS", e);
					return new ResponseEntity<>(new Ack(500, "INTERNAL SERVER ERROR"),
							HttpStatus.INTERNAL_SERVER_ERROR);

				}

		} else {

			try {

				ArtWork artWork = artWorkBusiness.getArtWorkQR(idArtWork);
				return new ResponseEntity<>(artWork, HttpStatus.OK);

			} catch (ArtWorkNotFoundException e) {

				LOGGER.error("ERROR GETTING ARTWORK", e);
				return new ResponseEntity<>(
						new Ack(404, "ARTWORK NOT FOUND - Verify your input ( idArtWork: " + idArtWork + " )"),
						HttpStatus.NOT_FOUND);

			} catch (Exception e) {

				LOGGER.error("ERROR GETTING ARTWORK", e);
				return new ResponseEntity<>(new Ack(500, "ERROR INSERTING ARTWORK"), HttpStatus.INTERNAL_SERVER_ERROR);

			}

		}

	}

	@PostMapping(value = "/artwork", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> insertArtWork(@Valid @RequestBody ArtWorkInsert artWork) {

		try {

			ArtWork insertedArtWork = artWorkBusiness.insertArtWork(artWork);
			return new ResponseEntity<>(insertedArtWork, HttpStatus.CREATED);

		} catch (Exception e) {

			LOGGER.error("ERROR INSERTING ARTWORK", e);
			return new ResponseEntity<>(new Ack(500, "ERROR INSERTING ARTWORK"), HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PutMapping(value = "/artwork", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updateArtWork(@Valid @RequestBody ArtWorkUpdate artWork) {

		try {

			return new ResponseEntity<>(artWorkBusiness.updateArtWork(artWork), HttpStatus.OK);

		} catch (ArtWorkNotFoundException e) {

			LOGGER.error("ERROR UPDATING ARTWORK", e);
			return new ResponseEntity<>(
					new Ack(404, "ARTWORK NOT FOUND - Verify your input (" + artWork.getIdArtWork() + ")"),
					HttpStatus.NOT_FOUND);

		} catch ( HttpClientErrorException e) {

			LOGGER.error("HALL NOT FOUND - Verify your input (" + artWork.getIdHall() + ")", e);
			return new ResponseEntity<>(
					new Ack(404, "HALL NOT FOUND - Verify your input (" + artWork.getIdHall() + ")"),
					HttpStatus.NOT_FOUND);

		} catch (Exception e) {

			LOGGER.error("ERROR UPDATING ARTWORK", e);
			return new ResponseEntity<>(new Ack(500, "ERROR UPDATING ARTWORK"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/artwork")
	public ResponseEntity<?> deleteArtwork(@RequestParam(value = "idArtWork") int idArtWork) {

		try {

			artWorkBusiness.deleteArtWork(idArtWork);
			return new ResponseEntity<>(new Ack(200, "ARTWORK DELETED"), HttpStatus.OK);

		} catch (ArtWorkNotFoundException e) {

			LOGGER.error("ERROR DELETING ARTWORK", e);
			return new ResponseEntity<>(
					new Ack(404, "ARTWORK NOT FOUND - Verify your input ( idArtWork: " + idArtWork + " )"),
					HttpStatus.NOT_FOUND);

		} catch (Exception e) {

			LOGGER.error("ERROR DELETING ARTWORK", e);
			return new ResponseEntity<>(new Ack(500, "ERROR DELETING ARTWORK"), HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

}
