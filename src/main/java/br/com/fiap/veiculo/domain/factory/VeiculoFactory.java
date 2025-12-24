package br.com.fiap.veiculo.domain.factory;

import br.com.fiap.veiculo.domain.model.Marca;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.domain.model.Veiculo;

import java.math.BigDecimal;
import java.util.UUID;

public class VeiculoFactory {

    public static Veiculo novo(Marca marca, String modelo, Integer ano, String cor, BigDecimal preco) {
        return new Veiculo(
                UUID.randomUUID(),
                marca,
                modelo,
                ano,
                cor,
                preco,
                StatusVeiculo.INATIVO
        );
    }

}
