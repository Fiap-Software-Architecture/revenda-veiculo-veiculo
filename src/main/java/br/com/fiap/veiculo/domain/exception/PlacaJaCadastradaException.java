package br.com.fiap.veiculo.domain.exception;

import br.com.fiap.veiculo.domain.model.Placa;

public class PlacaJaCadastradaException extends RuntimeException {
    public PlacaJaCadastradaException(Placa placa) {
        super("Placa já cadastrada: " + placa.getValue());
    }
}
