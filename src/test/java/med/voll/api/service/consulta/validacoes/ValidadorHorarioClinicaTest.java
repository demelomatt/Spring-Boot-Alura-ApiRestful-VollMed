package med.voll.api.service.consulta.validacoes;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;

@SpringBootTest
class ValidadorHorarioClinicaTest {

    @Autowired
    private ValidadorHorarioClinica service;

    private LocalDateTime date;
    private ConsultaDto dados;

    @Test
    void itShouldThrowExceptionWhenSunday() {
        this.date = LocalDateTime.of(2023, 04, 30, 7, 0);
        this.dados = new ConsultaDto(0l,0l,this.date, Especialidade.CARDIOLOGIA);
        assertThrows(BusinessException.class, ()-> this.service.validar(this.dados));
    }

    @Test
    void itShouldNotThrowExceptionWhenNotSunday() {
        this.date = LocalDateTime.of(2023, 04, 28, 7, 0);
        this.dados = new ConsultaDto(0l,0l,this.date, Especialidade.CARDIOLOGIA);
        assertDoesNotThrow(()-> this.service.validar(this.dados));
    }

    @Test
    void itShouldThrowExceptionWhenTimeIsBeforeStartTime() {
        this.date = LocalDateTime.of(LocalDate.now(), this.service.getSTART_TIME());
        this.dados = new ConsultaDto(0l,0l,this.date, Especialidade.CARDIOLOGIA);
        assertThrows(BusinessException.class, ()-> this.service.validar(this.dados));
    }

    @Test
    void itShouldNotThrowExceptionWhenTimeIsAfterStartTime() {
        this.date = LocalDateTime.of(LocalDate.now(), this.service.getSTART_TIME().plusMinutes(1));
        this.dados = new ConsultaDto(0l,0l,this.date, Especialidade.CARDIOLOGIA);
        assertDoesNotThrow(()-> this.service.validar(this.dados));
    }

    @Test
    void itShouldThrowExceptionWhenTimeIsAfterEndTime() {
        this.date = LocalDateTime.of(LocalDate.now(), this.service.getEND_TIME());
        this.dados = new ConsultaDto(0l,0l,this.date, Especialidade.CARDIOLOGIA);
        assertThrows(BusinessException.class, ()-> this.service.validar(this.dados));
    }

    @Test
    void itShouldNotThrowExceptionWhenTimeIsBeforeEndTime() {
        this.date = LocalDateTime.of(LocalDate.now(), this.service.getEND_TIME().minusMinutes(1));
        this.dados = new ConsultaDto(0l,0l,this.date, Especialidade.CARDIOLOGIA);
        assertDoesNotThrow(()-> this.service.validar(this.dados));
    }

}