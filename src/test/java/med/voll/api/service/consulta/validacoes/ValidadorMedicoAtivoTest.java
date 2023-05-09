package med.voll.api.service.consulta.validacoes;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import med.voll.api.application.dto.consulta.ConsultaIdDto;
import med.voll.api.application.service.consulta.validacoes.ValidadorMedicoAtivo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import med.voll.api.domain.entity.medico.Especialidade;
import med.voll.api.adapter.web.dto.consulta.ConsultaAgendarRequest;
import med.voll.api.domain.exception.BusinessException;
import med.voll.api.infra.repository.jpa.MedicoRepository;

class ValidadorMedicoAtivoTest {

    @Mock
    private MedicoRepository medicoRepositoryMock;

    @InjectMocks
    private ValidadorMedicoAtivo service;

    private ConsultaIdDto dados;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Não deveria lançar uma exceção quando não for informado um médico.")
    void itShouldNotThrowExceptionGivenMedicoIdNull() {
        Long idMedico = null;
        this.dados = new ConsultaIdDto(1l, 1l, idMedico, LocalDateTime.now(), Especialidade.CARDIOLOGIA);

        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }

    @Test
    @DisplayName("Não deveria lançar uma exceção quando o médico informado estiver ativo.")
    void itShouldNotThrowExceptionGivenMedicoAtivoTrue() {
        Long idMedico = 1l;
        this.dados = new ConsultaIdDto(1l, 1l, idMedico, LocalDateTime.now(), Especialidade.CARDIOLOGIA);

        Mockito.when(this.medicoRepositoryMock.findAtivoById(idMedico))
                .thenReturn(true);

        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }

    @Test
    @DisplayName("Deveria lançar uma exceção quando o médico informado não estiver ativo.")
    void itShouldThrowExceptionGivenMedicoAtivoFalse() {
        Long idMedico = 1l;
        this.dados = new ConsultaIdDto(1l, 1l, idMedico, LocalDateTime.now(), Especialidade.CARDIOLOGIA);

        Mockito.when(this.medicoRepositoryMock.findAtivoById(idMedico))
                .thenReturn(false);

        assertThrows(BusinessException.class, () -> this.service.validar(this.dados));
    }

}