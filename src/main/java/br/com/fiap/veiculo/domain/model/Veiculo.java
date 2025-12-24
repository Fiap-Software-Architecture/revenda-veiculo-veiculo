package br.com.fiap.veiculo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Veiculo {

    private final UUID id;
    private final Marca marca;
    private final String modelo;
    private final int ano;
    private final String cor;
    private final BigDecimal preco;
    private StatusVeiculo status;

    public void ativar() {
        this.status = StatusVeiculo.DISPONIVEL;
    }
}

