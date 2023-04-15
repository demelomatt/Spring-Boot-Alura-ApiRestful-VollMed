package med.voll.api.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.voll.api.dto.usuario.AutenticacaoDto;
import med.voll.api.service.usuario.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<Void> autenticar(@RequestBody @Valid AutenticacaoDto dados) {
        service.login(dados);
        return ResponseEntity.ok().build();
    }
}
