package br.com.fiap.veiculo.domain.factory;

import br.com.fiap.veiculo.domain.model.Marca;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.domain.model.Veiculo;

import java.math.BigDecimal;
import java.time.Year;
import java.util.UUID;

public class VeiculoFactory {

    public static Veiculo novoCadastro(Marca marca, String modelo, Integer ano, String cor, BigDecimal preco) {

        if (marca == null) throw new IllegalArgumentException("marca é obrigatória");
        if (modelo == null || modelo.isBlank()) throw new IllegalArgumentException("modelo é obrigatório");
        if (cor == null || cor.isBlank()) throw new IllegalArgumentException("cor é obrigatória");
        if (preco == null || preco.signum() <= 0) throw new IllegalArgumentException("preço deve ser > 0");

        int anoAtual = Year.now().getValue();
        if (ano < 1886 || ano > anoAtual + 1) {
            throw new IllegalArgumentException("ano inválido: " + ano);
        }

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
