package br.com.fiap.veiculo.application.service;

import br.com.fiap.veiculo.application.port.input.ListarVeiculosUseCase;
import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.domain.model.Veiculo;

import java.util.List;
import java.util.Optional;

public class ListarVeiculosService implements ListarVeiculosUseCase {

    private final VeiculoRepositoryPort repository;

    public ListarVeiculosService(VeiculoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Veiculo> listar(Optional<StatusVeiculo> status) {
        return status
                .map(repository::listarPorStatusOrdenadoPorPreco)
                .orElseGet(repository::listarOrdenadoPorPreco);
    }
}

