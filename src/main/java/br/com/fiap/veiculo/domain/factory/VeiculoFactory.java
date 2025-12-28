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
        validarDadosPadroes(placa, marca, modelo, ano, cor, preco);

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

    public static Veiculo atualizarCadastro(UUID id, Placa placa, Marca marca, String modelo, Integer ano, String cor, BigDecimal preco, StatusVeiculo status) {
        if (id == null) throw new DomainValidationException("id", "id é obrigatório");
        if (status == null) throw new DomainValidationException("status", "status é obrigatório");
        validarDadosPadroes(placa, marca, modelo, ano, cor, preco);

        return new Veiculo(
                id,
                placa,
                marca,
                modelo,
                ano,
                cor,
                preco,
                status
        );
    }

    private static void validarDadosPadroes(Placa placa, Marca marca, String modelo, Integer ano, String cor, BigDecimal preco) {
        if (placa == null) throw new DomainValidationException("placa", "placa é obrigatória");
        if (marca == null) throw new DomainValidationException("marca", "marca é obrigatória");
        if (modelo == null || modelo.isBlank()) throw new DomainValidationException("modelo", "modelo é obrigatório");
        if (cor == null || cor.isBlank()) throw new DomainValidationException("cor", "cor é obrigatória");
        if (ano == null) throw new DomainValidationException("ano", "ano é obrigatório");

        int anoAtual = Year.now().getValue();
        if (ano < 1900 || ano > anoAtual + 1) throw new DomainValidationException("ano", "ano inválido");

        if (preco == null) throw new DomainValidationException("preco", "preço é obrigatório");
        if (preco.signum() < 0) throw new DomainValidationException("preco", "preço não pode ser negativo");
    }

}
