package med.voll.api.application.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import med.voll.api.adapter.web.dto.autenticacao.AutenticacaoUsuarioRequest;
import med.voll.api.domain.entity.admin.Admin;

public record AdminDto(
        Long id,
        @Email String login,
        String senha
) {
    public AdminDto(AutenticacaoUsuarioRequest dados) {
        this(null, dados.login(), dados.senha());
    }

    public AdminDto(Admin admin) {
        this(admin.getId(), admin.getLogin(), admin.getSenha());
    }
}
