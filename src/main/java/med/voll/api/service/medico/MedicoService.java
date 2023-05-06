package med.voll.api.service.medico;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import med.voll.api.domain.medico.Medico;
import med.voll.api.dto.medico.MedicoAtualizarRequest;
import med.voll.api.repository.MedicoRepository;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    public void cadastrar(Medico medico) {
        this.medicoRepository.save(medico);
    }

    public Page<Medico> listar(Pageable paginacao) {
        Page<Medico> medicos = this.medicoRepository.findAllByAtivoTrue(paginacao);
        return medicos;
    }

    @Transactional
    public Medico atualizar(MedicoAtualizarRequest dados) {
        Medico medico = this.medicoRepository.getReferenceById(dados.id());
        medico.atualizar(dados);
        return medico;
    }

    @Transactional
    public void deletar(Long id) {
        Medico medico = this.medicoRepository.getReferenceById(id);
        if (medico.isAtivo()) {
            medico.excluir();
        }

        throw new EntityNotFoundException("O médico informado não está ativo.");
    }

    public Medico detalhar(Long id) {
        Medico medico = this.medicoRepository.getReferenceById(id);
        if (medico.isAtivo()) {
            return medico;
        }

        throw new EntityNotFoundException("O médico informado não está ativo.");
    }
}
