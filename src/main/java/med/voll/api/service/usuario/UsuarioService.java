package med.voll.api.service.usuario;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import med.voll.api.domain.usuario.Usuario;
import med.voll.api.dto.autenticacao.AutenticacaoDto;
import med.voll.api.repository.UsuarioRepository;

@Service
public class UsuarioService{

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;

    @Transactional
    public Usuario registerUser(AutenticacaoDto dados) {
        Usuario usuario = new Usuario(dados);
        usuario.setLogin(dados.login());
        usuario.setSenha(this.passwordEncoder.encode(dados.senha()));
        this.repository.save(usuario);

        return usuario;
    }

    public Authentication login(AutenticacaoDto dados) {
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        Authentication auth = manager.authenticate(token);
        return auth;

    }
}
