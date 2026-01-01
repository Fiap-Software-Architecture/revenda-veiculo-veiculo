package br.com.fiap.veiculo.application.port.input;

import br.com.fiap.veiculo.domain.model.StatusVeiculo;

import java.util.UUID;

public interface AtualizarStatusVeiculoUseCase {

    void atualizarStatus(UUID veiculoId, StatusVeiculo status);

}
