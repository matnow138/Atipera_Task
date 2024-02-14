package com.atipera.AtiperaTask;

import com.atipera.AtiperaTask.services.GithubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/")
public class GithubContoller {

    private final GithubService githubService;

    public GithubContoller(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping
    public Object getRepositories(@RequestParam String username) throws Exception {
        return githubService.getRepositoriesOfUser(username);

    }
}
