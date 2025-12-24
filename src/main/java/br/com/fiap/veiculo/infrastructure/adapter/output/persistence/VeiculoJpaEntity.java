package br.com.fiap.veiculo.infrastructure.adapter.output.persistence;

import br.com.fiap.veiculo.domain.model.Marca;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.domain.model.Veiculo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "veiculos")
public class VeiculoJpaEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Marca marca;

    private String modelo;

    private int ano;

    private String cor;

    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    private StatusVeiculo status;

    public static VeiculoJpaEntity fromDomain(Veiculo veiculo) {
        VeiculoJpaEntity entity = new VeiculoJpaEntity();
        entity.id = veiculo.getId();
        entity.marca = veiculo.getMarca();
        entity.modelo = veiculo.getModelo();
        entity.ano = veiculo.getAno();
        entity.cor = veiculo.getCor();
        entity.preco = veiculo.getPreco();
        entity.status = veiculo.getStatus();
        return entity;
    }

    public Veiculo toDomain() {
        return new Veiculo(
                this.id,
                this.marca,
                this.modelo,
                this.ano,
                this.cor,
                this.preco,
                this.status
        );
    }
}

