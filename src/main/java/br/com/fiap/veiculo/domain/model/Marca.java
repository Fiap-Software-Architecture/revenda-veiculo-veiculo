package br.com.fiap.veiculo.domain.model;

import br.com.fiap.veiculo.domain.exception.DomainValidationException;

import java.util.Arrays;

public enum Marca {
    TOYOTA,
    HONDA,
    FORD,
    CHEVROLET,
    VOLKSWAGEN;

    public static Marca from(String raw) {
        if (raw == null) {
            throw new DomainValidationException("marca", "marca não pode ser nula");
        }

        String normalized = raw.trim()
                .replace(" ", "_")
                .replace("-", "_")
                .toUpperCase();

        return Arrays.stream(values())
                .filter(v -> v.name().equals(normalized))
                .findFirst()
                .orElseThrow(() -> new DomainValidationException("marca", "marca inválida: " + raw));
    }
}

