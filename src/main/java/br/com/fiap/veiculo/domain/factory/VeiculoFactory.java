package br.com.fiap.veiculo.domain.factory;

import br.com.fiap.veiculo.domain.exception.DomainValidationException;
import br.com.fiap.veiculo.domain.model.Marca;
import br.com.fiap.veiculo.domain.model.Placa;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.domain.model.Veiculo;

import java.math.BigDecimal;
import java.time.Year;
import java.util.UUID;

public class VeiculoFactory {

    public static Veiculo novoCadastro(Placa placa, Marca marca, String modelo, Integer ano, String cor, BigDecimal preco) {

        if (placa == null) throw new DomainValidationException("placa", "placa é obrigatória");
        if (marca == null) throw new DomainValidationException("marca", "marca é obrigatória");
        if (modelo == null || modelo.isBlank()) throw new DomainValidationException("modelo", "modelo é obrigatório");
        if (cor == null || cor.isBlank()) throw new DomainValidationException("cor", "cor é obrigatória");
        if (preco == null || preco.signum() <= 0) throw new DomainValidationException("preco", "preço deve ser > 0");

        int anoAtual = Year.now().getValue();
        if (ano < 1886 || ano > anoAtual + 1) {
            throw new DomainValidationException("ano", "ano inválido: " + ano);
        }

        return new Veiculo(
                UUID.randomUUID(),
                placa,
                marca,
                modelo,
                ano,
                cor,
                preco,
                StatusVeiculo.INATIVO
        );
    }

}
