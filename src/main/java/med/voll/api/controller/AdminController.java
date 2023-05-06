package med.voll.api.controller;

import java.net.URI;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import med.voll.api.domain.admin.Admin;
import med.voll.api.dto.autenticacao.AutenticacaoUsuarioRequest;
import med.voll.api.dto.admin.AdminCadastrarResponse;
import med.voll.api.service.admin.AdminService;

@Tag(name = "Usuário")
@RestController
@RequestMapping("/usuarios")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Operation(summary = "Cadastrar um novo usuário")
    @PostMapping
    public ResponseEntity<AdminCadastrarResponse> cadastrar(@RequestBody @Valid AutenticacaoUsuarioRequest dados, UriComponentsBuilder uriBuilder) {
        Admin admin = this.adminService.cadastrar(dados);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(admin.getId()).toUri();
        return ResponseEntity.created(uri).body(new AdminCadastrarResponse(admin));
    }
}
