package med.voll.api.adapter.web.dto.paciente;

import med.voll.api.application.dto.paciente.PacienteDto;
import med.voll.api.domain.entity.paciente.Paciente;
import med.voll.api.adapter.web.dto.endereco.EnderecoRequest;

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

    public PacienteDetalheResponse(PacienteDto dados) {
        this(dados.id(), dados.nome(), dados.email(), dados.telefone(), dados.cpf(), dados.endereco());
    }


}
