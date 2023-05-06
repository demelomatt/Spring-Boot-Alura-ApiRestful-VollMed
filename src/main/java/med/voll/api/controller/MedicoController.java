package med.voll.api.controller;

import java.net.URI;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import med.voll.api.domain.medico.Medico;
import med.voll.api.dto.medico.MedicoDetalheDto;
import med.voll.api.dto.medico.MedicoAtualizarDto;
import med.voll.api.dto.medico.MedicoDto;
import med.voll.api.dto.medico.MedicoListDto;
import med.voll.api.service.medico.MedicoService;

@Tag(name = "Médico")
@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @Operation(summary = "Cadastrar novo médico")
    @PostMapping
    public ResponseEntity<MedicoDetalheDto> cadastrar(@Valid @RequestBody MedicoDto dados, UriComponentsBuilder uriBuilder) {
        Medico medico = new Medico(dados);
        this.medicoService.cadastrar(medico);
        URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new MedicoDetalheDto(medico));
    }

    @Operation(summary = "Listar médicos")
    @GetMapping
    public ResponseEntity<Page<MedicoListDto>> listar(@PageableDefault(size = 30, sort = {"nome", "especialidade"}) Pageable paginacao) {
        Page<MedicoListDto> medicos = this.medicoService.listar(paginacao).map(MedicoListDto::new);
        return ResponseEntity.ok(medicos);
    }

    @Operation(summary = "Atualizar médico")
    @PutMapping
    public ResponseEntity<MedicoDetalheDto> atualizar(@Valid @RequestBody MedicoAtualizarDto dados) {
        Medico medico = this.medicoService.atualizar(dados);
        return ResponseEntity.ok(new MedicoDetalheDto(medico));
    }

    @Operation(summary = "Deletar médico")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        this.medicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Detalhar médico")
    @GetMapping("/{id}")
    public ResponseEntity<MedicoDetalheDto> detalhar(@PathVariable Long id) {
        Medico medico = this.medicoService.detalhar(id);
        return ResponseEntity.ok(new MedicoDetalheDto(medico));
    }
}
