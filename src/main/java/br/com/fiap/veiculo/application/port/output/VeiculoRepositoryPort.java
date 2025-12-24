package br.com.fiap.veiculo.application.port.output;

import br.com.fiap.veiculo.domain.model.Veiculo;

public interface VeiculoRepositoryPort {

    Veiculo salvar(Veiculo pedido);

}
