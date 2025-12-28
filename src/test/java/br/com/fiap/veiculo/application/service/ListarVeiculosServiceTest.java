package br.com.fiap.veiculo.application.service;

import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.domain.model.Marca;
import br.com.fiap.veiculo.domain.model.Placa;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.domain.model.Veiculo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarVeiculosServiceTest {

    @Mock
    private VeiculoRepositoryPort repository;

    @InjectMocks
    private ListarVeiculosService service;

    @Test
    void deveListarTodosOrdenadosPorPrecoQuandoStatusNaoInformado() {
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
                StatusVeiculo.INATIVO
        );

        List<Veiculo> esperado = List.of(v1, v2);
        when(repository.listarOrdenadoPorPreco()).thenReturn(esperado);

        // act
        List<Veiculo> resultado = service.listar(Optional.empty());

        // assert
        assertEquals(esperado, resultado);

        verify(repository, times(1)).listarOrdenadoPorPreco();
        verify(repository, never()).listarPorStatusOrdenadoPorPreco(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveListarFiltrandoPorStatusQuandoStatusInformado() {
        // arrange
        StatusVeiculo status = StatusVeiculo.DISPONIVEL;

        Veiculo v1 = new Veiculo(
                UUID.fromString("33333333-3333-3333-3333-333333333333"),
                new Placa("GHI7J89"),
                Marca.FIAT,
                "Argo",
                2021,
                "Vermelho",
                new BigDecimal("15000.00"),
                status
        );

        List<Veiculo> esperado = List.of(v1);
        when(repository.listarPorStatusOrdenadoPorPreco(status)).thenReturn(esperado);

        // act
        List<Veiculo> resultado = service.listar(Optional.of(status));

        // assert
        assertEquals(esperado, resultado);

        verify(repository, times(1)).listarPorStatusOrdenadoPorPreco(status);
        verify(repository, never()).listarOrdenadoPorPreco();
        verifyNoMoreInteractions(repository);
    }
}
