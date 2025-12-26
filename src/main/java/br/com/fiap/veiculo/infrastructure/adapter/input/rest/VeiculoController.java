package br.com.fiap.veiculo.infrastructure.adapter.input.rest;

import br.com.fiap.veiculo.application.port.input.CadastrarVeiculoUseCase;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.request.CadastrarVeiculoRequest;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.response.CadastrarVeiculoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final CadastrarVeiculoUseCase cadastrarVeiculo;

    public VeiculoController(CadastrarVeiculoUseCase cadastrarVeiculo) {
        this.cadastrarVeiculo = cadastrarVeiculo;
    }

    @PostMapping
    public ResponseEntity<CadastrarVeiculoResponse> cadastrar(
            @RequestBody @Valid CadastrarVeiculoRequest request
    ) {
        UUID veiculoId = cadastrarVeiculo.executar(request.toCommand());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CadastrarVeiculoResponse(veiculoId));
    }
}

