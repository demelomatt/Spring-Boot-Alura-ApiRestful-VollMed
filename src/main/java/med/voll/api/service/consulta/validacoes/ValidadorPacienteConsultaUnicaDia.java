package med.voll.api.service.consulta.validacoes;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;
import med.voll.api.repository.ConsultaRepository;

@Service
public class ValidadorPacienteConsultaUnicaDia implements InterfaceValidadorAgendamento {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(ConsultaDto dados) {
        LocalDateTime primeiroHorario = dados.date().withHour(7);
        LocalDateTime ultimoHorario = dados.date().withHour(18);
        Boolean pacientePossuiOutraConsultaNoDia = this.consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
        if (pacientePossuiOutraConsultaNoDia) {
            throw new BusinessException("Paciente já possui uma consulta agendada nesse dia!");
        }
    }
}
