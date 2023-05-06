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
import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;
import med.voll.api.repository.PacienteRepository;

class ValidadorPacienteAtivoTest {

    @Mock
    private PacienteRepository pacienteRepositoryMock;

    @InjectMocks
    private ValidadorPacienteAtivo service;

    private Long idPaciente;
    private ConsultaDto dados;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        this.idPaciente = 1l;
        this.dados = new ConsultaDto(idPaciente, 1l, LocalDateTime.now(), Especialidade.CARDIOLOGIA);
    }

    @Test
    @DisplayName("Não deveria lançar uma exceção quando o paciente estiver ativo.")
    void itShouldNotThrowExceptionGivenPacienteAtivoTrue() {
        Mockito.when(this.pacienteRepositoryMock.findAtivoById(this.idPaciente))
                .thenReturn(true);

        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }

    @Test
    @DisplayName("Deveria lançar uma exceção quando o paciente não estiver ativo.")
    void itShouldThrowExceptionGivenPacienteAtivoFalse() {
        Mockito.when(this.pacienteRepositoryMock.findAtivoById(this.idPaciente))
                .thenReturn(false);

        assertThrows(BusinessException.class, () -> this.service.validar(this.dados));
    }

}