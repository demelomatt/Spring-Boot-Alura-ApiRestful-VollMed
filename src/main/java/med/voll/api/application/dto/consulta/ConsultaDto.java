package med.voll.api.application.dto.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import med.voll.api.adapter.web.dto.consulta.ConsultaAgendarRequest;
import med.voll.api.domain.entity.consulta.Consulta;
import med.voll.api.domain.entity.medico.Medico;
import med.voll.api.domain.entity.paciente.Paciente;

import java.time.LocalDateTime;

public record ConsultaDto(
        Long id,
        Paciente paciente,
        Medico medico,
        @Future @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime date
) {
    public ConsultaDto(Consulta consulta) {
        this(consulta.getId(), consulta.getPaciente(), consulta.getMedico(), consulta.getData());
    }

}
