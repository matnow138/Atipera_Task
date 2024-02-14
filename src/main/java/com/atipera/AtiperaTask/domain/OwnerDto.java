package com.atipera.AtiperaTask.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OwnerDto {

    @JsonProperty("login")
    private String ownerName;

    public String getOwnerName() {
        return ownerName;
    }
}
