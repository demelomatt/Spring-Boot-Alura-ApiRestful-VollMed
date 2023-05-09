package med.voll.api.controller;

import java.time.LocalDateTime;

import med.voll.api.adapter.web.dto.medico.MedicoListarResponse;
import med.voll.api.adapter.web.dto.paciente.PacienteListarResponse;
import med.voll.api.application.dto.consulta.ConsultaDto;
import med.voll.api.application.dto.consulta.ConsultaIdDto;
import med.voll.api.domain.entity.consulta.Consulta;
import med.voll.api.domain.entity.medico.Medico;
import med.voll.api.domain.entity.paciente.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

import med.voll.api.domain.entity.medico.Especialidade;
import med.voll.api.adapter.web.dto.consulta.ConsultaDetalheResponse;
import med.voll.api.adapter.web.dto.consulta.ConsultaAgendarRequest;
import med.voll.api.application.service.consulta.ConsultaService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ConsultaAgendarRequest> consultaJson;

    @Autowired
    private JacksonTester<ConsultaDetalheResponse> consultaDetalheJson;

    @MockBean
    private ConsultaService consultaService;

    @Test
    @DisplayName("Deveria retornar código 400 quando body está vazio")
    @WithMockUser
    void itShouldReturnBadRequestGivenBodyEmpty() throws Exception {
        MockHttpServletResponse response = this.mvc.perform(post("/consultas"))
                 .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria retornar código 201 quando body é válido")
    @WithMockUser
    void itShouldReturnCreatedGivenBodyIsValid() throws Exception {

        // Given
        LocalDateTime date =  LocalDateTime.now().plusHours(1);
        Medico medico = new Medico();
        Paciente paciente = new Paciente();
        Consulta consulta = new Consulta(1l, paciente, medico, date, true);

        ConsultaAgendarRequest consultaAgendarRequest = new ConsultaAgendarRequest(1l, 1l,date, Especialidade.CARDIOLOGIA);
        ConsultaDetalheResponse consultaDetalheResponse = new ConsultaDetalheResponse(1l, new MedicoListarResponse(medico),new PacienteListarResponse(paciente), date);

        Mockito.when(this.consultaService.agendar(Mockito.any())).thenReturn(new ConsultaDto(consulta));

        // When
        MockHttpServletResponse response = this.mvc.perform(
                post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.consultaJson
                                .write(consultaAgendarRequest)
                                .getJson())
                )
                .andReturn().getResponse();


        String jsonResponse =  this.consultaDetalheJson
                .write(consultaDetalheResponse)
                .getJson();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResponse);

    }

}