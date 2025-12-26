package br.com.fiap.veiculo.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VeiculoTest {

    @Test
    void ativar_deveAlterarStatusParaDisponivel() {
        // arrange
        Veiculo veiculo = new Veiculo(
                UUID.randomUUID(),
                new Placa("ABC1D23"),
                Marca.FIAT,
                "Uno",
                2020,
                "Branco",
                new BigDecimal("35000.00"),
                StatusVeiculo.INATIVO
        );

        assertEquals(StatusVeiculo.INATIVO, veiculo.getStatus());

        // act
        veiculo.ativar();

        // assert
        assertEquals(StatusVeiculo.DISPONIVEL, veiculo.getStatus());
    }

    @Test
    void ativar_deveSerIdempotente() {
        // arrange
        Veiculo veiculo = new Veiculo(
                UUID.randomUUID(),
                new Placa("ABC1D23"),
                Marca.FIAT,
                "Uno",
                2020,
                "Branco",
                new BigDecimal("35000.00"),
                StatusVeiculo.DISPONIVEL
        );

        // act
        veiculo.ativar();

        // assert
        assertEquals(StatusVeiculo.DISPONIVEL, veiculo.getStatus());
    }
}