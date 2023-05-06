package med.voll.api.service.consulta.validacoes;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;
import med.voll.api.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidadorPacienteAtivoTest {

    @Autowired
    private ValidadorPacienteAtivo service;

    private ConsultaDto dados;

    @MockBean
    private PacienteRepository pacienteRepositoryMock;

    private Long idPaciente;

    @BeforeEach
    void init() {
        this.idPaciente = 1l;
        this.dados = new ConsultaDto(idPaciente, 1l, LocalDateTime.now(), Especialidade.CARDIOLOGIA);
    }

    @Test
    void itShouldNotThrowExceptionWhenAtivoTrue() {
        Mockito.when(this.pacienteRepositoryMock.findAtivoById(this.idPaciente))
                .thenReturn(true);

        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }

    @Test
    void itShouldThrowExceptionWhenAtivoFalse() {
        Mockito.when(this.pacienteRepositoryMock.findAtivoById(this.idPaciente))
                .thenReturn(false);

        assertThrows(BusinessException.class, () -> this.service.validar(this.dados));
    }

}