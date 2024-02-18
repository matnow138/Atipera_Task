package com.atipera.AtiperaTask.controller;

import com.atipera.AtiperaTask.api.GithubController;
import com.atipera.AtiperaTask.api.TargetRepositoryView;
import com.atipera.AtiperaTask.external.github.*;
import com.atipera.AtiperaTask.mapper.RepositoryMapper;
import com.atipera.AtiperaTask.services.GithubService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(GithubController.class)
@AutoConfigureMockMvc
public class GithubControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GithubService githubService;
    RepositoryMapper repositoryMapper1 = new RepositoryMapper();
    @Test
    public void getRepositoriesForUser() throws Exception {
        //Given
        RepositoryDto repositoryDto = new RepositoryDto();
        repositoryDto.setRepositoryName("RepositoryName");
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setOwnerName("testOwner");
        repositoryDto.setOwnerDto(ownerDto);
        repositoryDto.setFork(false);
        BranchDto branchDto = new BranchDto();
        branchDto.setName("Branch Name");
        CommitDto commitDto = new CommitDto();
        commitDto.setSha("123");
        branchDto.setCommitDto(commitDto);



        List<BranchDto> branchDtoList = new ArrayList<>();
        branchDtoList.add(branchDto);

        TargetRepositoryView targetRepositoryView = repositoryMapper1.mapToTargetRepository(repositoryDto, branchDtoList);
        given(githubService.getRepositoriesOfUser(anyString())).willReturn(List.of(targetRepositoryView));

        //When & Then
        mockMvc.perform(get("/v1/")
                    .contentType(MediaType.APPLICATION_JSON)
                        .param("username","test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].repositoryName", is("RepositoryName")))
                .andExpect(jsonPath("$[0].owner", is("testOwner")));


    }
}
