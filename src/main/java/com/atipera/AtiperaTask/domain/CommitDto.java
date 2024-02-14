package com.atipera.AtiperaTask.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommitDto {

    @JsonProperty("sha")
    private String sha;
}
