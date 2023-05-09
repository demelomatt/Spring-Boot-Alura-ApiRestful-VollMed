package med.voll.api.adapter.web.dto.consulta;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import med.voll.api.domain.entity.medico.Especialidade;

public record ConsultaAgendarRequest(
        @NotNull Long idPaciente,
        Long idMedico,
        @NotNull @Future @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime date,
        Especialidade especialidade
) {}

