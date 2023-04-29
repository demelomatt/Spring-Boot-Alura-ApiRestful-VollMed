package med.voll.api.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.voll.api.dto.autenticacao.AutenticacaoDto;
import med.voll.api.dto.autenticacao.TokenDto;
import med.voll.api.service.admin.AdminService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AdminService adminService;


    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid AutenticacaoDto dados) {
        String credentials = this.adminService.getCredentials(dados);
        return ResponseEntity.ok(new TokenDto(credentials));
    }
}
