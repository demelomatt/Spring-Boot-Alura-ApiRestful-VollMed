package med.voll.api.service.consulta.validacoes;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;

class ValidadorHorarioClinicaTest {

    private ValidadorHorarioClinica service;
    private ConsultaDto dados;
    private LocalDateTime date;

    @BeforeEach
    public void init() {
        this.service = new ValidadorHorarioClinica();
    }

    @AfterEach
    public void asserts() {
        this.dados = new ConsultaDto(0l,0l,this.date, Especialidade.CARDIOLOGIA);
        assertThrows(BusinessException.class, ()-> this.service.validar(this.dados));
    }

    @Test
    public void itShouldThrowExceptionCosSunday() {
         this.date = LocalDateTime.of(2023, 04, 30, 10, 0);

    }

    @Test
    public void itShouldThrowExceptionCosTimeIsBefore() {
        this.date = LocalDateTime.of(2023, 04, 29, 6, 59);
    }

    @Test
    public void itShouldThrowExceptionCosTimeIsAfter() {
        this.date = LocalDateTime.of(2023, 04, 29, 18, 01);
    }

}