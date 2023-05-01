package med.voll.api.service.consulta.validacoes;

import java.time.LocalDateTime;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;
import med.voll.api.repository.MedicoRepository;

@SpringBootTest
class ValidadorMedicoAtivoTest {

    @Autowired
    private ValidadorMedicoAtivo service;

    private ConsultaDto dados;

    @MockBean
    private MedicoRepository medicoRepositoryMock;

    @Test
    void itShouldNotThrowExceptionWhenIdNull() {
        Long idMedico = null;
        this.dados = new ConsultaDto(1l, idMedico, LocalDateTime.now(), Especialidade.CARDIOLOGIA);

        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }

    @Test
    void itShouldNotThrowExceptionWhenAtivoTrue() {
        Long idMedico = 1l;
        this.dados = new ConsultaDto(1l, idMedico, LocalDateTime.now(), Especialidade.CARDIOLOGIA);

        Mockito.when(this.medicoRepositoryMock.findAtivoById(idMedico))
                .thenReturn(true);

        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }

    @Test
    void itShouldThrowExceptionWhenAtivoFalse() {
        Long idMedico = 1l;
        this.dados = new ConsultaDto(1l, idMedico, LocalDateTime.now(), Especialidade.CARDIOLOGIA);

        Mockito.when(this.medicoRepositoryMock.findAtivoById(idMedico))
                .thenReturn(false);

        assertThrows(BusinessException.class, () -> this.service.validar(this.dados));
    }

}