package med.voll.api.application.service.consulta.validacoes;

import lombok.Getter;

import med.voll.api.application.dto.consulta.ConsultaIdDto;
import med.voll.api.domain.exception.BusinessException;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Service
public class ValidadorHorarioClinica implements InterfaceValidadorAgendamento {

    private final LocalTime START_TIME = LocalTime.of(6, 59);
    private final LocalTime END_TIME = LocalTime.of(18, 1);

    @Override
    public void validar(ConsultaIdDto dados) {
        if (dados.date() == null)
            return;

        LocalDateTime dataConsulta = dados.date();
        LocalTime horarioConsulta = dataConsulta.toLocalTime();

        boolean isDiaTrabalho = !dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean isHorarioTrabalho = horarioConsulta.isAfter(START_TIME) && horarioConsulta.isBefore(END_TIME);

        if (!isDiaTrabalho || !isHorarioTrabalho)  {
            throw new BusinessException("A consulta deve ser agendada dentro do horário de funcionamento da clínica!");
        }
    }
}
