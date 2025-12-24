package br.com.fiap.veiculo.infrastructure.adapter.input.rest.request;

import br.com.fiap.veiculo.application.dto.CadastrarVeiculoCommand;
import br.com.fiap.veiculo.domain.model.Marca;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CadastrarVeiculoRequest {

    @NotNull
    private Marca marca;

    @NotBlank
    private String modelo;

    @Min(1900)
    private int ano;

    @NotBlank
    private String cor;

    @DecimalMin("0.0")
    private BigDecimal preco;

    public CadastrarVeiculoCommand toCommand() {
        return new CadastrarVeiculoCommand(
                marca, modelo, ano, cor, preco
        );
    }
}

