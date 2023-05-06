package med.voll.api.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import med.voll.api.dto.autenticacao.AutenticacaoAutenticarRequest;
import med.voll.api.dto.autenticacao.AutenticacaoAutenticarResponse;
import med.voll.api.service.admin.AdminService;

@Tag(name = "Autenticação")
@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "Autenticar usuário")
    @PostMapping
    public ResponseEntity<AutenticacaoAutenticarResponse> autenticar(@RequestBody @Valid AutenticacaoAutenticarRequest dados) {
        String credentials = this.adminService.getCredentials(dados);
        return ResponseEntity.ok(new AutenticacaoAutenticarResponse(credentials));
    }
}
