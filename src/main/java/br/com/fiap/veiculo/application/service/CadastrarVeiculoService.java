package br.com.fiap.veiculo.application.service;

import br.com.fiap.veiculo.application.dto.CadastrarVeiculoCommand;
import br.com.fiap.veiculo.application.port.input.CadastrarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.domain.factory.VeiculoFactory;
import br.com.fiap.veiculo.domain.model.Veiculo;

import java.util.UUID;

public class CadastrarVeiculoService implements CadastrarVeiculoUseCase {

    private final VeiculoRepositoryPort repository;

    public CadastrarVeiculoService(VeiculoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public UUID executar(CadastrarVeiculoCommand command) {

        Veiculo veiculo = VeiculoFactory.novo(
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

