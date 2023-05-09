package med.voll.api.adapter.web.controller;

import java.net.URI;

import jakarta.validation.Valid;

import med.voll.api.application.dto.admin.AdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import med.voll.api.domain.entity.admin.Admin;
import med.voll.api.adapter.web.dto.autenticacao.AutenticacaoUsuarioRequest;
import med.voll.api.adapter.web.dto.admin.AdminCadastrarResponse;
import med.voll.api.application.service.admin.AdminService;

@Tag(name = "Usuário")
@RestController
@RequestMapping("/usuarios")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Operation(summary = "Cadastrar um novo usuário")
    @PostMapping
    public ResponseEntity<AdminCadastrarResponse> cadastrar(@RequestBody @Valid AutenticacaoUsuarioRequest dados, UriComponentsBuilder uriBuilder) {
        AdminCadastrarResponse admin =
                new AdminCadastrarResponse(
                        this.adminService.cadastrar(
                                new AdminDto(dados)));
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(admin.id()).toUri();
        return ResponseEntity.created(uri).body(admin);
    }
}
