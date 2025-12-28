package br.com.fiap.veiculo.application.service;

import br.com.fiap.veiculo.application.dto.CadastrarVeiculoCommand;
import br.com.fiap.veiculo.application.port.input.CadastrarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.domain.exception.PlacaJaCadastradaException;
import br.com.fiap.veiculo.domain.factory.VeiculoFactory;
import br.com.fiap.veiculo.domain.model.Veiculo;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class CadastrarVeiculoService implements CadastrarVeiculoUseCase {

    private final VeiculoRepositoryPort repository;

    @Override
    public UUID executar(CadastrarVeiculoCommand command) {

        if (repository.existePorPlaca(command.placa())) {
            throw new PlacaJaCadastradaException(command.placa());
        }

        Veiculo veiculo = VeiculoFactory.novoCadastro(
                command.placa(),
                command.marca(),
                command.modelo(),
                command.ano(),
                command.cor(),
                command.preco()
        );

        veiculo.ativar();
        Veiculo veiculoSalvo = repository.salvar(veiculo);

        return veiculoSalvo.getId();
    }
}

