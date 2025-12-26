package br.com.fiap.veiculo.application.service;

import br.com.fiap.veiculo.application.dto.CadastrarVeiculoCommand;
import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.domain.exception.PlacaJaCadastradaException;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarVeiculoServiceTest {

    @Mock
    private VeiculoRepositoryPort repository;

    @InjectMocks
    private CadastrarVeiculoService service;

    private CadastrarVeiculoCommand command;

    @BeforeEach
    void setUp() {
        command = new CadastrarVeiculoCommand(
                new Placa("ABC1D23"),
                Marca.FIAT,
                "Uno",
                2020,
                "Branco",
                new BigDecimal("35000.00")
        );
    }

    @Test
    void deveCadastrarVeiculoComSucessoERetornarId() {
        // arrange
        when(repository.existePorPlaca(command.placa()))
                .thenReturn(false);

        when(repository.salvar(any(Veiculo.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // act
        UUID idRetornado = service.executar(command);

        // assert
        assertNotNull(idRetornado);

        verify(repository, times(1)).existePorPlaca(command.placa());
        verify(repository, times(1)).salvar(any(Veiculo.class));
    }

    @Test
    void deveAtivarVeiculoAntesDeSalvar() {
        // arrange
        when(repository.existePorPlaca(command.placa()))
                .thenReturn(false);

        ArgumentCaptor<Veiculo> veiculoCaptor =
                ArgumentCaptor.forClass(Veiculo.class);

        when(repository.salvar(veiculoCaptor.capture()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // act
        service.executar(command);

        // assert
        Veiculo veiculoSalvo = veiculoCaptor.getValue();

        assertNotNull(veiculoSalvo.getId());
        assertEquals(StatusVeiculo.DISPONIVEL, veiculoSalvo.getStatus());
        assertEquals(command.placa(), veiculoSalvo.getPlaca());
        assertEquals(command.marca(), veiculoSalvo.getMarca());
        assertEquals(command.modelo(), veiculoSalvo.getModelo());
    }

    @Test
    void deveLancarExcecaoQuandoPlacaJaExiste() {
        // arrange
        when(repository.existePorPlaca(command.placa()))
                .thenReturn(true);

        // act + assert
        assertThrows(
                PlacaJaCadastradaException.class,
                () -> service.executar(command)
        );

        verify(repository, times(1)).existePorPlaca(command.placa());
        verify(repository, never()).salvar(any());
    }
}