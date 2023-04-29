package med.voll.api.service.consulta;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.dto.consulta.ConsultaDetalheDto;
import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import med.voll.api.service.consulta.validacoes.InterfaceValidadorAgendamento;

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
        public ConsultaDetalheDto agendar(ConsultaDto dados) {
            Paciente paciente = this.pacienteRepository.findById(dados.idPaciente()).orElseThrow(() -> new EntityNotFoundException("Esse paciente não existe!"));
            Medico medico = this.getMedico(dados);

            if (medico == null) {
                throw new BusinessException("Não há médicos dessa especialidade disponíveis para a data informada.");
            }

            this.validadores.forEach(v -> v.validar(dados));

            Consulta consulta = new Consulta(paciente, medico, dados.date());
            this.consultaRepository.save(consulta);

            return new ConsultaDetalheDto(consulta);
        }

        private Medico getMedico(ConsultaDto dados) {
            if (dados.idMedico() != null) {
                return  this.medicoRepository.findById(dados.idMedico()).orElseThrow(() -> new EntityNotFoundException("Esse médico não existe!"));
            }

            if (dados.especialidade() == null) {
                throw new IllegalArgumentException("Necessário informar a especialidade do médico!");
            }

            return this.medicoRepository.getAvailableMedico(dados.especialidade(), dados.date());

    }
}
