package med.voll.api.repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import med.voll.api.application.dto.medico.MedicoDto;
import med.voll.api.application.dto.paciente.PacienteDto;
import med.voll.api.infra.repository.jpa.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import med.voll.api.domain.entity.consulta.Consulta;
import med.voll.api.domain.entity.medico.Especialidade;
import med.voll.api.domain.entity.medico.Medico;
import med.voll.api.domain.entity.paciente.Paciente;
import med.voll.api.adapter.web.dto.endereco.EnderecoRequest;
import med.voll.api.adapter.web.dto.medico.MedicoCadastrarRequest;
import med.voll.api.adapter.web.dto.paciente.PacienteCadastrarRequest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria retornar null quando não há nenhum médico disponível na data.")
    void itShouldReturnNullGivenMedicoHasConsulta() {

        // Given
        Medico medico = createMedico("Medico", "medico@voll.med", "1199998888" ,"123456", Especialidade.CARDIOLOGIA);
        Paciente paciente = createPaciente("Paciente", "paciente@email.com","1188887777" ,"00000000000");
        createConsulta(paciente, medico, getDateTime());

        // When
        Medico availableMedico = medicoRepository.getAvailableMedico(medico.getEspecialidade(), getDateTime());

        // Then
        assertThat(availableMedico).isNull();
    }

    @Test
    @DisplayName("Deveria retornar um Médico quando ele estiver disponível na data.")
    void itShouldReturnMedicoGivenMedicoAvailable() {

        // Given
        Medico medico = createMedico("Medico", "medico@voll.med", "1199998888" ,"123456", Especialidade.CARDIOLOGIA);

        // When
        Medico availableMedico = medicoRepository.getAvailableMedico(medico.getEspecialidade(), getDateTime());

        // Then
        assertThat(availableMedico).isEqualTo(medico);
    }

    private LocalDateTime getDateTime() {
        return LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
    }

    private EnderecoRequest dtoEndereco() {
        return new EnderecoRequest(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }

    private MedicoDto dtoMedico(String nome, String email, String telefone, String crm, Especialidade especialidade) {
        return new MedicoDto(
                1l,
                nome,
                email,
                telefone,
                crm,
                especialidade,
                dtoEndereco()
        );

    }

    private PacienteDto dtoPaciente(String nome, String email, String telefone, String cpf) {
        return new PacienteDto(
                1l,
                nome,
                email,
                telefone,
                cpf,
                dtoEndereco()
        );
    }

    private Medico createMedico(String nome, String email, String telefone, String crm, Especialidade especialidade) {
        Medico medico = new Medico(this.dtoMedico(nome, email, telefone, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente createPaciente(String nome, String email, String telefone, String cpf) {
        Paciente paciente = new Paciente(this.dtoPaciente(nome, email, telefone, cpf));
        em.persist(paciente);
        return paciente;
    }

    private Consulta createConsulta(Paciente paciente, Medico medico, LocalDateTime date) {
        Consulta consulta = new Consulta(paciente, medico, date);
        em.persist(consulta);
        return consulta;
    }
}