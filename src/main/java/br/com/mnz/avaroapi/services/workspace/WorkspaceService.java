package br.com.mnz.avaroapi.services.workspace;

import br.com.mnz.avaroapi.domain.entity.workspace.Workspace;
import br.com.mnz.avaroapi.repositories.workspace.WorkspaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public Mono<Long> createDefaultWorkspace() {
        log.info("Creating default workspace");
        Workspace defautlWorkspace = new Workspace();
        defautlWorkspace.setName("Default Workspace");
        defautlWorkspace.setDescription("Default description for the workspace");
        defautlWorkspace.setSlug(UUID.randomUUID().toString());
        defautlWorkspace.setActive(true);
        return this.workspaceRepository.save(defautlWorkspace)
                .flatMap(workspace -> Mono.just(workspace.getId()));
    }
}
