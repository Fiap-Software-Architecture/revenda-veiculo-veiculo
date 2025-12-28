package br.com.fiap.veiculo.application.service;

import br.com.fiap.veiculo.application.dto.AtualizarVeiculoCommand;
import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.domain.exception.PlacaJaCadastradaException;
import br.com.fiap.veiculo.domain.exception.VeiculoNaoEncontradoException;
import br.com.fiap.veiculo.domain.model.Marca;
import br.com.fiap.veiculo.domain.model.Placa;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.domain.model.Veiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarVeiculoServiceTest {

    @Mock
    private VeiculoRepositoryPort repository;

    @InjectMocks
    private AtualizarVeiculoService service;

    private UUID id;
    private AtualizarVeiculoCommand command;

    @BeforeEach
    void setUp() {
        id = UUID.fromString("22222222-2222-2222-2222-222222222222");

        command = new AtualizarVeiculoCommand(
                id,
                new Placa("ABC1D23"),
                Marca.FIAT,
                "Uno",
                2020,
                "Branco",
                new BigDecimal("35000.00"),
                StatusVeiculo.DISPONIVEL
        );
    }

    @Test
    void deveAtualizarVeiculoComSucesso() {
        // arrange
        Veiculo veiculoAtual = new Veiculo(
                id,
                new Placa("OLD0A00"),
                Marca.FORD,
                "Ka",
                2018,
                "Preto",
                new BigDecimal("25000.00"),
                StatusVeiculo.INATIVO
        );

        when(repository.buscarPorId(id)).thenReturn(Optional.of(veiculoAtual));
        when(repository.existePorPlacaEIdDiferente(command.placa(), id)).thenReturn(false);

        ArgumentCaptor<Veiculo> veiculoCaptor = ArgumentCaptor.forClass(Veiculo.class);
        when(repository.salvar(veiculoCaptor.capture()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // act
        service.executar(command);

        // assert
        verify(repository, times(1)).buscarPorId(id);
        verify(repository, times(1)).existePorPlacaEIdDiferente(command.placa(), id);
        verify(repository, times(1)).salvar(any(Veiculo.class));

        Veiculo veiculoSalvo = veiculoCaptor.getValue();
        assertNotNull(veiculoSalvo);

        assertEquals(id, veiculoSalvo.getId());
        assertEquals(command.placa(), veiculoSalvo.getPlaca());
        assertEquals(command.marca(), veiculoSalvo.getMarca());
        assertEquals(command.modelo(), veiculoSalvo.getModelo());
        assertEquals(command.ano(), veiculoSalvo.getAno());
        assertEquals(command.cor(), veiculoSalvo.getCor());
        assertEquals(command.preco(), veiculoSalvo.getPreco());
        assertEquals(command.status(), veiculoSalvo.getStatus());
    }

    @Test
    void deveLancarExcecaoQuandoVeiculoNaoExiste() {
        // arrange
        when(repository.buscarPorId(id)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(
                VeiculoNaoEncontradoException.class,
                () -> service.executar(command)
        );

        verify(repository, times(1)).buscarPorId(id);
        verify(repository, never()).existePorPlacaEIdDiferente(any(), any());
        verify(repository, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoPlacaJaExisteParaOutroId() {
        // arrange
        Veiculo veiculoAtual = new Veiculo(
                id,
                new Placa("OLD0A00"),
                Marca.FORD,
                "Ka",
                2018,
                "Preto",
                new BigDecimal("25000.00"),
                StatusVeiculo.INATIVO
        );

        when(repository.buscarPorId(id)).thenReturn(Optional.of(veiculoAtual));
        when(repository.existePorPlacaEIdDiferente(command.placa(), id)).thenReturn(true);

        // act + assert
        assertThrows(
                PlacaJaCadastradaException.class,
                () -> service.executar(command)
        );

        verify(repository, times(1)).buscarPorId(id);
        verify(repository, times(1)).existePorPlacaEIdDiferente(command.placa(), id);
        verify(repository, never()).salvar(any());
    }
}
