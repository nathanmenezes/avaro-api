package br.com.mnz.avaroapi.config.security;

import br.com.mnz.avaroapi.services.auth.JWTService;
import br.com.mnz.avaroapi.services.auth.UserDetailsService;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtSecurityContextRepository(JWTService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String userEmail = jwtService.extractUserName(token);
            if (StringUtils.isNotEmpty(userEmail)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                return userDetailsService
                        .findByUsername(userEmail)
                        .mapNotNull(user -> {
                                    if (jwtService.isTokenValid(token, user)) {
                                        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                                    }
                            return null;
                        })
                        .map(SecurityContextImpl::new);
            }
        }
        return Mono.empty();
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }
}
