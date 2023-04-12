package med.voll.api.dto.paciente;

import med.voll.api.model.paciente.Paciente;
import med.voll.api.model.util.Endereco;

public record PacienteDetalheDto(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco
) {
    public PacienteDetalheDto(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), (paciente.getEndereco()));
    }
}
