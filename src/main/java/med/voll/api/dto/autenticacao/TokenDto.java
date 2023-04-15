package med.voll.api.dto.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record TokenDto(
        @NotBlank String token) {
}
