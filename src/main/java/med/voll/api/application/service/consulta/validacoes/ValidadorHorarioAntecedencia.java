package med.voll.api.application.service.consulta.validacoes;

import lombok.Getter;

import med.voll.api.application.dto.consulta.ConsultaIdDto;
import med.voll.api.domain.exception.BusinessException;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Service
public class ValidadorHorarioAntecedencia implements InterfaceValidadorAgendamento{

    private final int MINUTES = 30;

    @Override
    public void validar(ConsultaIdDto dados) {
        if (dados.date() == null)
                return;

        LocalDateTime dataConsulta = dados.date();
        LocalDateTime dataAtual = LocalDateTime.now();
        Long diffMin = Duration.between(dataAtual, dataConsulta).toMinutes() + 1;

        if (diffMin < MINUTES) {
            throw new BusinessException("A consulta deve ser agendada com pelo menos 30min de antecedência!");
        }
    }

}
