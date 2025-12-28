package br.com.fiap.veiculo.application.service;

import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.domain.exception.VeiculoNaoEncontradoException;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoverVeiculoServiceTest {

    @Mock
    private VeiculoRepositoryPort repository;

    @InjectMocks
    private RemoverVeiculoService service;

    @Test
    void deveRemoverVeiculoComSucesso() {
        // arrange
        UUID id = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        Veiculo veiculo = new Veiculo(
                id,
                new Placa("ABC1D23"),
                Marca.FIAT,
                "Uno",
                2020,
                "Branco",
                new BigDecimal("35000.00"),
                StatusVeiculo.DISPONIVEL
        );

        when(repository.buscarPorId(id)).thenReturn(Optional.of(veiculo));

        // act
        service.executar(id);

        // assert
        verify(repository, times(1)).buscarPorId(id);
        verify(repository, times(1)).removerPorId(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveLancarExcecaoQuandoVeiculoNaoEncontrado() {
        // arrange
        UUID id = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
        when(repository.buscarPorId(id)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(VeiculoNaoEncontradoException.class, () -> service.executar(id));

        verify(repository, times(1)).buscarPorId(id);
        verify(repository, never()).removerPorId(any());
        verifyNoMoreInteractions(repository);
    }
}
