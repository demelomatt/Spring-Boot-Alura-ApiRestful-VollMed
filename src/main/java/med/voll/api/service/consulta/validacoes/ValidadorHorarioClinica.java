package med.voll.api.service.consulta.validacoes;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import lombok.Getter;

import med.voll.api.dto.consulta.ConsultaAgendarRequest;
import med.voll.api.exception.BusinessException;

@Getter
@Service
public class ValidadorHorarioClinica implements InterfaceValidadorAgendamento {

    private final LocalTime START_TIME = LocalTime.of(6, 59);
    private final LocalTime END_TIME = LocalTime.of(18, 1);

    @Override
    public void validar(ConsultaAgendarRequest dados) {
        LocalDateTime dataConsulta = dados.date();
        LocalTime horarioConsulta = dataConsulta.toLocalTime();

        boolean isDiaTrabalho = !dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean isHorarioTrabalho = horarioConsulta.isAfter(START_TIME) && horarioConsulta.isBefore(END_TIME);

        if (!isDiaTrabalho || !isHorarioTrabalho)  {
            throw new BusinessException("A consulta deve ser agendada dentro do horário de funcionamento da clínica!");
        }
    }
}
