package med.voll.api.service.paciente;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import med.voll.api.domain.paciente.Paciente;
import med.voll.api.dto.paciente.PacienteAtualizarRequest;
import med.voll.api.repository.PacienteRepository;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    public void cadastrar(Paciente paciente) {
        this.pacienteRepository.save(paciente);
    }

    public Page<Paciente> listar(Pageable paginacao) {
        Page<Paciente> pacientes = this.pacienteRepository.findAllByAtivoTrue(paginacao);
        return pacientes;
    }

    @Transactional
    public Paciente atualizar(PacienteAtualizarRequest dados) {
        Paciente paciente = this.pacienteRepository.getReferenceById(dados.id());
        paciente.atualizar(dados);
        return paciente;
    }

    @Transactional
    public void deletar(Long id) {
        Paciente paciente = this.pacienteRepository.getReferenceById(id);
        if (paciente.isAtivo()) {
            paciente.excluir();
        }

        throw new EntityNotFoundException("O paciente informado não está ativo.");
    }

    public Paciente detalhar(Long id) {
        Paciente paciente = this.pacienteRepository.getReferenceById(id);
        if (paciente.isAtivo()) {
            return paciente;
        }

        throw new EntityNotFoundException("O paciente informado não está ativo.");
    }
}