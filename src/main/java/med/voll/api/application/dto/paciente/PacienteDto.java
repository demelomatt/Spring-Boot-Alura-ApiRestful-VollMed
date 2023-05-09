package med.voll.api.application.dto.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import med.voll.api.adapter.web.dto.endereco.EnderecoRequest;
import med.voll.api.adapter.web.dto.paciente.PacienteAtualizarRequest;
import med.voll.api.adapter.web.dto.paciente.PacienteCadastrarRequest;
import med.voll.api.domain.entity.paciente.Paciente;

public record PacienteDto(
        Long id,
        String nome,
        @Email String email,
        @Pattern(regexp = "\\d{8,11}") String telefone,
        @Pattern(regexp = "\\d{11}") String cpf,
        @Valid EnderecoRequest endereco
) {
    public PacienteDto(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), new EnderecoRequest(paciente.getEndereco()));
    }

    public PacienteDto(PacienteCadastrarRequest dados) {
        this(null, dados.nome(), dados.email(), dados.telefone(), dados.cpf(), dados.endereco());
    }

    public PacienteDto(PacienteAtualizarRequest dados) {
        this(dados.id(), dados.nome(), null, dados.telefone(), null, dados.endereco());
    }
}
