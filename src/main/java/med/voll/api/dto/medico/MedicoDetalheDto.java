package med.voll.api.dto.medico;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.dto.endereco.EnderecoDto;

public record MedicoDetalheDto(
        Long id,
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        EnderecoDto endereco
) {
    public MedicoDetalheDto(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(), medico.getEspecialidade(), new EnderecoDto(medico.getEndereco()));
    }
}
