package med.voll.api.application.service.medico;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import med.voll.api.application.dto.medico.MedicoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import med.voll.api.domain.entity.medico.Medico;
import med.voll.api.infra.repository.jpa.MedicoRepository;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    public MedicoDto cadastrar(MedicoDto dados) {
        Medico medico = new Medico(dados);
        return new MedicoDto(this.medicoRepository.save(medico));
    }

    @Transactional
    public MedicoDto atualizar(MedicoDto dados) {
        Medico medico = this.medicoRepository.getReferenceById(dados.id());
        medico.atualizar(dados);
        return new MedicoDto(medico);
    }

    public Page<MedicoDto> listar(Pageable paginacao) {
        return this.medicoRepository.findAllByAtivoTrue(paginacao).map(MedicoDto::new);
    }

    @Transactional
    public void deletar(Long id) {
        Medico medico = this.medicoRepository.getReferenceById(id);
        if (medico.isAtivo()) {
            medico.excluir();
            return;
        }

        throw new EntityNotFoundException("O médico informado não está ativo.");
    }

    public MedicoDto detalhar(Long id) {
        Medico medico = this.medicoRepository.getReferenceById(id);
        if (medico.isAtivo()) {
            return new MedicoDto(medico);
        }

        throw new EntityNotFoundException("O médico informado não está ativo.");
    }
}
