package med.voll.api.application.service.consulta.validacoes;

import java.time.LocalDateTime;

import med.voll.api.application.dto.consulta.ConsultaIdDto;
import med.voll.api.domain.exception.PacienteNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Getter;

import med.voll.api.domain.exception.BusinessException;
import med.voll.api.infra.repository.jpa.ConsultaRepository;

@Service
public class ValidadorPacienteConsultaUnicaDia implements InterfaceValidadorAgendamento {

    @Getter private final int START_HOUR = 7;
    @Getter private final int END_HOUR = 18;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(ConsultaIdDto dados) {
        if (dados.date() == null || dados.idPaciente() == null)
            return;

        LocalDateTime primeiroHorario = dados.date().withHour(START_HOUR);
        LocalDateTime ultimoHorario = dados.date().withHour(END_HOUR);

        Boolean pacientePossuiOutraConsultaNoDia = this.consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
        if (pacientePossuiOutraConsultaNoDia) {
            throw new PacienteNotValidException("Paciente já possui uma consulta agendada nesse dia!");
        }
    }
}