package br.com.mnz.avaroapi.services.auth;

import br.com.mnz.avaroapi.domain.entity.user.User;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CurrentUserService {

    public Mono<User> getCurrentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(auth -> (User) auth.getPrincipal());
    }

    public Mono<String> getCurrentUserSlug() {
        return getCurrentUser()
                .map(User::getSlug);
    }
}
