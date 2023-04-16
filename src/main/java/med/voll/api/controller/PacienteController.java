package med.voll.api.controller;

import java.net.URI;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import med.voll.api.domain.paciente.Paciente;
import med.voll.api.dto.paciente.PacienteDetalheDto;
import med.voll.api.dto.paciente.PacienteAtualizarDto;
import med.voll.api.dto.paciente.PacienteDto;
import med.voll.api.dto.paciente.PacienteListDto;
import med.voll.api.repository.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<PacienteDetalheDto> cadastrar(@Valid @RequestBody PacienteDto dados, UriComponentsBuilder uriBuilder) {
        Paciente paciente = new Paciente(dados);
        this.repository.save(paciente);
        URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new PacienteDetalheDto(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<PacienteListDto>> listar(Pageable paginacao) {
        Page<PacienteListDto> paciente =  this.repository.findAllByAtivoTrue(paginacao).map(PacienteListDto::new);
        return ResponseEntity.ok(paciente);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PacienteDetalheDto> atualizar(@Valid @RequestBody PacienteAtualizarDto dados) {
        Paciente paciente = this.repository.getReferenceById(dados.id());
        paciente.atualizar(dados);
        return ResponseEntity.ok(new PacienteDetalheDto(paciente));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Paciente paciente = this.repository.getReferenceById(id);
        if (paciente.isAtivo()) {
            paciente.excluir();
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDetalheDto> detalhar(@PathVariable Long id) {
        Paciente paciente = this.repository.getReferenceById(id);
        if (paciente.isAtivo())
            return ResponseEntity.ok(new PacienteDetalheDto(paciente));
        return ResponseEntity.notFound().build();
    }

}