package med.voll.api.dto.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record AutenticacaoUsuarioResponse(
        @NotBlank String token) {
}
