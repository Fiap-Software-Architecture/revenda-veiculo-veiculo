package br.com.fiap.veiculo.application.dto;

import br.com.fiap.veiculo.domain.model.Marca;

import java.math.BigDecimal;

public record CadastrarVeiculoCommand(
        Marca marca,
        String modelo,
        int ano,
        String cor,
        BigDecimal preco
) {}
