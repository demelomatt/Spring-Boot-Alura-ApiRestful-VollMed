package med.voll.api.controller;

import java.net.URI;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import med.voll.api.domain.paciente.Paciente;
import med.voll.api.dto.paciente.PacienteDetalheResponse;
import med.voll.api.dto.paciente.PacienteAtualizarRequest;
import med.voll.api.dto.paciente.PacienteCadastrarRequest;
import med.voll.api.dto.paciente.PacienteListarResponse;
import med.voll.api.service.paciente.PacienteService;

@Tag(name = "Paciente")
@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Operation(summary = "Cadastra novo paciente")
    @PostMapping
    public ResponseEntity<PacienteDetalheResponse> cadastrar(@Valid @RequestBody PacienteCadastrarRequest dados, UriComponentsBuilder uriBuilder) {
        Paciente paciente = new Paciente(dados);
        this.pacienteService.cadastrar(paciente);
        URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new PacienteDetalheResponse(paciente));
    }

    @Operation(summary = "Listar pacientes")
    @GetMapping
    public ResponseEntity<Page<PacienteListarResponse>> listar(Pageable paginacao) {
        Page<PacienteListarResponse> paciente =  this.pacienteService.listar(paginacao).map(PacienteListarResponse::new);
        return ResponseEntity.ok(paciente);
    }

    @Operation(summary = "Atualizar paciente")
    @PutMapping
    public ResponseEntity<PacienteDetalheResponse> atualizar(@Valid @RequestBody PacienteAtualizarRequest dados) {
        Paciente paciente = this.pacienteService.atualizar(dados);
        return ResponseEntity.ok(new PacienteDetalheResponse(paciente));

    }

    @Operation(summary = "Deletar paciente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        this.pacienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Detalhar paciente")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDetalheResponse> detalhar(@PathVariable Long id) {
        Paciente paciente = this.pacienteService.detalhar(id);
        return ResponseEntity.ok(new PacienteDetalheResponse(paciente));
    }

}