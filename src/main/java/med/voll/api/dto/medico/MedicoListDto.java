package med.voll.api.dto.medico;

import med.voll.api.model.medico.Especialidade;
import med.voll.api.model.medico.Medico;

public record MedicoListDto(
        Long id,
        String nome,
        String email,
        String crm,
        Especialidade especialidade) {

    public MedicoListDto(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
