package br.com.fiap.veiculo.infrastructure.adapter.input.rest.validation;

import br.com.fiap.veiculo.domain.exception.DomainValidationException;
import br.com.fiap.veiculo.domain.model.Marca;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MarcaValidaValidator implements ConstraintValidator<MarcaValida, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) return true;

        try {
            Marca.from(value);
            return true;
        } catch (DomainValidationException ex) {
            return false;
        }
    }
}
