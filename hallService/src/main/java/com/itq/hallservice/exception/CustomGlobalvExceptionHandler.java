package com.itq.hallservice.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.itq.hallservice.service.HallServiceController;

@ControllerAdvice
public class CustomGlobalvExceptionHandler extends ResponseEntityExceptionHandler {

    	private static final Logger LOGGER = LoggerFactory.getLogger(HallServiceController.class);


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(x -> x.getDefaultMessage())
                                .collect(Collectors.toList());

        body.put("errors", errors);

        LOGGER.error("CHECK ERRORS\n");

        return new ResponseEntity<>(body, headers, status);
    }
}