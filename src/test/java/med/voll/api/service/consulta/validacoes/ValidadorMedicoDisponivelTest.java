package med.voll.api.service.consulta.validacoes;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.dto.consulta.ConsultaAgendarRequest;
import med.voll.api.exception.BusinessException;
import med.voll.api.repository.ConsultaRepository;

class ValidadorMedicoDisponivelTest {

    @Mock
    private ConsultaRepository consultaRepositoryMock;

    @InjectMocks
    private ValidadorMedicoDisponivel service;

    private ConsultaAgendarRequest dados;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        this.dados = new ConsultaAgendarRequest(1l,1l, LocalDateTime.now(), Especialidade.CARDIOLOGIA);
    }

    @Test
    @DisplayName("Deveria lançar uma exceção quando o médico informado já tiver uma consulta na data e horário informados.")
    void itShouldThrowExceptionGivenMedicoHasConsulta() {
        Mockito.when(this.consultaRepositoryMock.existsByMedicoIdAndData(this.dados.idMedico(), this.dados.date()))
                .thenReturn(true);

        assertThrows(BusinessException.class, () -> this.service.validar(this.dados));
    }

    @Test
    @DisplayName("Não deveria lançar uma exceção quando o médico informado não tiver uma consulta na data e horário informados.")
    void itShouldNotThrowExceptionGivenMedicoDoesntHaveConsulta() {
        Mockito.when(this.consultaRepositoryMock.existsByMedicoIdAndData(this.dados.idMedico(), this.dados.date()))
                .thenReturn(false);

        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }
}