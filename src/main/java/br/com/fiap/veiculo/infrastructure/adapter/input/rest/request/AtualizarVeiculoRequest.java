package br.com.fiap.veiculo.infrastructure.adapter.input.rest.request;

import br.com.fiap.veiculo.application.dto.AtualizarVeiculoCommand;
import br.com.fiap.veiculo.domain.model.Marca;
import br.com.fiap.veiculo.domain.model.Placa;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.validation.MarcaValida;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.validation.StatusVeiculoValida;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AtualizarVeiculoRequest {

    @NotBlank
    private String placa;

    @NotBlank
    @MarcaValida
    private String marca;

    @NotBlank
    private String modelo;

    @Min(1886)
    private int ano;

    @NotBlank
    private String cor;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal preco;

    @NotNull
    @StatusVeiculoValida
    private String status;

    public AtualizarVeiculoCommand toCommand(UUID id) {
        return new AtualizarVeiculoCommand(
                id,
                new Placa(placa),
                Marca.from(marca.toUpperCase()),
                modelo,
                ano,
                cor,
                preco,
                StatusVeiculo.valueOf(status)
        );
    }

}
