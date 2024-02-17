package com.atipera.AtiperaTask.external.github;

import org.json.JSONObject;

public class UserNotFoundException extends RuntimeException {
    private UserNotFoundDto userNotFoundDto =new UserNotFoundDto();
    public UserNotFoundException(UserNotFoundDto userNotFoundDto) {

    this.userNotFoundDto=userNotFoundDto;
    }

    public UserNotFoundDto getUserNotFoundDto() {
        return userNotFoundDto;
    }
}