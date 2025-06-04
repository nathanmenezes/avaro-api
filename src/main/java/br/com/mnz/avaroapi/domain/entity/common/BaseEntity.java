package br.com.mnz.avaroapi.domain.entity.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
public abstract class BaseEntity implements Serializable {
    @Id
    @Column("id")
    private Long id;

    @Column("created_at")
    private Instant createdAt;

    @Column("slug")
    private String slug;
}
