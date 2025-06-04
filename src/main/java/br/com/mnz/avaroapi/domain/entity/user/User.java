package br.com.mnz.avaroapi.domain.entity.user;

import br.com.mnz.avaroapi.domain.entity.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("users")
public class User extends BaseEntity {

    @Column("email")
    private String email;

    @Column("password")
    private String password;

    @Column("nickname")
    private String nickname;

    @Column("is_active")
    private boolean isActive;

    @Column("role")
    private String role;
}