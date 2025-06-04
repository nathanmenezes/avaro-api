package br.com.mnz.avaroapi.services.auth;

import br.com.mnz.avaroapi.domain.dto.request.auth.AuthRequest;
import br.com.mnz.avaroapi.domain.dto.request.auth.SignUpRequest;
import br.com.mnz.avaroapi.domain.dto.response.auth.AuthResponse;
import br.com.mnz.avaroapi.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    private final ReactiveAuthenticationManager reactiveAuthenticationManager;
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public AuthService(ReactiveAuthenticationManager reactiveAuthenticationManager,
                       JWTService jwtService,
                       UserDetailsService userDetailsService,
                       UserService userService) {
        this.reactiveAuthenticationManager = reactiveAuthenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    public Mono<AuthResponse> signIn(@Valid AuthRequest authRequest) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                authRequest.email(), authRequest.password()
        );
        
        return reactiveAuthenticationManager.authenticate(authToken)
                .flatMap(auth -> userDetailsService.findByUsername(auth.getName()))
                .map(userDetails -> {
                    String token = jwtService.generateToken(userDetails);
                    return new AuthResponse(token);
                })
                .onErrorResume(e -> Mono.error(new RuntimeException(e.getMessage())));
    }

    public Mono<AuthResponse> signUp(@Valid SignUpRequest signUpRequest) {
        return this.userService.createUser(signUpRequest)
                .flatMap(user -> this.signIn(new AuthRequest(user.getEmail(), signUpRequest.password())))
                .onErrorResume(e -> Mono.error(new RuntimeException(e.getMessage())));
    }
}