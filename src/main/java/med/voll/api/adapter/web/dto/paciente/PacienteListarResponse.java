package med.voll.api.adapter.web.dto.paciente;

import med.voll.api.application.dto.paciente.PacienteDto;
import med.voll.api.domain.entity.paciente.Paciente;

public record PacienteListarResponse(
        Long id,
        String nome,
        String email,
        String cpf) {

    public PacienteListarResponse(Paciente paciente) {
        this(paciente.getId() , paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }

    public PacienteListarResponse(PacienteDto dados) {
        this(dados.id(), dados.nome(), dados.email(), dados.cpf());
    }
}
