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
import med.voll.api.dto.paciente.PacienteDetalheDto;
import med.voll.api.dto.paciente.PacienteAtualizarDto;
import med.voll.api.dto.paciente.PacienteDto;
import med.voll.api.dto.paciente.PacienteListDto;
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
    public ResponseEntity<PacienteDetalheDto> cadastrar(@Valid @RequestBody PacienteDto dados, UriComponentsBuilder uriBuilder) {
        Paciente paciente = new Paciente(dados);
        this.pacienteService.cadastrar(paciente);
        URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new PacienteDetalheDto(paciente));
    }

    @Operation(summary = "Listar pacientes")
    @GetMapping
    public ResponseEntity<Page<PacienteListDto>> listar(Pageable paginacao) {
        Page<PacienteListDto> paciente =  this.pacienteService.listar(paginacao).map(PacienteListDto::new);
        return ResponseEntity.ok(paciente);
    }

    @Operation(summary = "Atualizar paciente")
    @PutMapping
    public ResponseEntity<PacienteDetalheDto> atualizar(@Valid @RequestBody PacienteAtualizarDto dados) {
        Paciente paciente = this.pacienteService.atualizar(dados);
        return ResponseEntity.ok(new PacienteDetalheDto(paciente));

    }

    @Operation(summary = "Deletar paciente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        this.pacienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Detalhar paciente")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDetalheDto> detalhar(@PathVariable Long id) {
        Paciente paciente = this.pacienteService.detalhar(id);
        return ResponseEntity.ok(new PacienteDetalheDto(paciente));
    }

}