package br.com.fiap.veiculo.application.port.output;

import br.com.fiap.veiculo.domain.model.Placa;
import br.com.fiap.veiculo.domain.model.Veiculo;

public interface VeiculoRepositoryPort {

    boolean existePorPlaca(Placa placa);

    Veiculo salvar(Veiculo pedido);

}
