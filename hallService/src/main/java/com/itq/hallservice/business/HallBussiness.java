package com.itq.hallservice.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itq.hallservice.dao.HallDAO;
import com.itq.hallservice.dto.Hall;
import com.itq.hallservice.exception.*;
import com.itq.hallservice.service.HallServiceController;

import java.util.List;

@Service
public class HallBussiness {

    @Autowired
    private HallDAO hallDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(HallServiceController.class);


    public List<Hall> getAllHalls() throws Exception {

        return hallDAO.getAllHalls();

    }

    public Hall getHallById(int idHall) throws Exception {

        return hallDAO.getHallById(idHall);

    }

}