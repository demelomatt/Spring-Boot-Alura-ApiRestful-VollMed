package med.voll.api.dto.autenticacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import med.voll.api.domain.admin.Admin;

public record AutenticacaoAutenticarRequest(
        @NotBlank @Email String login,
        @NotBlank String senha
) {
    public AutenticacaoAutenticarRequest(Admin admin) {
        this(admin.getLogin(), admin.getSenha());
    }
}
