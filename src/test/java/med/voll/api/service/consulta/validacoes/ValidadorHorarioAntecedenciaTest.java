package med.voll.api.service.consulta.validacoes;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;

@SpringBootTest
public class ValidadorHorarioAntecedenciaTest {

    @Autowired
    private ValidadorHorarioAntecedencia service;

    private LocalDateTime horarioAtual;
    private LocalDateTime futureDate;
    private ConsultaDto dados;

    @BeforeEach
    void init() {
        this.horarioAtual = LocalDateTime.now();
    }

    @Test
    void itShouldThrowExceptionWhenHorarioLesser() {
        int minutosAntecedencia = this.service.getMINUTES() - 1;
        this.futureDate = this.horarioAtual.plusMinutes(minutosAntecedencia);
        this.dados = new ConsultaDto(0l,0l,this.futureDate, Especialidade.CARDIOLOGIA);
        assertThrows(BusinessException.class, () -> this.service.validar(this.dados));
    }

    @Test
    void itShouldNotThrowExceptionWhenHorarioEquals() {
        int minutosAntecedencia = this.service.getMINUTES();
        this.futureDate = this.horarioAtual.plusMinutes(minutosAntecedencia);
        this.dados = new ConsultaDto(0l,0l,this.futureDate, Especialidade.CARDIOLOGIA);
        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }

    @Test
    void itShouldNotThrowExceptionWhenHorarioGreater() {
        int minutosAntecedencia = this.service.getMINUTES() + 1;
        this.futureDate = this.horarioAtual.plusMinutes(minutosAntecedencia);
        this.dados = new ConsultaDto(0l,0l,this.futureDate, Especialidade.CARDIOLOGIA);
        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }

}
