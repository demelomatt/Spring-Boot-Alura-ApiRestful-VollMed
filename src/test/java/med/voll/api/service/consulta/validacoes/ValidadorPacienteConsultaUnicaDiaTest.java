package med.voll.api.service.consulta.validacoes;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;
import med.voll.api.repository.ConsultaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
    public void init() {
        this.dados = new ConsultaDto(1l, 1l, LocalDateTime.now(), Especialidade.CARDIOLOGIA);
        this.primeiroHorario = dados.date().withHour(7);
        this.ultimoHorario = dados.date().withHour(18);
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