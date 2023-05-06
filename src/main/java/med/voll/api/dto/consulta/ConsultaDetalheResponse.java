package med.voll.api.dto.consulta;

import java.time.LocalDateTime;

import med.voll.api.domain.consulta.Consulta;

public record ConsultaDetalheResponse(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {
    public ConsultaDetalheResponse(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}
