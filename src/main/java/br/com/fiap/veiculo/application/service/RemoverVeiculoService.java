package br.com.fiap.veiculo.application.service;

import br.com.fiap.veiculo.application.port.input.RemoverVeiculoUseCase;
import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.domain.exception.VeiculoNaoEncontradoException;

import java.util.UUID;

public class RemoverVeiculoService implements RemoverVeiculoUseCase {

    private final VeiculoRepositoryPort repository;

    public RemoverVeiculoService(VeiculoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void executar(UUID id) {
        repository.buscarPorId(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(id));
        repository.removerPorId(id);
    }
}
