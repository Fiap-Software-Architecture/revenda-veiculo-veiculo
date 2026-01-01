package br.com.fiap.veiculo.domain.model;

import br.com.fiap.veiculo.domain.exception.DomainValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Veiculo {

    private final UUID id;
    private final Placa placa;
    private final Marca marca;
    private final String modelo;
    private final int ano;
    private final String cor;
    private final BigDecimal preco;
    private StatusVeiculo status;

    public void ativar() {
        this.status = StatusVeiculo.DISPONIVEL;
    }

    public void atualizarStatus(StatusVeiculo novoStatus) {
        if (novoStatus == null) {
            throw new DomainValidationException("status", "Status do veículo é obrigatório");
        }

        if (this.status == StatusVeiculo.VENDIDO) {
            throw new DomainValidationException(
                    "status",
                    "Não é permitido alterar o status de um veículo já vendido"
            );
        }

        this.status = novoStatus;
    }

}

