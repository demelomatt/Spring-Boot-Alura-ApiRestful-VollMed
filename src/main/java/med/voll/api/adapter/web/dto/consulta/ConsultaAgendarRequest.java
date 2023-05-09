package med.voll.api.adapter.web.dto.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import med.voll.api.domain.entity.medico.Especialidade;

import java.time.LocalDateTime;

public record ConsultaAgendarRequest(
        @NotNull Long idPaciente,
        Long idMedico,
        @NotNull @Future @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime date,
        Especialidade especialidade
) {}

