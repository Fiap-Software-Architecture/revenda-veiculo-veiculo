package br.com.fiap.veiculo.infrastructure.adapter.output.persistence;

import br.com.fiap.veiculo.domain.model.Marca;
import br.com.fiap.veiculo.domain.model.Placa;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.domain.model.Veiculo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VeiculoJpaEntityTest {

    @Test
    void fromDomain_deveMapearTodosOsCamposCorretamente() {
        // arrange
        UUID id = UUID.randomUUID();

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

        // act
        VeiculoJpaEntity entity = VeiculoJpaEntity.fromDomain(veiculo);

        // assert
        assertNotNull(entity);
        assertEquals(id, getField(entity, "id"));
        assertEquals("ABC1D23", getField(entity, "placa"));
        assertEquals(Marca.FIAT, getField(entity, "marca"));
        assertEquals("Uno", getField(entity, "modelo"));
        assertEquals(2020, getField(entity, "ano"));
        assertEquals("Branco", getField(entity, "cor"));
        assertEquals(new BigDecimal("35000.00"), getField(entity, "preco"));
        assertEquals(StatusVeiculo.DISPONIVEL, getField(entity, "status"));
    }

    @Test
    void toDomain_deveConverterEntityParaDominioCorretamente() {
        // arrange
        VeiculoJpaEntity entity = new VeiculoJpaEntity();

        setField(entity, "id", UUID.randomUUID());
        setField(entity, "placa", "ABC1234");
        setField(entity, "marca", Marca.TOYOTA);
        setField(entity, "modelo", "Corolla");
        setField(entity, "ano", 2022);
        setField(entity, "cor", "Preto");
        setField(entity, "preco", new BigDecimal("120000.00"));
        setField(entity, "status", StatusVeiculo.VENDIDO);

        // act
        Veiculo veiculo = entity.toDomain();

        // assert
        assertNotNull(veiculo);
        assertEquals("ABC1234", veiculo.getPlaca().getValue());
        assertEquals(Marca.TOYOTA, veiculo.getMarca());
        assertEquals("Corolla", veiculo.getModelo());
        assertEquals(2022, veiculo.getAno());
        assertEquals("Preto", veiculo.getCor());
        assertEquals(new BigDecimal("120000.00"), veiculo.getPreco());
        assertEquals(StatusVeiculo.VENDIDO, veiculo.getStatus());
    }

    @Test
    void roundTrip_domainParaEntityParaDomain_devePreservarDados() {
        // arrange
        Veiculo original = new Veiculo(
                UUID.randomUUID(),
                new Placa("XYZ9Z99"),
                Marca.FORD,
                "Focus",
                2018,
                "Azul",
                new BigDecimal("55000.00"),
                StatusVeiculo.INATIVO
        );

        // act
        VeiculoJpaEntity entity = VeiculoJpaEntity.fromDomain(original);
        Veiculo convertido = entity.toDomain();

        // assert
        assertEquals(original.getId(), convertido.getId());
        assertEquals(original.getPlaca(), convertido.getPlaca());
        assertEquals(original.getMarca(), convertido.getMarca());
        assertEquals(original.getModelo(), convertido.getModelo());
        assertEquals(original.getAno(), convertido.getAno());
        assertEquals(original.getCor(), convertido.getCor());
        assertEquals(original.getPreco(), convertido.getPreco());
        assertEquals(original.getStatus(), convertido.getStatus());
    }

    // ===== Helpers de reflexão (sem setters públicos) =====

    private static Object getField(Object target, String fieldName) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
