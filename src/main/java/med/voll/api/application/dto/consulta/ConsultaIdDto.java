package med.voll.api.application.dto.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import med.voll.api.adapter.web.dto.consulta.ConsultaAgendarRequest;
import med.voll.api.adapter.web.dto.consulta.ConsultaAtualizarRequest;
import med.voll.api.domain.entity.consulta.Consulta;
import med.voll.api.domain.entity.medico.Especialidade;

import java.time.LocalDateTime;

public record ConsultaIdDto(
        Long id,
        Long idPaciente,
        Long idMedico,
        @Future @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime date,
        Especialidade especialidade
) {
    public ConsultaIdDto(ConsultaAtualizarRequest dados) {
        this(dados.id(), null, dados.idMedico(), dados.date(), dados.especialidade());
    }

    public ConsultaIdDto(ConsultaAgendarRequest dados) {
        this(null, dados.idPaciente(), dados.idMedico(), dados.date(), dados.especialidade());
    }

    public ConsultaIdDto(Consulta consulta) {
        this(consulta.getId(), consulta.getPaciente().getId(), consulta.getMedico().getId(), consulta.getData(), consulta.getMedico().getEspecialidade());
    }
}
