package br.com.fiap.veiculo.infrastructure.adapter.input.rest;

import br.com.fiap.veiculo.application.port.input.AtualizarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.input.BuscarVeiculoPorIdUseCase;
import br.com.fiap.veiculo.application.port.input.CadastrarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.input.ListarVeiculosUseCase;
import br.com.fiap.veiculo.application.port.input.RemoverVeiculoUseCase;
import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import br.com.fiap.veiculo.domain.model.Veiculo;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.request.AtualizarVeiculoRequest;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.request.CadastrarVeiculoRequest;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.response.CadastrarVeiculoResponse;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.response.VeiculoResponse;
import br.com.fiap.veiculo.infrastructure.adapter.input.rest.validation.StatusVeiculoValida;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@AllArgsConstructor
public class VeiculoController {

    private final BuscarVeiculoPorIdUseCase buscarVeiculoPorId;
    private final ListarVeiculosUseCase listarVeiculos;
    private final CadastrarVeiculoUseCase cadastrarVeiculo;
    private final AtualizarVeiculoUseCase atualizarVeiculo;
    private final RemoverVeiculoUseCase removerVeiculo;

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponse> buscarPorId(
            @PathVariable UUID id
    ) {
        var veiculo = buscarVeiculoPorId.executar(id);
        return ResponseEntity.ok(VeiculoResponse.fromDomain(veiculo));
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> listar(
            @RequestParam(required = false) @StatusVeiculoValida String status
    ) {
        Optional<StatusVeiculo> statusOptional = Optional.ofNullable(status)
                .map(StatusVeiculo::valueOf);

        List<Veiculo> veiculos = listarVeiculos.executar(statusOptional);
        List<VeiculoResponse> response = veiculos.stream()
                .map(VeiculoResponse::fromDomain)
                .toList();

        return ResponseEntity.ok(response);
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
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @PathVariable UUID id
    ) {
        removerVeiculo.executar(id);
        return ResponseEntity.noContent().build();
    }

}

