package med.voll.api.application.dto.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import med.voll.api.adapter.web.dto.endereco.EnderecoRequest;
import med.voll.api.adapter.web.dto.medico.MedicoAtualizarRequest;
import med.voll.api.adapter.web.dto.medico.MedicoCadastrarRequest;
import med.voll.api.domain.entity.medico.Especialidade;
import med.voll.api.domain.entity.medico.Medico;

public record MedicoDto(
        Long id,
        String nome,
        @Email String email,
        @Pattern(regexp = "\\d{8,13}") String telefone,
        @Pattern(regexp = "\\d{4,6}") String crm,
        Especialidade especialidade,
        @Valid EnderecoRequest endereco
) {

    public MedicoDto(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(), medico.getEspecialidade(), new EnderecoRequest(medico.getEndereco()));
    }

    public MedicoDto(MedicoCadastrarRequest dados) {
        this(null, dados.nome(), dados.email(), dados.telefone(), dados.crm(), dados.especialidade(), dados.endereco());
    }

    public MedicoDto(MedicoAtualizarRequest dados) {
        this(dados.id(), dados.nome(), null, dados.telefone(), null, null, dados.endereco());
    }

}