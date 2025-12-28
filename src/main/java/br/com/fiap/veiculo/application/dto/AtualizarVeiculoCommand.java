package br.com.fiap.veiculo.application.dto;

import br.com.fiap.veiculo.domain.model.Marca;
import br.com.fiap.veiculo.domain.model.Placa;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;

import java.math.BigDecimal;
import java.util.UUID;

public record AtualizarVeiculoCommand(
        UUID id,
        Placa placa,
        Marca marca,
        String modelo,
        int ano,
        String cor,
        BigDecimal preco,
        StatusVeiculo status
) {}
