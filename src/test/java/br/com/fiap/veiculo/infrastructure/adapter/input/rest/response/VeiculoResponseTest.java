package br.com.fiap.veiculo.infrastructure.adapter.input.rest.response;

import br.com.fiap.veiculo.domain.model.Marca;
import br.com.fiap.veiculo.domain.model.Placa;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.domain.model.Veiculo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VeiculoResponseTest {

    @Test
    void deveConverterVeiculoDomainParaVeiculoResponse() {
        // arrange
        UUID id = UUID.randomUUID();

        Veiculo veiculo = new Veiculo(
                id,
                new Placa("ABC1D23"),
                Marca.FIAT,
                "Uno",
                2020,
                "Branco",
                new BigDecimal("45000.00"),
                StatusVeiculo.DISPONIVEL
        );

        // act
        VeiculoResponse response = VeiculoResponse.fromDomain(veiculo);

        // assert
        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals("ABC1D23", response.placa());
        assertEquals("FIAT", response.marca());
        assertEquals("Uno", response.modelo());
        assertEquals(2020, response.ano());
        assertEquals("Branco", response.cor());
        assertEquals(new BigDecimal("45000.00"), response.preco());
        assertEquals(StatusVeiculo.DISPONIVEL, response.status());
    }
}
