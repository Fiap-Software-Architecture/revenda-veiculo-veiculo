package br.com.fiap.veiculo.infrastructure.adapter.input.rest.response;

import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.domain.model.Veiculo;

import java.math.BigDecimal;
import java.util.UUID;

public record VeiculoResponse(
        UUID id,
        String placa,
        String marca,
        String modelo,
        int ano,
        String cor,
        BigDecimal preco,
        StatusVeiculo status
) {

    public static VeiculoResponse fromDomain(Veiculo veiculo) {
        return new VeiculoResponse(
                veiculo.getId(),
                veiculo.getPlaca().getValue(),
                veiculo.getMarca().name(),
                veiculo.getModelo(),
                veiculo.getAno(),
                veiculo.getCor(),
                veiculo.getPreco(),
                veiculo.getStatus()
        );
    }

}

