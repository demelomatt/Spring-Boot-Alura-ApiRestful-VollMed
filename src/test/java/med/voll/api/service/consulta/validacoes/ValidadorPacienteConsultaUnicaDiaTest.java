package med.voll.api.service.consulta.validacoes;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;
import med.voll.api.repository.ConsultaRepository;

@SpringBootTest
class ValidadorPacienteConsultaUnicaDiaTest {

    @Autowired
    private ValidadorPacienteConsultaUnicaDia service;

    private ConsultaDto dados;

    @MockBean
    private ConsultaRepository consultaRepositoryMock;

    private LocalDateTime primeiroHorario;
    private LocalDateTime ultimoHorario;

    @BeforeEach
    void init() {
        this.dados = new ConsultaDto(1l, 1l, LocalDateTime.now(), Especialidade.CARDIOLOGIA);
        this.primeiroHorario = dados.date().withHour(this.service.getSTART_HOUR());
        this.ultimoHorario = dados.date().withHour(this.service.getEND_HOUR());
    }

    @Test
    void itShouldNotThrowExceptionWhenPacienteDoesntHasConsulta () {

        Mockito.when(this.consultaRepositoryMock.existsByPacienteIdAndDataBetween(1l, this.primeiroHorario, this.ultimoHorario))
                .thenReturn(false);

        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }

    @Test
    void itShouldThrowExceptionWhenPacienteHasConsulta () {

        Mockito.when(this.consultaRepositoryMock.existsByPacienteIdAndDataBetween(1l, this.primeiroHorario, this.ultimoHorario))
                .thenReturn(true);

        assertThrows(BusinessException.class, () -> this.service.validar(this.dados));
    }
}