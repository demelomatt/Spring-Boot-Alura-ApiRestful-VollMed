package med.voll.api.adapter.web.dto.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record AutenticacaoUsuarioResponse(
        @NotBlank String token) {
}
