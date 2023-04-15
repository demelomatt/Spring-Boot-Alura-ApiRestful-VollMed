package med.voll.api.dto.usuario;

import med.voll.api.domain.usuario.Usuario;

public record UsuarioDetalheDto() {
    public UsuarioDetalheDto(Usuario usuario) {
        this();
    }
}
