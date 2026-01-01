package br.com.fiap.veiculo.infrastructure.adapter.input.rest.request;

import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusVeiculoRequest (
        @NotNull StatusVeiculo status
) {}
