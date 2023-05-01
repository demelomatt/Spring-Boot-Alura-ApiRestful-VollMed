package med.voll.api.service.consulta.validacoes;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ValidadorHorarioClinicaTest {

    @Autowired
    private ValidadorHorarioClinica service;

    private LocalDateTime date;
    private ConsultaDto dados;

    @BeforeEach
    public void init() {
    }

    @AfterEach
    public void asserts() {
        this.dados = new ConsultaDto(0l,0l,this.date, Especialidade.CARDIOLOGIA);
        assertThrows(BusinessException.class, ()-> this.service.validar(this.dados));
    }

    @Test
    public void itShouldThrowExceptionWhenSunday() {
         this.date = LocalDateTime.of(2023, 04, 30, 7, 0);

    }

    @Test
    public void itShouldThrowExceptionWhenTimeIsBefore() {
        this.date = LocalDateTime.of(1900, 01, 01, 6, 59);
    }

    @Test
    public void itShouldThrowExceptionWhenTimeIsAfter() {
        this.date = LocalDateTime.of(1900, 01, 01, 18, 01);
    }

}