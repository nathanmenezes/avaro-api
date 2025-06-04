package br.com.mnz.avaroapi.services.auth;

import br.com.mnz.avaroapi.domain.dto.request.auth.AuthRequest;
import br.com.mnz.avaroapi.domain.dto.response.auth.AuthResponse;
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

    public AuthService(ReactiveAuthenticationManager reactiveAuthenticationManager,
                       JWTService jwtService,
                       UserDetailsService userDetailsService) {
        this.reactiveAuthenticationManager = reactiveAuthenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
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
                .onErrorResume(e -> Mono.error(new RuntimeException("Falha na autenticação: " + e.getMessage())));
    }
}