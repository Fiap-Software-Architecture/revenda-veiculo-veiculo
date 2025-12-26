package br.com.fiap.veiculo.infrastructure.adapter.input.rest.request;

import br.com.fiap.veiculo.application.dto.CadastrarVeiculoCommand;
import br.com.fiap.veiculo.domain.model.Marca;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CadastrarVeiculoRequestTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void initValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidatorFactory() {
        validatorFactory.close();
    }

    @Test
    void toCommand_deveMapearCamposCorretamente() {
        // arrange
        CadastrarVeiculoRequest request = new CadastrarVeiculoRequest();
        request.setPlaca("AAA0001");
        request.setMarca(Marca.FIAT.name());
        request.setModelo("Uno");
        request.setAno(2020);
        request.setCor("Branco");
        request.setPreco(new BigDecimal("35000.00"));

        // act
        CadastrarVeiculoCommand cmd = request.toCommand();

        // assert
        assertNotNull(cmd);
        assertEquals(Marca.FIAT, cmd.marca());
        assertEquals("Uno", cmd.modelo());
        assertEquals(2020, cmd.ano());
        assertEquals("Branco", cmd.cor());
        assertEquals(new BigDecimal("35000.00"), cmd.preco());
    }

    @Test
    void validation_deveFalharQuandoMarcaNula() {
        CadastrarVeiculoRequest request = requestValido();
        request.setMarca(null);

        Set<ConstraintViolation<CadastrarVeiculoRequest>> violations = validator.validate(request);

        assertHasViolationForField(violations, "marca");
    }

    @Test
    void validation_deveFalharQuandoModeloEmBranco() {
        CadastrarVeiculoRequest request = requestValido();
        request.setModelo("   ");

        Set<ConstraintViolation<CadastrarVeiculoRequest>> violations = validator.validate(request);

        assertHasViolationForField(violations, "modelo");
    }

    @Test
    void validation_deveFalharQuandoAnoMenorQue1900() {
        CadastrarVeiculoRequest request = requestValido();
        request.setAno(1899);

        Set<ConstraintViolation<CadastrarVeiculoRequest>> violations = validator.validate(request);

        assertHasViolationForField(violations, "ano");
    }

    @Test
    void validation_deveFalharQuandoCorEmBranco() {
        CadastrarVeiculoRequest request = requestValido();
        request.setCor("");

        Set<ConstraintViolation<CadastrarVeiculoRequest>> violations = validator.validate(request);

        assertHasViolationForField(violations, "cor");
    }

    @Test
    void validation_deveFalharQuandoPrecoNegativo() {
        CadastrarVeiculoRequest request = requestValido();
        request.setPreco(new BigDecimal("-0.01"));

        Set<ConstraintViolation<CadastrarVeiculoRequest>> violations = validator.validate(request);

        assertHasViolationForField(violations, "preco");
    }

    @Test
    void validation_devePassarQuandoPrecoNulo() {
        // @DecimalMin não falha para null (Bean Validation padrão)
        CadastrarVeiculoRequest request = requestValido();
        request.setPreco(null);

        Set<ConstraintViolation<CadastrarVeiculoRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), () -> "Esperava zero violações, mas veio: " + violations);
    }

    private static CadastrarVeiculoRequest requestValido() {
        CadastrarVeiculoRequest request = new CadastrarVeiculoRequest();
        request.setPlaca("AAA0001");
        request.setMarca(Marca.FIAT.name());
        request.setModelo("Uno");
        request.setAno(2020);
        request.setCor("Branco");
        request.setPreco(new BigDecimal("35000.00"));
        return request;
    }

    private static void assertHasViolationForField(
            Set<ConstraintViolation<CadastrarVeiculoRequest>> violations,
            String fieldName
    ) {
        assertFalse(violations.isEmpty(), "Esperava violações, mas não houve nenhuma.");

        boolean found = violations.stream()
                .anyMatch(v -> v.getPropertyPath() != null && fieldName.equals(v.getPropertyPath().toString()));

        assertTrue(found, () -> "Esperava violação no campo '" + fieldName + "', mas veio: " + violations);
    }
}
