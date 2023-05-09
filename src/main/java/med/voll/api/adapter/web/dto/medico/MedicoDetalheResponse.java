package med.voll.api.adapter.web.dto.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import med.voll.api.application.dto.medico.MedicoDto;
import med.voll.api.domain.entity.medico.Especialidade;
import med.voll.api.domain.entity.medico.Medico;
import med.voll.api.adapter.web.dto.endereco.EnderecoRequest;
import med.voll.api.domain.entity.paciente.Paciente;

public record MedicoDetalheResponse(
        Long id,
        String nome,
        @Email String email,
        @Pattern(regexp = "\\d{8,13}") String telefone,
        @Pattern(regexp = "\\d{4,6}") String crm,
        Especialidade especialidade,
        @Valid EnderecoRequest endereco
) {

    public MedicoDetalheResponse(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(),medico.getEspecialidade(), new EnderecoRequest(medico.getEndereco()));
    }

    public MedicoDetalheResponse(MedicoDto dados) {
        this(dados.id(), dados.nome(), dados.email(), dados.telefone(), dados.crm(), dados.especialidade(), dados.endereco());
    }
}
