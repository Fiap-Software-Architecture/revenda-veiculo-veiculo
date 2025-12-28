package br.com.fiap.veiculo.infrastructure.adapter.input.rest;

import br.com.fiap.veiculo.application.dto.AtualizarVeiculoCommand;
import br.com.fiap.veiculo.application.dto.CadastrarVeiculoCommand;
import br.com.fiap.veiculo.application.port.input.AtualizarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.input.CadastrarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.input.ListarVeiculosUseCase;
import br.com.fiap.veiculo.domain.model.Marca;
import br.com.fiap.veiculo.domain.model.Placa;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.domain.model.Veiculo;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VeiculoController.class)
class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CadastrarVeiculoUseCase cadastrarVeiculoUseCase;
    @MockitoBean
    private AtualizarVeiculoUseCase alterarVeiculoUseCase;
    @MockitoBean
    private ListarVeiculosUseCase listarVeiculosUseCase;

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
        // arrange
        String payloadInvalido = """
                {
                  "placa": "",
                  "marca": null,
                  "modelo": "",
                  "ano": 1800,
                  "cor": "",
                  "preco": -1
                }
                """;

        // act + assert
        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadInvalido))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(cadastrarVeiculoUseCase);
    }

    @Test
    void cadastrar_deveChamarUseCaseComCommand() throws Exception {
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

    @Test
    void atualizar_deveRetornar204EChamarUseCase() throws Exception {
        // arrange
        UUID id = UUID.fromString("22222222-2222-2222-2222-222222222222");

        String payload = """
                {
                  "placa": "ABC1D23",
                  "marca": "FIAT",
                  "modelo": "Uno",
                  "ano": 2020,
                  "cor": "Branco",
                  "preco": 35000.00,
                  "status": "DISPONIVEL"
                }
                """;

        ArgumentCaptor<AtualizarVeiculoCommand> captor = ArgumentCaptor.forClass(AtualizarVeiculoCommand.class);

        // act + assert
        mockMvc.perform(put("/veiculos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(alterarVeiculoUseCase, times(1)).executar(captor.capture());
        AtualizarVeiculoCommand command = captor.getValue();

        assertEquals(id, command.id());
        assertEquals("ABC1D23", command.placa().getValue());
        assertEquals("FIAT", command.marca().name());
        assertEquals("Uno", command.modelo());
        assertEquals(2020, command.ano());
        assertEquals("Branco", command.cor());
        assertEquals("35000.00", command.preco().toPlainString());
        assertEquals("DISPONIVEL", command.status().name());
    }

    @Test
    void atualizar_quandoRequestInvalido_deveRetornar400ENaoChamarUseCase() throws Exception {
        // arrange
        UUID id = UUID.fromString("33333333-3333-3333-3333-333333333333");

        String payloadInvalido = """
                {
                  "placa": "",
                  "marca": "MARCA_INEXISTENTE",
                  "modelo": "",
                  "ano": 1800,
                  "cor": "",
                  "preco": -1,
                  "status": null
                }
                """;

        // act + assert
        mockMvc.perform(put("/veiculos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadInvalido))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(alterarVeiculoUseCase);
    }

    @Test
    void listar_semStatus_deveRetornar200ComListaDeVeiculos() throws Exception {
        // arrange
        Veiculo v1 = new Veiculo(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                new Placa("ABC1D23"),
                Marca.FIAT,
                "Uno",
                2020,
                "Branco",
                new BigDecimal("10000.00"),
                StatusVeiculo.DISPONIVEL
        );

        Veiculo v2 = new Veiculo(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                new Placa("DEF4G56"),
                Marca.FORD,
                "Ka",
                2018,
                "Preto",
                new BigDecimal("20000.00"),
                StatusVeiculo.VENDIDO
        );

        when(listarVeiculosUseCase.listar(eq(Optional.empty())))
                .thenReturn(List.of(v1, v2));

        // act + assert
        mockMvc.perform(get("/veiculos"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(v1.getId().toString()))
                .andExpect(jsonPath("$[0].placa").value(v1.getPlaca().getValue()))
                .andExpect(jsonPath("$[0].marca").value(v1.getMarca().name()))
                .andExpect(jsonPath("$[0].modelo").value(v1.getModelo()))
                .andExpect(jsonPath("$[0].cor").value(v1.getCor()))
                .andExpect(jsonPath("$[0].preco").value(10000.00))
                .andExpect(jsonPath("$[0].status").value(v1.getStatus().name()))

                .andExpect(jsonPath("$[1].id").value(v2.getId().toString()))
                .andExpect(jsonPath("$[1].placa").value(v2.getPlaca().getValue()))
                .andExpect(jsonPath("$[1].marca").value(v2.getMarca().name()))
                .andExpect(jsonPath("$[1].modelo").value(v2.getModelo()))
                .andExpect(jsonPath("$[1].cor").value(v2.getCor()))
                .andExpect(jsonPath("$[1].preco").value(20000.00))
                .andExpect(jsonPath("$[1].status").value(v2.getStatus().name()));

        verify(listarVeiculosUseCase, times(1)).listar(eq(Optional.empty()));
        verifyNoMoreInteractions(listarVeiculosUseCase);
    }

    @Test
    void listar_comStatus_deveRetornar200EChamarUseCaseComOptional() throws Exception {
        // arrange
        Veiculo v1 = new Veiculo(
                UUID.fromString("33333333-3333-3333-3333-333333333333"),
                new Placa("GHI7J89"),
                Marca.FIAT,
                "Argo",
                2021,
                "Vermelho",
                new BigDecimal("15000.00"),
                StatusVeiculo.DISPONIVEL
        );

        when(listarVeiculosUseCase.listar(eq(Optional.of(StatusVeiculo.DISPONIVEL))))
                .thenReturn(List.of(v1));

        // act + assert
        mockMvc.perform(get("/veiculos")
                        .queryParam("status", "DISPONIVEL"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(v1.getId().toString()))
                .andExpect(jsonPath("$[0].status").value("DISPONIVEL"));

        verify(listarVeiculosUseCase, times(1)).listar(eq(Optional.of(StatusVeiculo.DISPONIVEL)));
        verifyNoMoreInteractions(listarVeiculosUseCase);
    }

    @Test
    void listar_comStatusInvalido_deveRetornar400ENaoChamarUseCase() throws Exception {
        // act + assert
        mockMvc.perform(get("/veiculos")
                        .queryParam("status", "INVALIDO"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(listarVeiculosUseCase);
    }

}
