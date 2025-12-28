package br.com.fiap.veiculo.application.port.input;

import br.com.fiap.veiculo.application.dto.AtualizarVeiculoCommand;

public interface AtualizarVeiculoUseCase {

    void executar(AtualizarVeiculoCommand command);

}
