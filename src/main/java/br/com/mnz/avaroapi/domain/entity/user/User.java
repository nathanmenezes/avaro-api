package br.com.mnz.avaroapi.domain.entity.user;

import br.com.mnz.avaroapi.domain.dto.request.auth.SignUpRequest;
import br.com.mnz.avaroapi.domain.entity.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Table("users")
@NoArgsConstructor
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

    @Column("workspace_id")
    private Long workspaceId;

    public User(SignUpRequest signUpRequest,
                PasswordEncoder passwordEncoder, Long workspaceId) {
        this.email = signUpRequest.email();
        this.password = passwordEncoder.encode(signUpRequest.password());
        this.nickname = signUpRequest.nickname();
        this.isActive = true;
        this.role = "USER";
        this.workspaceId = workspaceId;
    }
}