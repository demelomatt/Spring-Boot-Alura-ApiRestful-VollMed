package med.voll.api.application.service.consulta.validacoes;

import med.voll.api.application.dto.consulta.ConsultaIdDto;

public interface InterfaceValidadorAgendamento {
    void validar(ConsultaIdDto dados);
}
