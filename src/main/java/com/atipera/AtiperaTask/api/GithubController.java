package com.atipera.AtiperaTask.api;

import com.atipera.AtiperaTask.services.GithubService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/")
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping
    public ResponseEntity<Object> getRepositories(@RequestParam String username) throws Exception {
        String REGEX = "^[-a-zA-Z0-9]*$";
        if(username.matches(REGEX)) {
            return ResponseEntity.ok().body(githubService.getRepositoriesOfUser(username));
        }else{
            return ResponseEntity.status(400).body("Only alphanumerical characters and hyphen are allowed");
        }
    }
}
