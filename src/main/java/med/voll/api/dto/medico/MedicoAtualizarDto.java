package med.voll.api.dto.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import med.voll.api.dto.endereco.EnderecoDto;

public record MedicoAtualizarDto(
        @NotNull Long id,
        String nome,
        @Pattern(regexp = "\\d{8,13}") String telefone,
        @Valid EnderecoDto endereco) {
}
