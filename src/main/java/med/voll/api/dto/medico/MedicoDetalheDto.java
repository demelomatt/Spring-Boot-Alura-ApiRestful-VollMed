package med.voll.api.dto.medico;

import med.voll.api.model.medico.Especialidade;
import med.voll.api.model.medico.Medico;
import med.voll.api.model.util.Endereco;

public record MedicoDetalheDto(
        Long id,
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        Endereco endereco
) {
    public MedicoDetalheDto(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco());
    }
}
