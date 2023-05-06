package med.voll.api.service.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.dto.consulta.ConsultaAgendarRequest;
import med.voll.api.exception.BusinessException;
import med.voll.api.repository.ConsultaRepository;

@Service
public class ValidadorMedicoDisponivel implements InterfaceValidadorAgendamento{

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(ConsultaAgendarRequest dados) {
        boolean medicoHasConsulta = this.consultaRepository.existsByMedicoIdAndData(dados.idMedico(), dados.date());
        if (medicoHasConsulta) {
            throw new BusinessException("Médico já possui outra consulta agendada nesse mesmo horário!");
        }
    }
}
