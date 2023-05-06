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
class ValidadorMedicoDisponivelTest {

    @Autowired
    private ValidadorMedicoDisponivel service;

    private ConsultaDto dados;

    @MockBean
    private ConsultaRepository consultaRepositoryMock;

    @BeforeEach
    void init() {
        this.dados = new ConsultaDto(1l,1l, LocalDateTime.now(), Especialidade.CARDIOLOGIA);
    }

    @Test
    void itShouldThrowExceptionWhenHasConsulta() {
        Mockito.when(this.consultaRepositoryMock.existsByMedicoIdAndData(this.dados.idMedico(), this.dados.date()))
                .thenReturn(true);

        assertThrows(BusinessException.class, () -> this.service.validar(this.dados));
    }

    @Test
    void itShouldNotThrowExceptionWhenDoesntHasConsulta() {
        Mockito.when(this.consultaRepositoryMock.existsByMedicoIdAndData(this.dados.idMedico(), this.dados.date()))
                .thenReturn(false);

        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }
}