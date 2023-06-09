package med.voll.api.service.consulta.validacoes;

import med.voll.api.application.dto.consulta.ConsultaIdDto;
import med.voll.api.application.service.consulta.validacoes.ValidadorHorarioAntecedencia;
import med.voll.api.domain.entity.medico.Especialidade;
import med.voll.api.domain.exception.BusinessException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidadorHorarioAntecedenciaTest {

    private ValidadorHorarioAntecedencia service;

    private LocalDateTime horarioAtual;
    private LocalDateTime futureDate;
    private ConsultaIdDto dados;

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
        this.dados = new ConsultaIdDto(0l, 0l,0l,this.futureDate, Especialidade.CARDIOLOGIA);
        assertThrows(BusinessException.class, () -> this.service.validar(this.dados));
    }

    @Test
    @DisplayName("Não deveria lançar uma exceção quando o horário da consulta informado tiver exatamente 30min de antecedência.")
    void itShouldNotThrowExceptionGivenHorarioEquals() {
        int minutosAntecedencia = this.service.getMINUTES();
        this.futureDate = this.horarioAtual.plusMinutes(minutosAntecedencia);
        this.dados = new ConsultaIdDto(0l,0l,0l,this.futureDate, Especialidade.CARDIOLOGIA);
        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }

    @Test
    @DisplayName("Não deveria lançar uma exceção quando o horário da consulta informado tiver mais que 30min de antecedência.")
    void itShouldNotThrowExceptionGivenHorarioGreater() {
        int minutosAntecedencia = this.service.getMINUTES() + 1;
        this.futureDate = this.horarioAtual.plusMinutes(minutosAntecedencia);
        this.dados = new ConsultaIdDto(0l, 0l,0l,this.futureDate, Especialidade.CARDIOLOGIA);
        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }

}
