package br.com.mnz.avaroapi.services.user;

import br.com.mnz.avaroapi.domain.dto.request.auth.SignUpRequest;
import br.com.mnz.avaroapi.domain.entity.user.User;
import br.com.mnz.avaroapi.repositories.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<User> createUser(SignUpRequest signUpRequest) {
        if (signUpRequest.password().length() < 8) {
            return Mono.error(new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres."));
        }
        return this.userRepository.existsByEmail(signUpRequest.email())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException("Email já cadastrado."));
                    }
                    return userRepository.save(new User(signUpRequest, passwordEncoder));
                });
    }
}
