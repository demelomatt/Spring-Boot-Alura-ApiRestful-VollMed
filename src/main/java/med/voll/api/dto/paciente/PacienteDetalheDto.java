package med.voll.api.dto.paciente;

import med.voll.api.domain.paciente.Paciente;
import med.voll.api.dto.endereco.EnderecoDto;

public record PacienteDetalheDto(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        EnderecoDto endereco
) {
    public PacienteDetalheDto(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), new EnderecoDto(paciente.getEndereco()));
    }
}
