package med.voll.api.service.consulta;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
        public void agendar(ConsultaDto dados) {
            Paciente paciente = pacienteRepository.findById(dados.idPaciente()).orElseThrow(() -> new EntityNotFoundException("Esse paciente não existe!"));
            Medico medico = this.getMedico(dados);

            if (medico == null) {
                throw new EntityNotFoundException("Não há médicos dessa especialidade disponíveis para a data informada.");
            }

            Consulta consulta = new Consulta(null, paciente, medico, dados.date());
            consultaRepository.save(consulta);
        }

        private Medico getMedico(ConsultaDto dados) {
            if (dados.idMedico() != null) {
                return  medicoRepository.findById(dados.idMedico()).orElseThrow(() -> new EntityNotFoundException("Esse médico não existe!"));
            }

            if (dados.especialidade() == null) {
                throw new IllegalArgumentException("Necessário informar a especialidade do médico!");
            }

            return medicoRepository.getAvailableMedico(dados.especialidade(), dados.date());

    }
}
