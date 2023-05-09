package med.voll.api.application.service.consulta.validacoes;

import med.voll.api.application.dto.consulta.ConsultaIdDto;
import med.voll.api.domain.exception.BusinessException;
import med.voll.api.infra.repository.jpa.ConsultaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidadorMedicoDisponivel implements InterfaceValidadorAgendamento{

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(ConsultaIdDto dados) {
        if (dados.idMedico() == null || dados.date() == null)
            return;

        boolean medicoHasConsulta = this.consultaRepository.existsByMedicoIdAndData(dados.idMedico(), dados.date());
        if (medicoHasConsulta) {
            throw new BusinessException("Médico já possui outra consulta agendada nesse mesmo horário!");
        }
    }
}
