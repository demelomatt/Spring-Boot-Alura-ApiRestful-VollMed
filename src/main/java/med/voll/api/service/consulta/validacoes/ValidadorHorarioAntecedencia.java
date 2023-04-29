package med.voll.api.service.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;

@Service
public class ValidadorHorarioAntecedencia implements InterfaceValidadorAgendamento{

    @Override
    public void validar(ConsultaDto dados) {
        LocalDateTime dataConsulta = dados.date();
        LocalDateTime dataAtual = LocalDateTime.now();
        Long diffMin = Duration.between(dataAtual, dataConsulta).toMinutes();

        if (diffMin < 30) {
            throw new BusinessException("A consulta deve ser agendada com pelo menos 30min de antecedência!");
        }
    }
}
