package br.com.fiap.veiculo.infrastructure.adapter.output.persistence;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoPersistenceAdapterTest {

    @Mock
    private VeiculoRepositoryJpa repositoryJpa;

    @InjectMocks
    private VeiculoPersistenceAdapter adapter;

    private Veiculo veiculoDominio;

    @BeforeEach
    void setUp() {
        veiculoDominio = new Veiculo(
                UUID.randomUUID(),
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
    void existePorPlaca_deveDelegarParaRepositoryJpa() {
        // arrange
        Placa placa = new Placa("ABC1D23");
        when(repositoryJpa.existsByPlaca("ABC1D23")).thenReturn(true);

        // act
        boolean existe = adapter.existePorPlaca(placa);

        // assert
        assertTrue(existe);
        verify(repositoryJpa, times(1)).existsByPlaca("ABC1D23");
        verifyNoMoreInteractions(repositoryJpa);
    }

    @Test
    void salvar_deveConverterDominioParaEntityESalvar() {
        // arrange
        ArgumentCaptor<VeiculoJpaEntity> captor =
                ArgumentCaptor.forClass(VeiculoJpaEntity.class);

        when(repositoryJpa.save(any(VeiculoJpaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // act
        Veiculo veiculoSalvo = adapter.salvar(veiculoDominio);

        // assert
        verify(repositoryJpa).save(captor.capture());
        VeiculoJpaEntity entityEnviada = captor.getValue();

        assertEquals(veiculoDominio.getId(), getField(entityEnviada, "id"));
        assertEquals("ABC1D23", getField(entityEnviada, "placa"));
        assertEquals(veiculoDominio.getMarca(), getField(entityEnviada, "marca"));
        assertEquals(veiculoDominio.getModelo(), getField(entityEnviada, "modelo"));
        assertEquals(veiculoDominio.getAno(), getField(entityEnviada, "ano"));
        assertEquals(veiculoDominio.getCor(), getField(entityEnviada, "cor"));
        assertEquals(veiculoDominio.getPreco(), getField(entityEnviada, "preco"));
        assertEquals(veiculoDominio.getStatus(), getField(entityEnviada, "status"));

        assertNotNull(veiculoSalvo);
        assertEquals(veiculoDominio.getId(), veiculoSalvo.getId());
        assertEquals(veiculoDominio.getPlaca(), veiculoSalvo.getPlaca());
        assertEquals(veiculoDominio.getMarca(), veiculoSalvo.getMarca());
        assertEquals(veiculoDominio.getModelo(), veiculoSalvo.getModelo());
        assertEquals(veiculoDominio.getAno(), veiculoSalvo.getAno());
        assertEquals(veiculoDominio.getCor(), veiculoSalvo.getCor());
        assertEquals(veiculoDominio.getPreco(), veiculoSalvo.getPreco());
        assertEquals(veiculoDominio.getStatus(), veiculoSalvo.getStatus());
    }

    // ===== Helpers de reflexão =====

    private static Object getField(Object target, String fieldName) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
