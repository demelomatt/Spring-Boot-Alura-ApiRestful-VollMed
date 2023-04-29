package med.voll.api.service.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;
import med.voll.api.repository.PacienteRepository;

@Service
public class ValidadorPacienteAtivo implements InterfaceValidadorAgendamento{

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public void validar(ConsultaDto dados) {

        var isPacienteAtivo = this.pacienteRepository.findAtivoById(dados.idPaciente());
        if (!isPacienteAtivo) {
            throw new BusinessException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}
