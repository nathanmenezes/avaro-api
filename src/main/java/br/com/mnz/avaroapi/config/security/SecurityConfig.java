package br.com.mnz.avaroapi.config.security;

import br.com.mnz.avaroapi.services.auth.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtSecurityContextRepository contextRepository;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtSecurityContextRepository contextRepository, UserDetailsService userDetailsService) {
        this.contextRepository = contextRepository;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .securityContextRepository(contextRepository)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/auth/**").permitAll()
                        .anyExchange().authenticated()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        return authentication -> userDetailsService.findByUsername(authentication.getName())
                .switchIfEmpty(Mono.error(new RuntimeException("Usuário não encontrado")))
                .flatMap(userDetails -> {
                    if (passwordEncoder().matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
                        return Mono.just(new UsernamePasswordAuthenticationToken(
                                userDetails,
                                userDetails.getPassword(),
                                userDetails.getAuthorities()
                        ));
                    } else {
                        return Mono.error(new RuntimeException("Senha inválida"));
                    }
                });
    }
}