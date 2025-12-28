package br.com.fiap.veiculo.application.port.input;

import br.com.fiap.veiculo.domain.model.Veiculo;

import java.util.UUID;

public interface BuscarVeiculoPorIdUseCase {

    Veiculo executar(UUID id);

}
