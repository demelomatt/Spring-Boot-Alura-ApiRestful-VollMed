package med.voll.api.adapter.web.dto.medico;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import med.voll.api.application.dto.medico.MedicoDto;
import med.voll.api.domain.entity.medico.Especialidade;
import med.voll.api.domain.entity.medico.Medico;

public record MedicoListarResponse(
        Long id,
        String nome,
        @Email String email,
        @Pattern(regexp = "\\d{4,6}") String crm,
        Especialidade especialidade) {

    public MedicoListarResponse(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
    public MedicoListarResponse(MedicoDto dados) {
        this(dados.id(), dados.nome(), dados.email(), dados.crm(), dados.especialidade());
    }


}
