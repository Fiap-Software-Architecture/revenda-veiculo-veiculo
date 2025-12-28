package br.com.fiap.veiculo.infrastructure.adapter.input.rest.request;

import br.com.fiap.veiculo.application.dto.AtualizarVeiculoCommand;
import br.com.fiap.veiculo.domain.model.Marca;
import br.com.fiap.veiculo.domain.model.Placa;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AtualizarVeiculoRequestTest {

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
        UUID id = UUID.fromString("22222222-2222-2222-2222-222222222222");

        AtualizarVeiculoRequest request = new AtualizarVeiculoRequest();
        request.setPlaca("AAA0001");
        request.setMarca(Marca.FIAT.name());
        request.setModelo("Uno");
        request.setAno(2020);
        request.setCor("Branco");
        request.setPreco(new BigDecimal("35000.00"));
        request.setStatus(StatusVeiculo.DISPONIVEL.name());

        // act
        AtualizarVeiculoCommand cmd = request.toCommand(id);

        // assert
        assertNotNull(cmd);
        assertEquals(id, cmd.id());
        assertEquals(new Placa("AAA0001"), cmd.placa());
        assertEquals(Marca.FIAT, cmd.marca());
        assertEquals("Uno", cmd.modelo());
        assertEquals(2020, cmd.ano());
        assertEquals("Branco", cmd.cor());
        assertEquals(new BigDecimal("35000.00"), cmd.preco());
        assertEquals(StatusVeiculo.DISPONIVEL, cmd.status());
    }

    @Test
    void validation_deveFalharQuandoMarcaNula() {
        AtualizarVeiculoRequest request = requestValido();
        request.setMarca(null);

        Set<ConstraintViolation<AtualizarVeiculoRequest>> violations = validator.validate(request);

        assertHasViolationForField(violations, "marca");
    }

    @Test
    void validation_deveFalharQuandoModeloEmBranco() {
        AtualizarVeiculoRequest request = requestValido();
        request.setModelo("   ");

        Set<ConstraintViolation<AtualizarVeiculoRequest>> violations = validator.validate(request);

        assertHasViolationForField(violations, "modelo");
    }

    @Test
    void validation_deveFalharQuandoAnoMenorQue1886() {
        AtualizarVeiculoRequest request = requestValido();
        request.setAno(1885);

        Set<ConstraintViolation<AtualizarVeiculoRequest>> violations = validator.validate(request);

        assertHasViolationForField(violations, "ano");
    }

    @Test
    void validation_deveFalharQuandoCorEmBranco() {
        AtualizarVeiculoRequest request = requestValido();
        request.setCor(" ");

        Set<ConstraintViolation<AtualizarVeiculoRequest>> violations = validator.validate(request);

        assertHasViolationForField(violations, "cor");
    }

    @Test
    void validation_deveFalharQuandoPrecoNulo() {
        AtualizarVeiculoRequest request = requestValido();
        request.setPreco(null);

        Set<ConstraintViolation<AtualizarVeiculoRequest>> violations = validator.validate(request);

        assertHasViolationForField(violations, "preco");
    }

    @Test
    void validation_deveFalharQuandoPrecoNegativo() {
        AtualizarVeiculoRequest request = requestValido();
        request.setPreco(new BigDecimal("-1.00"));

        Set<ConstraintViolation<AtualizarVeiculoRequest>> violations = validator.validate(request);

        assertHasViolationForField(violations, "preco");
    }

    @Test
    void validation_deveFalharQuandoStatusNulo() {
        AtualizarVeiculoRequest request = requestValido();
        request.setStatus(null);

        Set<ConstraintViolation<AtualizarVeiculoRequest>> violations = validator.validate(request);

        assertHasViolationForField(violations, "status");
    }

    @Test
    void validation_deveFalharQuandoStatusInvalido() {
        AtualizarVeiculoRequest request = requestValido();
        request.setStatus("STATUS_QUE_NAO_EXISTE");

        Set<ConstraintViolation<AtualizarVeiculoRequest>> violations = validator.validate(request);

        assertHasViolationForField(violations, "status");
    }

    private static AtualizarVeiculoRequest requestValido() {
        AtualizarVeiculoRequest request = new AtualizarVeiculoRequest();
        request.setPlaca("AAA0001");
        request.setMarca(Marca.FIAT.name());
        request.setModelo("Uno");
        request.setAno(2020);
        request.setCor("Branco");
        request.setPreco(new BigDecimal("35000.00"));
        request.setStatus(StatusVeiculo.DISPONIVEL.name());
        return request;
    }

    private static void assertHasViolationForField(
            Set<ConstraintViolation<AtualizarVeiculoRequest>> violations,
            String fieldName
    ) {
        assertFalse(violations.isEmpty(), "Esperava violações, mas não houve nenhuma.");

        boolean found = violations.stream()
                .anyMatch(v -> v.getPropertyPath() != null && fieldName.equals(v.getPropertyPath().toString()));

        assertTrue(found, () -> "Esperava violação no campo '" + fieldName + "', mas veio: " + violations);
    }
}
