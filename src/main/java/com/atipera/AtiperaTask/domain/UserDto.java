package com.atipera.AtiperaTask.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {

    @JsonProperty("username")
    private String username;

    public String getUsername() {
        return username;
    }
}
