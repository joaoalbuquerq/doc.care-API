package med.voll.api.controller;

import med.voll.api.domain.consulta.ConsultaService;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;
    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaConsultaJson;

    @MockBean
    private ConsultaService service;

    @Test
    @DisplayName("Deveria devolver código 400 quando informações estão inválidas")
    @WithMockUser
    void agendarCenario1() throws Exception {
        var response = mvc.perform(MockMvcRequestBuilders.post("/consultas")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código 400 quando informações estão inválidas")
    @WithMockUser
    void cancelarCenario1() throws Exception {
        var response = mvc.perform(MockMvcRequestBuilders.delete("/consultas")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código 200 quando informações estão válidas")
    @WithMockUser
    void agendarCenario2() throws Exception {

        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 3l, 3l, data);
        when(service.agendar(any())).thenReturn(dadosDetalhamento);

        var response = mvc.perform(
                MockMvcRequestBuilders.post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAgendamentoConsultaJson.write(
                                new DadosAgendamentoConsulta(3l,3l, data, especialidade)
                        ).getJson())
                )
                .andReturn()
                .getResponse();

        var jsonEsperado = dadosDetalhamentoConsultaConsultaJson.write(dadosDetalhamento).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

}