package med.voll.api.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.voll.api.domain.usuario.Usuario;
import med.voll.api.dto.autenticacao.AutenticacaoDto;
import med.voll.api.dto.autenticacao.TokenDto;
import med.voll.api.service.usuario.UsuarioService;
import med.voll.api.service.autenticacao.TokenService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid AutenticacaoDto dados) {
        Authentication auth = this.usuarioService.login(dados);
        String token = this.tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new TokenDto(token));
    }
}
