package med.voll.api.adapter.web.dto.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import med.voll.api.adapter.web.dto.endereco.EnderecoRequest;
import med.voll.api.domain.entity.medico.Especialidade;

public record MedicoCadastrarRequest(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "\\d{8,13}") String telefone,
        @NotBlank @Pattern(regexp = "\\d{4,6}") String crm,
        @NotNull Especialidade especialidade,
        @NotNull @Valid EnderecoRequest endereco) {
}
