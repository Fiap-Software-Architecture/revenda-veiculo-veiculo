package br.com.fiap.veiculo.infrastructure.adapter.input.rest;

import br.com.fiap.veiculo.application.port.input.AtualizarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.input.CadastrarVeiculoUseCase;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.request.AtualizarVeiculoRequest;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.request.CadastrarVeiculoRequest;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.response.CadastrarVeiculoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final CadastrarVeiculoUseCase cadastrarVeiculo;
    private final AtualizarVeiculoUseCase atualizarVeiculo;

    public VeiculoController(
            CadastrarVeiculoUseCase cadastrarVeiculo,
            AtualizarVeiculoUseCase atualizarVeiculo
    ) {
        this.cadastrarVeiculo = cadastrarVeiculo;
        this.atualizarVeiculo = atualizarVeiculo;
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

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid AtualizarVeiculoRequest request
    ) {
        atualizarVeiculo.executar(request.toCommand(id));
        return ResponseEntity.noContent().build(); // 204
    }

}

