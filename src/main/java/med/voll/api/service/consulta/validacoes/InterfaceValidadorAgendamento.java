package med.voll.api.service.consulta.validacoes;

import med.voll.api.dto.consulta.ConsultaDto;

public interface InterfaceValidadorAgendamento {
    void validar(ConsultaDto dados);
}
