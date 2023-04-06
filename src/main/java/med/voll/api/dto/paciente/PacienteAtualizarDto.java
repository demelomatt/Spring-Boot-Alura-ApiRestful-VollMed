package med.voll.api.dto.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import med.voll.api.dto.endereco.EnderecoDto;

public record PacienteAtualizarDto(
        @NotNull Long id,
        String nome,
        @Pattern(regexp = "\\d{8,11}") String telefone,
        @Valid EnderecoDto endereco) {
}
