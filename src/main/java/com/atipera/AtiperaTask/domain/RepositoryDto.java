package com.atipera.AtiperaTask.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RepositoryDto {

    @JsonProperty("name")
    private String RepositoryName;
    @JsonProperty("owner")
    private OwnerDto ownerDto;
    @JsonProperty("fork")
    private boolean fork;


}
