package br.com.fiap.veiculo.application.dto;

import br.com.fiap.veiculo.domain.model.Marca;
import br.com.fiap.veiculo.domain.model.Placa;

import java.math.BigDecimal;

public record CadastrarVeiculoCommand(
        Placa placa,
        Marca marca,
        String modelo,
        int ano,
        String cor,
        BigDecimal preco
) {}
