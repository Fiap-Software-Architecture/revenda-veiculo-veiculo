package br.com.fiap.veiculo.application.port.output;

import br.com.fiap.veiculo.domain.model.Placa;
import br.com.fiap.veiculo.domain.model.Veiculo;

import java.util.Optional;
import java.util.UUID;

public interface VeiculoRepositoryPort {

    boolean existePorPlaca(Placa placa);

    boolean existePorPlacaEIdDiferente(Placa placa, UUID id);

    Optional<Veiculo> buscarPorId(UUID id);

    Veiculo salvar(Veiculo pedido);

}
