package br.com.fiap.veiculo.infrastructure.adapter.input.rest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = StatusVeiculoValidaValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StatusVeiculoValida {
    String message() default "status inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
