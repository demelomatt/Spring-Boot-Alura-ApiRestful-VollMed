package med.voll.api.dto.autenticacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import med.voll.api.domain.admin.admin;

public record AutenticacaoDto(
        @NotBlank @Email String login,
        @NotBlank String senha
) {
    public AutenticacaoDto(admin admin) {
        this(admin.getLogin(), admin.getSenha());
    }
}
