package med.voll.api.application.service.consulta;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import med.voll.api.application.dto.consulta.ConsultaDto;
import med.voll.api.application.dto.consulta.ConsultaIdDto;
import med.voll.api.application.service.consulta.validacoes.InterfaceValidadorAgendamento;
import med.voll.api.domain.entity.consulta.Consulta;
import med.voll.api.domain.entity.medico.Medico;
import med.voll.api.domain.entity.paciente.Paciente;
import med.voll.api.domain.exception.BusinessException;
import med.voll.api.infra.repository.jpa.ConsultaRepository;
import med.voll.api.infra.repository.jpa.MedicoRepository;
import med.voll.api.infra.repository.jpa.PacienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private List<InterfaceValidadorAgendamento> validadores;

    private Medico getMedico(ConsultaIdDto dados) {
        if (dados.idMedico() != null) {
            return  this.medicoRepository.findById(dados.idMedico()).orElseThrow(() -> new EntityNotFoundException("Esse médico não existe!"));
        }

        if (dados.especialidade() == null) {
            throw new IllegalArgumentException("Necessário informar a especialidade do médico!");
        }

        Medico medico =  this.medicoRepository.getAvailableMedico(dados.especialidade(), dados.date());

        if (medico == null) {
            throw new BusinessException("Não há médicos dessa especialidade disponíveis para a data informada.");
        }

        return medico;

    }

    @Transactional
        public ConsultaDto agendar(ConsultaIdDto dados) {

            Paciente paciente = this.pacienteRepository.findById(dados.idPaciente()).orElseThrow(() -> new EntityNotFoundException("Esse paciente não existe!"));
            Medico medico = this.getMedico(dados);

            this.validadores.forEach((v) -> v.validar(dados));
            Consulta consulta = new Consulta(paciente, medico, dados.date());
            this.consultaRepository.save(consulta);

            return new ConsultaDto(consulta);
        }

    @Transactional
    public ConsultaDto atualizar(ConsultaIdDto dados) {
        Consulta consulta = this.consultaRepository.getReferenceById(dados.id());
        Medico medico = this.getMedico(dados);

        this.validadores.forEach((v) -> v.validar(dados));

        consulta.atualizar(new ConsultaDto(dados.id(), null, medico, dados.date()));

        return new ConsultaDto(consulta);
    }

    @Transactional
    public void deletar(Long id) {
        Consulta consulta = this.consultaRepository.getReferenceById(id);
        if (consulta.isAtivo()) {
            consulta.excluir();
            return;
        }

        throw new EntityNotFoundException("A consulta informada não está ativa.");
    }

    public Page<ConsultaDto> listar(Pageable paginacao) {
        return this.consultaRepository.findAllByAtivoTrue(paginacao).map(ConsultaDto::new);
    }

    public ConsultaDto detalhar(Long id) {
        Consulta consulta = this.consultaRepository.getReferenceById(id);
        if (consulta.isAtivo()) {
            return new ConsultaDto(this.consultaRepository.getReferenceById(id));
        }

        throw new EntityNotFoundException("A consulta informada não está ativa.");

    }

}
