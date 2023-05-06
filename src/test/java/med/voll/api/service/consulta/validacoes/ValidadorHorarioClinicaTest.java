package med.voll.api.service.consulta.validacoes;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;

class ValidadorHorarioClinicaTest {

    private ValidadorHorarioClinica service;

    private LocalDateTime date;
    private ConsultaDto dados;

    @BeforeEach
    void init() {
        this.service = new ValidadorHorarioClinica();
    }

    @Test
    @DisplayName("Deveria lançar uma exceção quando a data de consulta informada for em um domingo.")
    void itShouldThrowExceptionGivenSundayDate() {
        this.date = LocalDateTime.of(2023, 04, 30, 7, 0);
        this.dados = new ConsultaDto(0l,0l,this.date, Especialidade.CARDIOLOGIA);
        assertThrows(BusinessException.class, ()-> this.service.validar(this.dados));
    }

    @Test
    @DisplayName("Não deveria lançar uma exceção quando a data de consulta informada for em uma dia diferente de domingo.")
    void itShouldNotThrowExceptionGivenNonSundayDate() {
        this.date = LocalDateTime.of(2023, 04, 28, 7, 0);
        this.dados = new ConsultaDto(0l,0l,this.date, Especialidade.CARDIOLOGIA);
        assertDoesNotThrow(()-> this.service.validar(this.dados));
    }

    @Test
    @DisplayName("Deveria lançar uma exceção quando o horário de consulta informado for antes do horário de abertura da clínica.")
    void itShouldThrowExceptionGivenTimeIsBeforeStartTime() {
        this.date = LocalDateTime.of(LocalDate.now(), this.service.getSTART_TIME());
        this.dados = new ConsultaDto(0l,0l,this.date, Especialidade.CARDIOLOGIA);
        assertThrows(BusinessException.class, ()-> this.service.validar(this.dados));
    }

    @Test
    @DisplayName("Não deveria lançar uma exceção quando o horário de consulta informado for depois do horário de abertura da clínica.")
    void itShouldNotThrowExceptionGivenTimeIsAfterStartTime() {
        this.date = LocalDateTime.of(LocalDate.now(), this.service.getSTART_TIME().plusMinutes(1));
        this.dados = new ConsultaDto(0l,0l,this.date, Especialidade.CARDIOLOGIA);
        assertDoesNotThrow(()-> this.service.validar(this.dados));
    }

    @Test
    @DisplayName("Deveria lançar uma exceção quando o horário de consulta informado for depois do horário de fechamento da clínica.")
    void itShouldThrowExceptionGivenTimeIsAfterEndTime() {
        this.date = LocalDateTime.of(LocalDate.now(), this.service.getEND_TIME());
        this.dados = new ConsultaDto(0l,0l,this.date, Especialidade.CARDIOLOGIA);
        assertThrows(BusinessException.class, ()-> this.service.validar(this.dados));
    }

    @Test
    @DisplayName("Não deveria lançar uma exceção quando o horário de consulta informado for antes do horário de fechamento da clínica.")
    void itShouldNotThrowExceptionGivenTimeIsBeforeEndTime() {
        this.date = LocalDateTime.of(LocalDate.now(), this.service.getEND_TIME().minusMinutes(1));
        this.dados = new ConsultaDto(0l,0l,this.date, Especialidade.CARDIOLOGIA);
        assertDoesNotThrow(()-> this.service.validar(this.dados));
    }

}