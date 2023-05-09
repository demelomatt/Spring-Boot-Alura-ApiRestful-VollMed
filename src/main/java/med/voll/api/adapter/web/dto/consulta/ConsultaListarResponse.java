package med.voll.api.adapter.web.dto.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import med.voll.api.adapter.web.dto.medico.MedicoListarResponse;
import med.voll.api.adapter.web.dto.paciente.PacienteListarResponse;
import med.voll.api.application.dto.consulta.ConsultaDto;
import med.voll.api.application.dto.consulta.ConsultaIdDto;
import med.voll.api.domain.entity.medico.Medico;
import med.voll.api.domain.entity.paciente.Paciente;

import java.time.LocalDateTime;

public record ConsultaListarResponse(
        Long id,
        @Valid MedicoListarResponse medico,
        @Valid PacienteListarResponse paciente,
        @Future @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime data
) {
    public ConsultaListarResponse(ConsultaDto consultaDto) {
        this(consultaDto.id(), new MedicoListarResponse(consultaDto.medico()), new PacienteListarResponse(consultaDto.paciente()), consultaDto.date());

    }
}
