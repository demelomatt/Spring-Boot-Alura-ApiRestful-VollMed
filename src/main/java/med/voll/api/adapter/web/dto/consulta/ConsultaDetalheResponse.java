package med.voll.api.adapter.web.dto.consulta;

import java.time.LocalDateTime;

import jakarta.validation.Valid;
import med.voll.api.adapter.web.dto.medico.MedicoDetalheResponse;
import med.voll.api.adapter.web.dto.medico.MedicoListarResponse;
import med.voll.api.adapter.web.dto.paciente.PacienteDetalheResponse;
import med.voll.api.adapter.web.dto.paciente.PacienteListarResponse;
import med.voll.api.application.dto.consulta.ConsultaDto;
import med.voll.api.application.dto.consulta.ConsultaIdDto;

public record ConsultaDetalheResponse(
        Long id,
        @Valid MedicoListarResponse medico,
        @Valid PacienteListarResponse paciente,
        LocalDateTime data
) {
    public ConsultaDetalheResponse(ConsultaDto consulta) {
        this(consulta.id(), new MedicoListarResponse(consulta.medico()), new PacienteListarResponse(consulta.paciente()), consulta.date());
    }
}
