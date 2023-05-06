package med.voll.api.dto.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record AutenticacaoAutenticarResponse(
        @NotBlank String token) {
}
