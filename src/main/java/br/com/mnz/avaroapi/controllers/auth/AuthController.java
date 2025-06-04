package br.com.mnz.avaroapi.controllers.auth;

import br.com.mnz.avaroapi.domain.dto.request.auth.AuthRequest;
import br.com.mnz.avaroapi.domain.dto.response.auth.AuthResponse;
import br.com.mnz.avaroapi.services.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("sign-in")
    public Mono<AuthResponse> signIn(@RequestBody @Valid final AuthRequest authRequest) {
        return this.authService.signIn(authRequest);
    }
}
