package br.com.fiap.veiculo.infrastructure.adapter.input.rest;

import br.com.fiap.veiculo.application.dto.CadastrarVeiculoCommand;
import br.com.fiap.veiculo.application.port.input.AtualizarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.input.CadastrarVeiculoUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VeiculoController.class)
class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CadastrarVeiculoUseCase cadastrarVeiculoUseCase;

    @MockitoBean
    private AtualizarVeiculoUseCase alterarVeiculoUseCase;

    @Test
    void cadastrar_deveRetornar201ComJsonId() throws Exception {
        // arrange
        UUID idGerado = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(cadastrarVeiculoUseCase.executar(any()))
                .thenReturn(idGerado);

        String payload = """
                {
                  "placa": "ABC1D23",
                  "marca": "FIAT",
                  "modelo": "Uno",
                  "ano": 2020,
                  "cor": "Branco",
                  "preco": 35000.00
                }
                """;

        // act + assert
        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(idGerado.toString()));

        verify(cadastrarVeiculoUseCase, times(1)).executar(any());
    }

    @Test
    void cadastrar_quandoRequestInvalido_deveRetornar400() throws Exception {
        // Ex.: placa vazia + marca nula etc (ajuste conforme suas anotações no request)
        String payloadInvalido = """
                {
                  "placa": "",
                  "marca": null,
                  "modelo": "",
                  "ano": 0,
                  "cor": "",
                  "preco": null
                }
                """;

        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadInvalido))
                .andExpect(status().isBadRequest());

        verify(cadastrarVeiculoUseCase, never()).executar(any());
    }

    @Test
    void cadastrar_deveChamarUseCaseComCommandCriadoPeloRequest() throws Exception {
        // arrange
        UUID idGerado = UUID.randomUUID();
        when(cadastrarVeiculoUseCase.executar(any()))
                .thenReturn(idGerado);

        String payload = """
                {
                  "placa": "ABC1D23",
                  "marca": "FIAT",
                  "modelo": "Uno",
                  "ano": 2020,
                  "cor": "Branco",
                  "preco": 35000.00
                }
                """;

        ArgumentCaptor<Object> commandCaptor = ArgumentCaptor.forClass(Object.class);

        // act
        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());

        // assert (mínimo): garante que o controller gerou um command e passou ao use case
        verify(cadastrarVeiculoUseCase).executar((CadastrarVeiculoCommand) commandCaptor.capture());
        assertNotNull(commandCaptor.getValue());
    }
}
