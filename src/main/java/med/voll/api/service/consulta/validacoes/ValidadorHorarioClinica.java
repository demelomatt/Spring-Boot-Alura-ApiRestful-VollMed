package med.voll.api.service.consulta.validacoes;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;

@Service
public class ValidadorHorarioClinica implements InterfaceValidadorAgendamento{

    @Override
    public void validar(ConsultaDto dados) {
        LocalDateTime dataConsulta = dados.date();
        LocalTime horarioConsulta = dataConsulta.toLocalTime();
        LocalTime startTime = LocalTime.of(6, 59);
        LocalTime endTime = LocalTime.of(18, 1);

        boolean isDiaTrabalho = !dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean isHorarioTrabalho = horarioConsulta.isAfter(startTime) && horarioConsulta.isBefore(endTime);

        if (!isDiaTrabalho || !isHorarioTrabalho)  {
            throw new BusinessException("A consulta deve ser agendada dentro do horário de funcionamento da clínica!");
        }
    }
}
