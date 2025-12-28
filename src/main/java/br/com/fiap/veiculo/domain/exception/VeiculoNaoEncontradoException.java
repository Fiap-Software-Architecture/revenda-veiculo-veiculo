package br.com.fiap.veiculo.domain.exception;

import java.util.UUID;

public class VeiculoNaoEncontradoException extends RuntimeException {
    public VeiculoNaoEncontradoException(UUID id) {
        super("Veículo não encontrado. id=" + id);
    }
}
