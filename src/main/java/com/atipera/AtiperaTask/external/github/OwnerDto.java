package com.atipera.AtiperaTask.external.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OwnerDto {
    @JsonProperty("login")
    private String ownerName;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
