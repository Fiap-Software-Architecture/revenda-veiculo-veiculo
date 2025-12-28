package br.com.fiap.veiculo.application.service;

import br.com.fiap.veiculo.application.port.input.BuscarVeiculoPorIdUseCase;
import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.domain.exception.VeiculoNaoEncontradoException;
import br.com.fiap.veiculo.domain.model.Veiculo;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class BuscarVeiculoPorIdService implements BuscarVeiculoPorIdUseCase {

    private final VeiculoRepositoryPort repository;

    @Override
    public Veiculo buscarPorId(UUID id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(id));
    }
}
