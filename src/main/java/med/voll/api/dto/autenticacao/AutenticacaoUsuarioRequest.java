package med.voll.api.dto.autenticacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import med.voll.api.domain.admin.Admin;

public record AutenticacaoUsuarioRequest(
        @NotBlank @Email String login,
        @NotBlank String senha
) {
    public AutenticacaoUsuarioRequest(Admin admin) {
        this(admin.getLogin(), admin.getSenha());
    }
}
