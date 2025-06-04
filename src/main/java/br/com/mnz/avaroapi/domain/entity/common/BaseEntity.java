package br.com.mnz.avaroapi.domain.entity.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
public abstract class BaseEntity implements Serializable {

    @Id
    @Column("id")
    private Long id;

    @Column("created_at")
    private Instant createdAt;

    @Column("slug")
    private String slug;

    public BaseEntity() {
        this.createdAt = Instant.now();
        this.slug = UUID.randomUUID().toString();
    }
}
