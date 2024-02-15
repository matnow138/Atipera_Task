package com.atipera.AtiperaTask.services;

import com.atipera.AtiperaTask.domain.BranchDto;
import com.atipera.AtiperaTask.domain.OwnerDto;
import com.atipera.AtiperaTask.domain.RepositoryDto;
import com.atipera.AtiperaTask.domain.TargetRepositoryView;
import com.atipera.AtiperaTask.exceptions.ErrorHandlerController;
import com.atipera.AtiperaTask.mapper.RepositoryMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.Data;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Service
@Data
public class GithubService {

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final ObjectReader repositoryReader = mapper.readerForArrayOf(RepositoryDto.class);
    private final ObjectReader ownerReader = mapper.readerForArrayOf(OwnerDto.class);
    private final ObjectReader userReader = mapper.readerForArrayOf(OwnerDto.class);
    private final ObjectReader targetViewReader = mapper.readerForArrayOf(TargetRepositoryView.class);
    private final ObjectReader branchReader = mapper.readerForArrayOf(BranchDto.class);
    private final RepositoryMapper repositoryMapper;

    public HttpRequest createRequestForRepositories(String username) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(constructUriForRepositories(username))
                .build();
    }

    private URI constructUriForRepositories(String username) throws URISyntaxException {
        return new URIBuilder("https://api.github.com")
                .setPath("/users/" + username + "/repos")
                .build();
    }

    public HttpRequest createRequestForBranches(String username, String repositoryName) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(constructUriForBranches(username, repositoryName))
                .build();
    }

    private URI constructUriForBranches(String username, String repositoryName) throws URISyntaxException {
        return new URIBuilder("https://api.github.com")
                .setPath("/repos/" + username + "/" + repositoryName + "/branches")
                .build();
    }

    public Object getRepositoriesOfUser(String username) throws Exception {
        HttpRequest request = createRequestForRepositories(username);
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        int responseCode = response.statusCode();
        String body = response.body();
        if (checkIfUserExist(body)) {
            RepositoryDto[] repositoryArrayNode = repositoryReader.readValue(body, RepositoryDto[].class);

            return convertToTargetView(username, repositoryArrayNode);
        } else {
            JSONObject object = new JSONObject(body);
            return ErrorHandlerController.handleNotFoundError(responseCode,object);
        }
    }

    public List<BranchDto> getBranches(String username, String repositoryName) throws Exception {
        HttpRequest request = createRequestForBranches(username, repositoryName);
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        BranchDto[] branchArrayNode = repositoryReader.readValue(body, BranchDto[].class);

        return Arrays.stream(branchArrayNode)
                .toList();
    }


    private List<TargetRepositoryView> convertToTargetView(String username, RepositoryDto[] repositoryDtoList) {

        return Arrays.stream(repositoryDtoList)
                .filter(x -> !x.isFork())
                .map(x -> {
                    try {
                        return repositoryMapper.mapToTargetRepository(x, getBranches(username, x.getRepositoryName()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                })
                .toList();
    }

    public boolean checkIfUserExist(String body) {

        return !body.contains("\"message\":\"Not Found\"");

    }


}
