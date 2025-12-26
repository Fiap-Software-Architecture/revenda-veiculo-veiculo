package br.com.fiap.veiculo.domain.model;

import br.com.fiap.veiculo.domain.exception.DomainValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarcaTest {

    @Test
    void from_deveConverterStringValidaParaEnum() {
        // act
        Marca marca = Marca.from("fiat");

        // assert
        assertEquals(Marca.FIAT, marca);
    }

    @Test
    void from_deveConverterStringMaiuscula() {
        // act
        Marca marca = Marca.from("FORD");

        // assert
        assertEquals(Marca.FORD, marca);
    }

    @Test
    void from_deveConverterStringMinuscula() {
        // act
        Marca marca = Marca.from("toyota");

        // assert
        assertEquals(Marca.TOYOTA, marca);
    }

    @Test
    void from_deveLancarExcecaoQuandoMarcaInvalida() {
        // act + assert
        DomainValidationException exception = assertThrows(
                DomainValidationException.class,
                () -> Marca.from("FERRARI")
        );

        assertTrue(exception.getMessage().contains("marca inválida: FERRARI"));
    }

    @Test
    void from_deveLancarExcecaoQuandoValorNulo() {
        // act + assert
        assertThrows(
                DomainValidationException.class,
                () -> Marca.from(null)
        );
    }

}