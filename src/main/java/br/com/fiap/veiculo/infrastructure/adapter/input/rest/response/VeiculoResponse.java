package br.com.fiap.veiculo.infrastructure.adapter.input.rest.response;

import br.com.fiap.veiculo.domain.model.StatusVeiculo;

import java.math.BigDecimal;
import java.util.UUID;

public record VeiculoResponse(
        UUID id,
        String placa,
        String marca,
        String modelo,
        BigDecimal preco,
        StatusVeiculo status
) {}

