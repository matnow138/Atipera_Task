package com.atipera.AtiperaTask.exceptions;

import com.atipera.AtiperaTask.domain.RepositoryDto;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ErrorHandlerController {
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public static String handleNotFoundError(int responseCode, JSONObject object){
        return "status: "+ responseCode + "\n" +
                "message" + object.get("message");
    }
}
