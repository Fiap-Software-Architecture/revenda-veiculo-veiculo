package br.com.fiap.veiculo.infrastructure.adapter.input.rest.validation;

import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusVeiculoValidaValidator implements ConstraintValidator<StatusVeiculoValida, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) return true;

        try {
            StatusVeiculo.valueOf(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
