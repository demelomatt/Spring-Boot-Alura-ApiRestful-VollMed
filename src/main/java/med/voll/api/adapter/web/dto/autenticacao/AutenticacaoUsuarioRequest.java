package med.voll.api.adapter.web.dto.autenticacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import med.voll.api.domain.entity.admin.Admin;

public record AutenticacaoUsuarioRequest(
        @NotBlank @Email String login,
        @NotBlank String senha
) {
    public AutenticacaoUsuarioRequest(Admin admin) {
        this(admin.getLogin(), admin.getSenha());
    }
}
