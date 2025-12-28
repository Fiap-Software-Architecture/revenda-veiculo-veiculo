package br.com.fiap.veiculo.application.port.input;

import java.util.UUID;

public interface RemoverVeiculoUseCase {

    void executar(UUID id);

}
