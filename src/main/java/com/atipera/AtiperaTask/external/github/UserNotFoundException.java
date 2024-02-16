package com.atipera.AtiperaTask.external.github;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.server.ResponseStatusException;


public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int statusCode, JSONObject object) {
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,"Status: " + statusCode +" message: " + object.get("message"));
    }


}