package med.voll.api.controller;

import java.time.LocalDateTime;

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

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.dto.consulta.ConsultaDetalheResponse;
import med.voll.api.dto.consulta.ConsultaAgendarRequest;
import med.voll.api.service.consulta.ConsultaService;

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
    @DisplayName("Deveria retornar código 200 quando body é válido")
    @WithMockUser
    void itShouldReturnOkGivenBodyIsValid() throws Exception {
        // Given
        LocalDateTime date =  LocalDateTime.now().plusHours(1);
        ConsultaAgendarRequest consultaDto = new ConsultaAgendarRequest(1l, 1l,date, Especialidade.CARDIOLOGIA);
        ConsultaDetalheResponse consultaDetalheResponse = new ConsultaDetalheResponse(null, 1l,1l, date);

        Mockito.when(this.consultaService.agendar(Mockito.any())).thenReturn(consultaDetalheResponse);

        // When
        MockHttpServletResponse response = this.mvc.perform(
                post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.consultaJson
                                .write(consultaDto)
                                .getJson())
                )
                .andReturn().getResponse();


        String jsonResponse =  this.consultaDetalheJson
                .write(consultaDetalheResponse)
                .getJson();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResponse);

    }

}