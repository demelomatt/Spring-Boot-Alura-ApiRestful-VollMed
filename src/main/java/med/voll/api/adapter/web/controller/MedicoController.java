package med.voll.api.adapter.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import med.voll.api.adapter.web.dto.medico.MedicoAtualizarRequest;
import med.voll.api.adapter.web.dto.medico.MedicoCadastrarRequest;
import med.voll.api.adapter.web.dto.medico.MedicoDetalheResponse;
import med.voll.api.adapter.web.dto.medico.MedicoListarResponse;
import med.voll.api.application.dto.medico.MedicoDto;
import med.voll.api.application.service.medico.MedicoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(name = "Médico")
@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @Operation(summary = "Cadastrar novo médico")
    @PostMapping
    public ResponseEntity<MedicoDetalheResponse> cadastrar(@Valid @RequestBody MedicoCadastrarRequest dados, UriComponentsBuilder uriBuilder) {
        MedicoDetalheResponse medico =
                new MedicoDetalheResponse(
                        this.medicoService.cadastrar(
                                new MedicoDto(dados)));
        URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.id()).toUri();
        return ResponseEntity.created(uri).body(medico);
    }

    @Operation(summary = "Atualizar médico")
    @PutMapping
    public ResponseEntity<MedicoDetalheResponse> atualizar(@Valid @RequestBody MedicoAtualizarRequest dados) {
        MedicoDetalheResponse medico =
                new MedicoDetalheResponse(
                        this.medicoService.atualizar(
                                new MedicoDto(dados)));

        return ResponseEntity.ok(medico);
    }

    @Operation(summary = "Deletar médico")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        this.medicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar médicos")
    @GetMapping
    public ResponseEntity<Page<MedicoListarResponse>> listar(@PageableDefault(size = 30, sort = {"nome", "especialidade"}) Pageable paginacao) {
        Page<MedicoListarResponse> medicos = this.medicoService.listar(paginacao).map(MedicoListarResponse::new);
        return ResponseEntity.ok(medicos);
    }

    @Operation(summary = "Detalhar médico")
    @GetMapping("/{id}")
    public ResponseEntity<MedicoDetalheResponse> detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(
                new MedicoDetalheResponse(
                        this.medicoService.detalhar(id)));
    }

}
