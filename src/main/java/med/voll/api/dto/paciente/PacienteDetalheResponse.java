package med.voll.api.dto.paciente;

import med.voll.api.domain.paciente.Paciente;
import med.voll.api.dto.endereco.EnderecoRequest;

public record PacienteDetalheResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        EnderecoRequest endereco
) {
    public PacienteDetalheResponse(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), new EnderecoRequest(paciente.getEndereco()));
    }
}
