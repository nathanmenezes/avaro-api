package br.com.mnz.avaroapi.repositories.user;

import br.com.mnz.avaroapi.domain.entity.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByEmail(String email);

    Mono<Boolean> existsByEmail(@Email @NotBlank String email);
}
