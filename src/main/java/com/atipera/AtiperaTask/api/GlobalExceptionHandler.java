package com.atipera.AtiperaTask.api;

import com.atipera.AtiperaTask.external.github.UserNotFoundDto;
import com.atipera.AtiperaTask.external.github.UserNotFoundException;
import org.json.HTTP;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


    @ControllerAdvice
    public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<?> UserNotFoundException(UserNotFoundException exception) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getUserNotFoundDto());
        }
    }

