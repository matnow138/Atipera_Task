package com.atipera.AtiperaTask.mapper;

import com.atipera.AtiperaTask.external.github.BranchDto;
import com.atipera.AtiperaTask.external.github.RepositoryDto;
import com.atipera.AtiperaTask.api.TargetRepositoryView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryMapper {

    public TargetRepositoryView mapToTargetRepository(RepositoryDto repositoryDto, List<BranchDto> branchDto) {
        return TargetRepositoryView.builder()
                .repositoryName(repositoryDto.getRepositoryName())
                .owner(repositoryDto.getOwnerDto().getOwnerName())
                .branch(branchDto.getLast().getName())
                .sha(branchDto.getLast().getCommitDto().getSha())
                .build();
    }
}
