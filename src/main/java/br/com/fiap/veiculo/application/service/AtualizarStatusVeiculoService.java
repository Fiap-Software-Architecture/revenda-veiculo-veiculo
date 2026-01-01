package br.com.fiap.veiculo.application.service;

import br.com.fiap.veiculo.application.port.input.AtualizarStatusVeiculoUseCase;
import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.domain.exception.VeiculoNaoEncontradoException;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.domain.model.Veiculo;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class AtualizarStatusVeiculoService implements AtualizarStatusVeiculoUseCase {

    private final VeiculoRepositoryPort repository;

    @Override
    public void atualizarStatus(UUID veiculoId, StatusVeiculo status) {

        Veiculo veiculo = repository.buscarPorId(veiculoId)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(veiculoId));

        veiculo.atualizarStatus(status);

        repository.salvar(veiculo);
    }

}
