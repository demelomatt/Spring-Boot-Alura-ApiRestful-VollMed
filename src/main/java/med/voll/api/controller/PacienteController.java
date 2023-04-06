package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import med.voll.api.dto.paciente.PacienteAtualizarDto;
import med.voll.api.dto.paciente.PacienteDto;
import med.voll.api.dto.paciente.PacienteListDto;
import med.voll.api.model.paciente.Paciente;
import med.voll.api.repository.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@Valid @RequestBody PacienteDto dados) {
        this.repository.save(new Paciente(dados));
    }

    @GetMapping
    public Page<PacienteListDto> listar(Pageable paginacao) {
        return this.repository.findAllByAtivoTrue(paginacao).map(PacienteListDto::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@Valid @RequestBody PacienteAtualizarDto dados) {
        Paciente paciente = this.repository.getReferenceById(dados.id());
        paciente.atualizar(dados);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id) {
        Paciente paciente = this.repository.getReferenceById(id);
        paciente.excluir();
    }

}