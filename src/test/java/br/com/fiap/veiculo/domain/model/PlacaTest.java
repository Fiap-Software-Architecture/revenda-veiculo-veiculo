package br.com.fiap.veiculo.domain.model;

import br.com.fiap.veiculo.domain.exception.DomainValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlacaTest {

    @Test
    void deveCriarPlacaMercosulValida() {
        // act
        Placa placa = new Placa("ABC1D23");

        // assert
        assertEquals("ABC1D23", placa.getValue());
    }

    @Test
    void deveCriarPlacaAntigaValida() {
        // act
        Placa placa = new Placa("ABC1234");

        // assert
        assertEquals("ABC1234", placa.getValue());
    }

    @Test
    void deveNormalizarPlacaParaMaiusculoETrim() {
        // act
        Placa placa = new Placa(" abc1d23 ");

        // assert
        assertEquals("ABC1D23", placa.getValue());
    }

    @Test
    void deveLancarExcecaoQuandoPlacaNula() {
        // act + assert
        DomainValidationException exception = assertThrows(
                DomainValidationException.class,
                () -> new Placa(null)
        );

        assertEquals("placa", exception.getField());
        assertEquals("placa não pode ser nula", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoPlacaInvalida() {
        // act + assert
        DomainValidationException exception = assertThrows(
                DomainValidationException.class,
                () -> new Placa("123ABC")
        );

        assertEquals("placa", exception.getField());
        assertEquals("placa inválida", exception.getMessage());
    }

    @Test
    void placasComMesmoValorDevemSerIguais() {
        // arrange
        Placa placa1 = new Placa("ABC1D23");
        Placa placa2 = new Placa("abc1d23");

        // assert
        assertEquals(placa1, placa2);
        assertEquals(placa1.hashCode(), placa2.hashCode());
    }

    @Test
    void toStringDeveRetornarValorDaPlaca() {
        // act
        Placa placa = new Placa("ABC1234");

        // assert
        assertEquals("ABC1234", placa.toString());
    }
}
