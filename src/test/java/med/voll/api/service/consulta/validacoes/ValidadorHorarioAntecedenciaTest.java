package med.voll.api.service.consulta.validacoes;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.dto.consulta.ConsultaAgendarRequest;
import med.voll.api.exception.BusinessException;

public class ValidadorHorarioAntecedenciaTest {

    private ValidadorHorarioAntecedencia service;

    private LocalDateTime horarioAtual;
    private LocalDateTime futureDate;
    private ConsultaAgendarRequest dados;

    @BeforeEach
    void init() {
        this.service = new ValidadorHorarioAntecedencia();
        this.horarioAtual = LocalDateTime.now();
    }

    @Test
    @DisplayName("Deveria lançar uma exceção quando o horário da consulta informado tiver menos que 30min de antecedência.")
    void itShouldThrowExceptionGivenHorarioLesser() {
        int minutosAntecedencia = this.service.getMINUTES() - 1;
        this.futureDate = this.horarioAtual.plusMinutes(minutosAntecedencia);
        this.dados = new ConsultaAgendarRequest(0l,0l,this.futureDate, Especialidade.CARDIOLOGIA);
        assertThrows(BusinessException.class, () -> this.service.validar(this.dados));
    }

    @Test
    @DisplayName("Não deveria lançar uma exceção quando o horário da consulta informado tiver exatamente 30min de antecedência.")
    void itShouldNotThrowExceptionGivenHorarioEquals() {
        int minutosAntecedencia = this.service.getMINUTES();
        this.futureDate = this.horarioAtual.plusMinutes(minutosAntecedencia);
        this.dados = new ConsultaAgendarRequest(0l,0l,this.futureDate, Especialidade.CARDIOLOGIA);
        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }

    @Test
    @DisplayName("Não deveria lançar uma exceção quando o horário da consulta informado tiver mais que 30min de antecedência.")
    void itShouldNotThrowExceptionGivenHorarioGreater() {
        int minutosAntecedencia = this.service.getMINUTES() + 1;
        this.futureDate = this.horarioAtual.plusMinutes(minutosAntecedencia);
        this.dados = new ConsultaAgendarRequest(0l,0l,this.futureDate, Especialidade.CARDIOLOGIA);
        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }

}
