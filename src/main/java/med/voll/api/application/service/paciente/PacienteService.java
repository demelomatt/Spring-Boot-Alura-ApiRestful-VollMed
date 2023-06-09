package med.voll.api.application.service.paciente;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import med.voll.api.application.dto.paciente.PacienteDto;
import med.voll.api.domain.entity.paciente.Paciente;
import med.voll.api.infra.repository.jpa.PacienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    public PacienteDto cadastrar(PacienteDto dados) {
        Paciente paciente = new Paciente(dados);
        this.pacienteRepository.save(paciente);
        return new PacienteDto(paciente);
    }

    @Transactional
    public PacienteDto atualizar(PacienteDto dados) {
        Paciente paciente = this.pacienteRepository.getReferenceById(dados.id());
        paciente.atualizar(dados);
        return new PacienteDto(paciente);
    }

    @Transactional
    public void deletar(Long id) {
        Paciente paciente = this.pacienteRepository.getReferenceById(id);
        if (paciente.isAtivo()) {
            paciente.excluir();
            return;
        }

        throw new EntityNotFoundException("O paciente informado não está ativo.");
    }

    public Page<PacienteDto> listar(Pageable paginacao) {
        return this.pacienteRepository.findAllByAtivoTrue(paginacao).map(PacienteDto::new);
    }

    public PacienteDto detalhar(Long id) {
        Paciente paciente = this.pacienteRepository.getReferenceById(id);
        if (paciente.isAtivo()) {
            return new PacienteDto(paciente);
        }

        throw new EntityNotFoundException("O paciente informado não está ativo.");
    }
}