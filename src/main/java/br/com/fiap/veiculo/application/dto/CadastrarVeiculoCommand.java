package br.com.fiap.veiculo.application.dto;

import java.math.BigDecimal;

public record CadastrarVeiculoCommand(
        String marca,
        String modelo,
        int ano,
        String cor,
        BigDecimal preco
) {}
