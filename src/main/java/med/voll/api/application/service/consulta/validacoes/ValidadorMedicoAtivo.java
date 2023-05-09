package med.voll.api.application.service.consulta.validacoes;

import med.voll.api.application.dto.consulta.ConsultaIdDto;
import med.voll.api.domain.exception.BusinessException;
import med.voll.api.infra.repository.jpa.MedicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidadorMedicoAtivo implements InterfaceValidadorAgendamento{

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public void validar(ConsultaIdDto dados) {
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
