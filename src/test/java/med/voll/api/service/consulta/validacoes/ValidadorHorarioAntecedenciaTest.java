package med.voll.api.service.consulta.validacoes;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.dto.consulta.ConsultaDto;
import med.voll.api.exception.BusinessException;
public class ValidadorHorarioAntecedenciaTest {

    ValidadorHorarioAntecedencia service;
    LocalDateTime horarioAtual;
    ConsultaDto dados;

    @BeforeEach
    public void init() {
        this.service = new ValidadorHorarioAntecedencia();
        this.horarioAtual = LocalDateTime.now();
    }

    @Test
    public void itShouldThrowException() {
        LocalDateTime date = this.horarioAtual.plusMinutes(29);
        this.dados = new ConsultaDto(0l,0l,date,Especialidade.CARDIOLOGIA);

        assertThrows(BusinessException.class, () -> this.service.validar(this.dados));
    }

    @Test
    public void itShouldNotThrowException() {
        LocalDateTime date = this.horarioAtual.plusMinutes(30);
        this.dados = new ConsultaDto(0l,0l,date,Especialidade.CARDIOLOGIA);

        assertDoesNotThrow(() -> this.service.validar(this.dados));
    }

}
