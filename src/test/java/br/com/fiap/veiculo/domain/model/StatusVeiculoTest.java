package br.com.fiap.veiculo.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusVeiculoTest {

    @Test
    void deveConterTodosOsStatusEsperados() {
        // act
        StatusVeiculo[] values = StatusVeiculo.values();

        // assert
        assertArrayEquals(
                new StatusVeiculo[]{
                        StatusVeiculo.DISPONIVEL,
                        StatusVeiculo.VENDIDO,
                        StatusVeiculo.RESERVADO,
                        StatusVeiculo.INATIVO
                },
                values
        );
    }

    @Test
    void valueOf_deveRetornarEnumQuandoValorValido() {
        // act
        StatusVeiculo status = StatusVeiculo.valueOf("DISPONIVEL");

        // assert
        assertEquals(StatusVeiculo.DISPONIVEL, status);
    }

    @Test
    void valueOf_deveLancarExcecaoQuandoValorInvalido() {
        // act + assert
        assertThrows(
                IllegalArgumentException.class,
                () -> StatusVeiculo.valueOf("EM_MANUTENCAO")
        );
    }
}
