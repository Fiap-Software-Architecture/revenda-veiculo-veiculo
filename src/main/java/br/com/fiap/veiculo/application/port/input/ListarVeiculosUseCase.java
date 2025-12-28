package br.com.fiap.veiculo.application.port.input;

import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.domain.model.Veiculo;

import java.util.List;
import java.util.Optional;

public interface ListarVeiculosUseCase {
    List<Veiculo> listar(Optional<StatusVeiculo> status);
}

