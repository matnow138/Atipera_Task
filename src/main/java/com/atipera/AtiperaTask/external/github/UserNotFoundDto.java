package com.atipera.AtiperaTask.external.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNotFoundDto {

    private int statusCode;
    @JsonProperty("message")
    private String message;

}
