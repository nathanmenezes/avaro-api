package br.com.mnz.avaroapi.domain.entity.workspace;

import br.com.mnz.avaroapi.domain.entity.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("workspaces")
@NoArgsConstructor
public class Workspace extends BaseEntity {

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("is_active")
    private boolean isActive;
}
