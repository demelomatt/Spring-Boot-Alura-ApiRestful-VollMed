package med.voll.api.application.service.consulta.validacoes;

import med.voll.api.application.dto.consulta.ConsultaIdDto;
import med.voll.api.domain.exception.BusinessException;
import med.voll.api.infra.repository.jpa.PacienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidadorPacienteAtivo implements InterfaceValidadorAgendamento{

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public void validar(ConsultaIdDto dados) {
        if (dados.idPaciente() == null)
            return;

        boolean isPacienteAtivo = this.pacienteRepository.findAtivoById(dados.idPaciente());
        if (!isPacienteAtivo) {
            throw new BusinessException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}
