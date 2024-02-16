package com.atipera.AtiperaTask.external.github;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Repository
public class GithubClient {

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final ObjectReader repositoryReader = mapper.readerForArrayOf(RepositoryDto.class);

    public HttpRequest createRequestForRepositories(String username) throws URISyntaxException { //public tylko te musisz, reszta private
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

    public List<RepositoryDto> getRepositoriesOfUser(String username) throws Exception {
        HttpRequest request = createRequestForRepositories(username);
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()); //za każdym razem new client - czy tak powinno byc?
        String body = response.body(); //I know about Github pagination - simplified for less code.
        if (checkIfUserExist(body)) {
            return Arrays.asList(repositoryReader.readValue(body, RepositoryDto[].class));


        }
        JSONObject object = new JSONObject(body);
        throw new UserNotFoundException(response.statusCode(), object);
    }

    public List<BranchDto> getBranches(String username, String repositoryName) throws Exception {
        HttpRequest request = createRequestForBranches(username, repositoryName);
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        BranchDto[] branchArrayNode = repositoryReader.readValue(body, BranchDto[].class);

        return Arrays.stream(branchArrayNode)
                .toList();
    }

    public boolean checkIfUserExist(String body) {

        return !body.contains("\"message\":\"Not Found\"");

    }

}