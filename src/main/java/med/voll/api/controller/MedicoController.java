package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import med.voll.api.dto.medico.MedicoAtualizarDto;
import med.voll.api.dto.medico.MedicoDto;
import med.voll.api.dto.medico.MedicoListDto;
import med.voll.api.model.medico.Medico;
import med.voll.api.repository.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@Valid @RequestBody MedicoDto dados) {
        this.repository.save(new Medico(dados));
    }

    @GetMapping
    public Page<MedicoListDto> listar(@PageableDefault(size = 30, sort = {"nome", "especialidade"}) Pageable paginacao) {
        return this.repository.findAllByAtivoTrue(paginacao).map(MedicoListDto::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@Valid @RequestBody MedicoAtualizarDto dados) {
        Medico medico = this.repository.getReferenceById(dados.id());
        medico.atualizar(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id) {
        Medico medico = this.repository.getReferenceById(id);
        medico.excluir();
    }
}
