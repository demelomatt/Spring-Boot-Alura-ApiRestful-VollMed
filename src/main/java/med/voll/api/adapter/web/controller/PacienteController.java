package med.voll.api.adapter.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import med.voll.api.adapter.web.dto.paciente.PacienteAtualizarRequest;
import med.voll.api.adapter.web.dto.paciente.PacienteCadastrarRequest;
import med.voll.api.adapter.web.dto.paciente.PacienteDetalheResponse;
import med.voll.api.adapter.web.dto.paciente.PacienteListarResponse;
import med.voll.api.application.dto.paciente.PacienteDto;
import med.voll.api.application.service.paciente.PacienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
        PacienteDetalheResponse paciente =
                new PacienteDetalheResponse(
                        this.pacienteService.cadastrar(
                                new PacienteDto(dados)));
        URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.id()).toUri();
        return ResponseEntity.created(uri).body(paciente);
    }

    @Operation(summary = "Atualizar paciente")
    @PutMapping
    public ResponseEntity<PacienteDetalheResponse> atualizar(@Valid @RequestBody PacienteAtualizarRequest dados) {
        PacienteDetalheResponse paciente =
                new PacienteDetalheResponse(
                        this.pacienteService.atualizar(
                                new PacienteDto(dados)));

        return ResponseEntity.ok(paciente);

    }

    @Operation(summary = "Deletar paciente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        this.pacienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Listar pacientes")
    @GetMapping
    public ResponseEntity<Page<PacienteListarResponse>> listar(Pageable paginacao) {
        Page<PacienteListarResponse> paciente =  this.pacienteService.listar(paginacao).map(PacienteListarResponse::new);
        return ResponseEntity.ok(paciente);
    }

    @Operation(summary = "Detalhar paciente")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDetalheResponse> detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(
                new PacienteDetalheResponse(
                        this.pacienteService.detalhar(id)));
    }

}