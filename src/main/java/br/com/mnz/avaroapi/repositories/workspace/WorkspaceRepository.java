package br.com.mnz.avaroapi.repositories.workspace;

import br.com.mnz.avaroapi.domain.entity.workspace.Workspace;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface WorkspaceRepository extends ReactiveCrudRepository<Workspace, Long> {
}
