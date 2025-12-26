package br.com.fiap.veiculo.domain.model;

import br.com.fiap.veiculo.domain.exception.DomainValidationException;
import lombok.Data;

import java.util.Objects;

@Data
public class Placa {

    private final String value;

    public Placa(String value) {
        if (value == null) {
            throw new DomainValidationException("placa", "placa não pode ser nula");
        }

        String normalized = value.trim().toUpperCase();

        boolean mercosul = normalized.matches("^[A-Z]{3}[0-9][A-Z][0-9]{2}$");
        boolean antigo = normalized.matches("^[A-Z]{3}[0-9]{4}$");

        if (!mercosul && !antigo) {
            throw new DomainValidationException("placa", "placa inválida");
        }

        this.value = normalized;
    }

    @Override public boolean equals(Object o) {
        return (o instanceof Placa other) && Objects.equals(this.value, other.value);
    }

    @Override public int hashCode() {
        return Objects.hash(value);
    }

    @Override public String toString() {
        return value;
    }

}
