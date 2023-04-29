package med.voll.api.service.consulta.validacoes;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;

@Service
public class ValidadorHorarioClinica implements InterfaceValidadorAgendamento{

    @Override
    public void validar(ConsultaDto dados) {
        LocalDateTime dataConsulta = dados.date();
        boolean isDiaTrabalho = !dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean isHorarioTrabalho = ! (dataConsulta.getHour() < 7 && dataConsulta.getHour() > 18) ;

        if (!isDiaTrabalho && !isHorarioTrabalho)  {
            throw new BusinessException("A consulta deve ser agendada dentro do horário de funcionamento da clínica!");
        }
    }
}
