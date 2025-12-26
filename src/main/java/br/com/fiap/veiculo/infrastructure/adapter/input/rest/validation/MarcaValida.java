package br.com.fiap.veiculo.infrastructure.adapter.input.rest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = MarcaValidaValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MarcaValida {
    String message() default "marca inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
