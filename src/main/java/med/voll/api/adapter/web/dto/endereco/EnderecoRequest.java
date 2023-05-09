package med.voll.api.adapter.web.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import med.voll.api.domain.entity.util.Endereco;

public record EnderecoRequest(
        @NotBlank String logradouro,
        @NotBlank String bairro,
        @NotBlank @Pattern(regexp = "\\d{8}") String cep,
        @NotBlank String cidade,
        @NotBlank @Pattern(regexp = "[a-zA-Z]{2}") String uf,
        String numero,
        String complemento) {
    public EnderecoRequest(Endereco endereco) {
        this(endereco.getLogradouro(), endereco.getBairro(), endereco.getCep(), endereco.getCidade(), endereco.getUf(), endereco.getNumero(), endereco.getComplemento());
    }
}
