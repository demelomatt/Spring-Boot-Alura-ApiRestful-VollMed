package med.voll.api.repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.dto.endereco.EnderecoRequest;
import med.voll.api.dto.medico.MedicoCadastrarRequest;
import med.voll.api.dto.paciente.PacienteCadastrarRequest;

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

    private MedicoCadastrarRequest dtoMedico(String nome, String email, String telefone, String crm, Especialidade especialidade) {
        return new MedicoCadastrarRequest(
                nome,
                email,
                telefone,
                crm,
                especialidade,
                dtoEndereco()
        );

    }

    private PacienteCadastrarRequest dtoPaciente(String nome, String email, String telefone, String cpf) {
        return new PacienteCadastrarRequest(
                nome,
                email,
                telefone,
                cpf,
                dtoEndereco()
        );
    }

    private Medico createMedico(String nome, String email, String telefone, String crm, Especialidade especialidade) {
        Medico medico = new Medico(dtoMedico(nome, email, telefone, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente createPaciente(String nome, String email, String telefone, String cpf) {
        Paciente paciente = new Paciente(dtoPaciente(nome, email, telefone, cpf));
        em.persist(paciente);
        return paciente;
    }

    private Consulta createConsulta(Paciente paciente, Medico medico, LocalDateTime date) {
        Consulta consulta = new Consulta(paciente, medico, date);
        em.persist(consulta);
        return consulta;
    }


}