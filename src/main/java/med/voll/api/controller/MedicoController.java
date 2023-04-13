package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import med.voll.api.dto.medico.MedicoDetalheDto;
import med.voll.api.dto.medico.MedicoAtualizarDto;
import med.voll.api.dto.medico.MedicoDto;
import med.voll.api.dto.medico.MedicoListDto;
import med.voll.api.domain.medico.Medico;
import med.voll.api.repository.MedicoRepository;


@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<MedicoDetalheDto> cadastrar(@Valid @RequestBody MedicoDto dados, UriComponentsBuilder uriBuilder) {
        Medico medico = new Medico(dados);
        this.repository.save(medico);
        URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new MedicoDetalheDto(medico));
    }

    @GetMapping
    public ResponseEntity<Page<MedicoListDto>> listar(@PageableDefault(size = 30, sort = {"nome", "especialidade"}) Pageable paginacao) {
        Page<MedicoListDto> medicos = this.repository.findAllByAtivoTrue(paginacao).map(MedicoListDto::new);
        return ResponseEntity.ok(medicos);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<MedicoDetalheDto> atualizar(@Valid @RequestBody MedicoAtualizarDto dados) {
        Medico medico = this.repository.getReferenceById(dados.id());
        medico.atualizar(dados);
        return ResponseEntity.ok(new MedicoDetalheDto(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Medico medico = this.repository.getReferenceById(id);
        if (medico.isAtivo()) {
            medico.excluir();
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDetalheDto> detalhar(@PathVariable Long id) {
        Medico medico = this.repository.getReferenceById(id);
        if (medico.isAtivo()) {
            return ResponseEntity.ok(new MedicoDetalheDto(medico));
        }

        return ResponseEntity.notFound().build();
    }
}
