package br.com.fiap.veiculo.infrastructure.adapter.input.rest;

import br.com.fiap.veiculo.application.port.input.AtualizarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.input.CadastrarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.input.ListarVeiculosUseCase;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.domain.model.Veiculo;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.request.AtualizarVeiculoRequest;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.request.CadastrarVeiculoRequest;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.response.CadastrarVeiculoResponse;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.response.VeiculoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final CadastrarVeiculoUseCase cadastrarVeiculo;
    private final AtualizarVeiculoUseCase atualizarVeiculo;
    private final ListarVeiculosUseCase listarVeiculos;

    public VeiculoController(
            CadastrarVeiculoUseCase cadastrarVeiculo,
            AtualizarVeiculoUseCase atualizarVeiculo,
            ListarVeiculosUseCase listarVeiculos
    ) {
        this.cadastrarVeiculo = cadastrarVeiculo;
        this.atualizarVeiculo = atualizarVeiculo;
        this.listarVeiculos = listarVeiculos;
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> listar(
            @RequestParam(required = false) StatusVeiculo status
    ) {
        var veiculos = listarVeiculos.listar(Optional.ofNullable(status));

        var response = veiculos.stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    private VeiculoResponse toResponse(Veiculo veiculo) {
        return new VeiculoResponse(
                veiculo.getId(),
                veiculo.getPlaca().getValue(),
                veiculo.getMarca().name(),
                veiculo.getModelo(),
                veiculo.getPreco(),
                veiculo.getStatus()
        );
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

