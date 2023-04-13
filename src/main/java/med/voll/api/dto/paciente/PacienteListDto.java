package med.voll.api.dto.paciente;

import med.voll.api.domain.paciente.Paciente;

public record PacienteListDto(
        Long id,
        String nome,
        String email,
        String cpf) {

    public PacienteListDto(Paciente paciente) {
        this(paciente.getId() , paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
