package med.voll.api.service.consulta.validacoes;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Getter;

import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;
import med.voll.api.repository.ConsultaRepository;

@Service
public class ValidadorPacienteConsultaUnicaDia implements InterfaceValidadorAgendamento {

    @Getter private final int START_HOUR = 7;
    @Getter private final int END_HOUR = 18;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(ConsultaDto dados) {
        LocalDateTime primeiroHorario = dados.date().withHour(START_HOUR);
        LocalDateTime ultimoHorario = dados.date().withHour(END_HOUR);

        Boolean pacientePossuiOutraConsultaNoDia = this.consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
        if (pacientePossuiOutraConsultaNoDia) {
            throw new BusinessException("Paciente já possui uma consulta agendada nesse dia!");
        }
    }
}