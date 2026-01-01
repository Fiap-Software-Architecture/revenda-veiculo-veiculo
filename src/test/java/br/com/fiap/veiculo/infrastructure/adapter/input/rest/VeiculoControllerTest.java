package br.com.fiap.veiculo.infrastructure.adapter.input.rest;

import br.com.fiap.veiculo.application.dto.AtualizarVeiculoCommand;
import br.com.fiap.veiculo.application.dto.CadastrarVeiculoCommand;
import br.com.fiap.veiculo.application.port.input.AtualizarStatusVeiculoUseCase;
import br.com.fiap.veiculo.application.port.input.AtualizarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.input.BuscarVeiculoPorIdUseCase;
import br.com.fiap.veiculo.application.port.input.CadastrarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.input.ListarVeiculosUseCase;
import br.com.fiap.veiculo.application.port.input.RemoverVeiculoUseCase;
import br.com.fiap.veiculo.domain.exception.VeiculoNaoEncontradoException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VeiculoController.class)
class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BuscarVeiculoPorIdUseCase buscarVeiculoPorIdUseCase;
    @MockitoBean
    private ListarVeiculosUseCase listarVeiculosUseCase;
    @MockitoBean
    private CadastrarVeiculoUseCase cadastrarVeiculoUseCase;
    @MockitoBean
    private AtualizarVeiculoUseCase alterarVeiculoUseCase;
    @MockitoBean
    private RemoverVeiculoUseCase removerVeiculoUseCase;
    @MockitoBean
    private AtualizarStatusVeiculoUseCase atualizarStatusVeiculoUseCase;

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
    void buscarPorId_deveRetornar200ComVeiculo() throws Exception {
        // arrange
        UUID id = UUID.fromString("99999999-9999-9999-9999-999999999999");

        Veiculo veiculo = new Veiculo(
                id,
                new Placa("ABC1D23"),
                Marca.FIAT,
                "Uno",
                2020,
                "Branco",
                new BigDecimal("10000.00"),
                StatusVeiculo.DISPONIVEL
        );

        when(buscarVeiculoPorIdUseCase.executar(id))
                .thenReturn(veiculo);

        // act + assert
        mockMvc.perform(get("/veiculos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.placa").value("ABC1D23"))
                .andExpect(jsonPath("$.marca").value("FIAT"))
                .andExpect(jsonPath("$.modelo").value("Uno"))
                .andExpect(jsonPath("$.ano").value(2020))
                .andExpect(jsonPath("$.cor").value("Branco"))
                .andExpect(jsonPath("$.preco").value(10000.00))
                .andExpect(jsonPath("$.status").value("DISPONIVEL"));

        verify(buscarVeiculoPorIdUseCase, times(1)).executar(id);
        verifyNoMoreInteractions(buscarVeiculoPorIdUseCase);
    }

    @Test
    void buscarPorId_quandoNaoEncontrado_deveRetornar404() throws Exception {
        // arrange
        UUID id = UUID.fromString("88888888-8888-8888-8888-888888888888");

        when(buscarVeiculoPorIdUseCase.executar(id))
                .thenThrow(new VeiculoNaoEncontradoException(id));

        // act + assert
        mockMvc.perform(get("/veiculos/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Veículo não encontrado. id=" + id))
                .andExpect(jsonPath("$.path").value("/veiculos/" + id));

        verify(buscarVeiculoPorIdUseCase, times(1)).executar(id);
        verifyNoMoreInteractions(buscarVeiculoPorIdUseCase);
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

        when(listarVeiculosUseCase.executar(eq(Optional.empty())))
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

        verify(listarVeiculosUseCase, times(1)).executar(eq(Optional.empty()));
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

        when(listarVeiculosUseCase.executar(eq(Optional.of(StatusVeiculo.DISPONIVEL))))
                .thenReturn(List.of(v1));

        // act + assert
        mockMvc.perform(get("/veiculos")
                        .queryParam("status", "DISPONIVEL"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(v1.getId().toString()))
                .andExpect(jsonPath("$[0].status").value("DISPONIVEL"));

        verify(listarVeiculosUseCase, times(1)).executar(eq(Optional.of(StatusVeiculo.DISPONIVEL)));
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

    @Test
    void remover_deveRetornar204EChamarUseCase() throws Exception {
        // arrange
        UUID id = UUID.fromString("44444444-4444-4444-4444-444444444444");

        // act + assert
        mockMvc.perform(delete("/veiculos/{id}", id))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(removerVeiculoUseCase, times(1)).executar(id);
        verifyNoMoreInteractions(removerVeiculoUseCase);
    }

    @Test
    void remover_quandoNaoEncontrado_deveRetornar404() throws Exception {
        // arrange
        UUID id = UUID.fromString("55555555-5555-5555-5555-555555555555");

        doThrow(new VeiculoNaoEncontradoException(id))
                .when(removerVeiculoUseCase).executar(id);

        // act + assert
        mockMvc.perform(delete("/veiculos/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Veículo não encontrado. id=" + id))
                .andExpect(jsonPath("$.path").value("/veiculos/" + id));

        verify(removerVeiculoUseCase, times(1)).executar(id);
        verifyNoMoreInteractions(removerVeiculoUseCase);
    }


}
