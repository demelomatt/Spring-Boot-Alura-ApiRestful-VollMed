package med.voll.api.application.service.consulta;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import med.voll.api.application.dto.consulta.ConsultaDto;
import med.voll.api.application.dto.consulta.ConsultaIdDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import med.voll.api.domain.entity.consulta.Consulta;
import med.voll.api.domain.entity.medico.Medico;
import med.voll.api.domain.entity.paciente.Paciente;
import med.voll.api.domain.exception.BusinessException;
import med.voll.api.infra.repository.jpa.ConsultaRepository;
import med.voll.api.infra.repository.jpa.MedicoRepository;
import med.voll.api.infra.repository.jpa.PacienteRepository;
import med.voll.api.application.service.consulta.validacoes.InterfaceValidadorAgendamento;

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

    @Transactional
        public ConsultaDto agendar(ConsultaIdDto dados) {
            Paciente paciente = this.pacienteRepository.findById(dados.idPaciente()).orElseThrow(() -> new EntityNotFoundException("Esse paciente não existe!"));
            Medico medico = this.getMedico(dados);

            this.validadores.forEach(v -> v.validar(dados));

            Consulta consulta = new Consulta(paciente, medico, dados.date());
            this.consultaRepository.save(consulta);

            return new ConsultaDto(consulta);
        }


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

    public ConsultaDto atualizar(ConsultaIdDto dados) {
        Consulta consulta = this.consultaRepository.getReferenceById(dados.id());
        Paciente paciente = this.pacienteRepository.getReferenceById(dados.idPaciente());
        Medico medico = this.getMedico(dados);

        ConsultaIdDto consultaIdDto = new ConsultaIdDto(null, paciente.getId(), medico.getId(), dados.date(), medico.getEspecialidade());

        this.validadores.forEach(v -> v.validar(consultaIdDto));

        consulta.atualizar(new ConsultaDto(dados.id(), paciente, medico, dados.date()));

        return new ConsultaDto(consulta);
    }

    @Transactional
    public void deletar(Long id) {
        Consulta consulta = this.consultaRepository.getReferenceById(id);
        if (consulta.isAtivo()) {
            consulta.excluir();
        }

        throw new EntityNotFoundException("A consulta informada não está ativa.");
    }
}
