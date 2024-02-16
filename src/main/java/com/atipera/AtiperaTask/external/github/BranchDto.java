package com.atipera.AtiperaTask.external.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BranchDto {

    @JsonProperty("name")
    private String name;
    @JsonProperty("commit")
    private CommitDto commitDto;
}
