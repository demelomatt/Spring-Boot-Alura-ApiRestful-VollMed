package med.voll.api.dto.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import med.voll.api.dto.endereco.EnderecoDto;

public record PacienteDto(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "\\d{8,11}") String telefone,
        @NotBlank @Pattern(regexp = "\\d{11}") String cpf,
        @NotNull @Valid EnderecoDto endereco) {
}
