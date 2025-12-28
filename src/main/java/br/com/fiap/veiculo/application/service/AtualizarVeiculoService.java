package br.com.fiap.veiculo.application.service;

import br.com.fiap.veiculo.application.dto.AtualizarVeiculoCommand;
import br.com.fiap.veiculo.application.port.input.AtualizarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.domain.exception.PlacaJaCadastradaException;
import br.com.fiap.veiculo.domain.exception.VeiculoNaoEncontradoException;
import br.com.fiap.veiculo.domain.factory.VeiculoFactory;
import br.com.fiap.veiculo.domain.model.Veiculo;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AtualizarVeiculoService implements AtualizarVeiculoUseCase {

    private final VeiculoRepositoryPort repository;

    @Override
    public void executar(AtualizarVeiculoCommand command) {
        UUID id = command.id();

        Veiculo atual = repository.buscarPorId(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(id));

        if (repository.existePorPlacaEIdDiferente(command.placa(), id)) {
            throw new PlacaJaCadastradaException(command.placa());
        }

        Veiculo atualizado = VeiculoFactory.atualizarCadastro(
                atual.getId(),
                command.placa(),
                command.marca(),
                command.modelo(),
                command.ano(),
                command.cor(),
                command.preco(),
                command.status()
        );

        repository.salvar(atualizado);
    }
}
