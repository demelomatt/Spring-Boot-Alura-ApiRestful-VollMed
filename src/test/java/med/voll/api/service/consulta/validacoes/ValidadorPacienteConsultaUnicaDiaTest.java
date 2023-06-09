package med.voll.api.service.consulta.validacoes;

import med.voll.api.application.dto.consulta.ConsultaIdDto;
import med.voll.api.application.service.consulta.validacoes.ValidadorPacienteConsultaUnicaDia;
import med.voll.api.domain.entity.medico.Especialidade;
import med.voll.api.domain.exception.PacienteNotValidException;
import med.voll.api.infra.repository.jpa.ConsultaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidadorPacienteConsultaUnicaDiaTest {

    @Mock
    private ConsultaRepository consultaRepositoryMock;

    @InjectMocks
    private ValidadorPacienteConsultaUnicaDia service;

    private ConsultaIdDto dados;
    private LocalDateTime primeiroHorario;
    private LocalDateTime ultimoHorario;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        this.dados = new ConsultaIdDto(1l, 1l, 1l, LocalDateTime.now(), Especialidade.CARDIOLOGIA);
        this.primeiroHorario = dados.date().withHour(this.service.getSTART_HOUR());
        this.ultimoHorario = dados.date().withHour(this.service.getEND_HOUR());
    }

    @Test
    @DisplayName("Deveria lançar uma exceção quando o paciente já tiver uma consulta agendada para o dia.")
    void itShouldThrowExceptionGivenPacienteHasConsulta () {

        Mockito.when(this.consultaRepositoryMock.existsByPacienteIdAndDataBetween(1l, this.primeiroHorario, this.ultimoHorario))
                .thenReturn(true);

        assertThrows(PacienteNotValidException.class, () -> this.service.validar(this.dados));
    }

    @Test
    @DisplayName("Não deveria lançar uma exceção quando o paciente ainda não tiver uma consulta agendada para o dia.")
    void itShouldNotThrowExceptionGivenPacienteDoesntHaveConsulta () {

        Mockito.when(this.consultaRepositoryMock.existsByPacienteIdAndDataBetween(1l, this.primeiroHorario, this.ultimoHorario))
                .thenReturn(false);

        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }

}