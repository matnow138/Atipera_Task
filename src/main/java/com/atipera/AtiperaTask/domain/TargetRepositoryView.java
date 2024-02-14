package com.atipera.AtiperaTask.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class TargetRepositoryView {

    private String repositoryName;
    private String owner;
    private String branch;
    private String sha;

}
