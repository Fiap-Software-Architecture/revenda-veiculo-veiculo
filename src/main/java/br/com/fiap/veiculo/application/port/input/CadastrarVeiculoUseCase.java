package br.com.fiap.veiculo.application.port.input;

import br.com.fiap.veiculo.application.dto.CadastrarVeiculoCommand;

import java.util.UUID;

public interface CadastrarVeiculoUseCase {

    UUID executar(CadastrarVeiculoCommand command);

}
