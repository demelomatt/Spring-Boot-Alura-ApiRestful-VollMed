package med.voll.api.dto.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import med.voll.api.dto.endereco.EnderecoDto;
import med.voll.api.model.medico.Especialidade;

public record MedicoDto(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "\\d{8,13}") String telefone,
        @NotBlank @Pattern(regexp = "\\d{4,6}") String crm,
        @NotNull Especialidade especialidade,
        @NotNull @Valid EnderecoDto endereco) {

}
