package med.voll.api.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoDto(
        @NotBlank String logradouro,
        @NotBlank String bairro,
        @NotBlank @Pattern(regexp = "\\d{8}") String cep,
        @NotBlank String cidade,
        @NotBlank @Pattern(regexp = "[a-zA-Z]{2}") String uf,
        String numero,
        String complemento) {
}
