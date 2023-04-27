package med.voll.api.controller;

import java.net.URI;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import med.voll.api.domain.usuario.Usuario;
import med.voll.api.dto.autenticacao.AutenticacaoDto;
import med.voll.api.dto.usuario.UsuarioDetalheDto;
import med.voll.api.service.usuario.UsuarioService;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDetalheDto> cadastrar(@RequestBody @Valid AutenticacaoDto dados, UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioService.registerUser(dados);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioDetalheDto(usuario));
    }
}
