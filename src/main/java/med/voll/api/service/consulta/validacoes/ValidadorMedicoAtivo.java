package med.voll.api.service.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;
import med.voll.api.repository.MedicoRepository;

@Service
public class ValidadorMedicoAtivo implements InterfaceValidadorAgendamento{

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public void validar(ConsultaDto dados) {
        //escolha do médico opcional
        if (dados.idMedico() == null) {
            return;
        }

        boolean isMedicoAtivo = this.medicoRepository.findAtivoById(dados.idMedico());
        if (!isMedicoAtivo) {
            throw new BusinessException("Consulta não pode ser agendada com médico excluído");
        }
    }
}
