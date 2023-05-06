package med.voll.api.service.consulta.validacoes;

import med.voll.api.dto.consulta.ConsultaAgendarRequest;

public interface InterfaceValidadorAgendamento {
    void validar(ConsultaAgendarRequest dados);
}
