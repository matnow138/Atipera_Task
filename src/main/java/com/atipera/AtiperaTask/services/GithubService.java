package com.atipera.AtiperaTask.services;

import com.atipera.AtiperaTask.api.TargetRepositoryView;
import com.atipera.AtiperaTask.external.github.GithubClient;
import com.atipera.AtiperaTask.external.github.RepositoryDto;
import com.atipera.AtiperaTask.mapper.RepositoryMapper;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class GithubService {


    private final GithubClient githubClient;

    private final RepositoryMapper repositoryMapper;

    public GithubService(GithubClient githubClient, RepositoryMapper repositoryMapper) {
        this.githubClient = githubClient;
        this.repositoryMapper = repositoryMapper;
    }

    public List<TargetRepositoryView> getRepositoriesOfUser(String username) throws Exception {
        List<RepositoryDto> repos = githubClient.getRepositoriesOfUser(username);
        return repos.parallelStream()
                .filter(repo -> !repo.isFork())
                .map(repo -> convertToTargetView(username, repo)).toList();
    }

    private TargetRepositoryView convertToTargetView(String username, RepositoryDto repositoryDto) {
        try {
            return repositoryMapper.mapToTargetRepository(repositoryDto, githubClient.getBranches(username, repositoryDto.getRepositoryName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}