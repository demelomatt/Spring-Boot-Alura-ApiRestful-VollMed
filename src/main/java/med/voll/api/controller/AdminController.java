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

import med.voll.api.domain.admin.Admin;
import med.voll.api.dto.autenticacao.AutenticacaoDto;
import med.voll.api.dto.admin.AdminDetalheDto;
import med.voll.api.service.admin.AdminService;

@RestController
@RequestMapping("/usuarios")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping
    public ResponseEntity<AdminDetalheDto> cadastrar(@RequestBody @Valid AutenticacaoDto dados, UriComponentsBuilder uriBuilder) {
        Admin admin = this.adminService.cadastrar(dados);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(admin.getId()).toUri();
        return ResponseEntity.created(uri).body(new AdminDetalheDto(admin));
    }
}
