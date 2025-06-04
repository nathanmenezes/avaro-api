package br.com.mnz.avaroapi.domain.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(@Email @NotBlank String email, @NotBlank String password) {
}
