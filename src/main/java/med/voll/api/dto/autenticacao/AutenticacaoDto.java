package med.voll.api.dto.autenticacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import med.voll.api.domain.usuario.Usuario;

public record AutenticacaoDto(
        @NotBlank @Email String login,
        @NotBlank String senha
) {
    public AutenticacaoDto(Usuario usuario) {
        this(usuario.getLogin(), usuario.getSenha());
    }
}
