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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Busca um veículo pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veículo encontrado"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponse> buscarPorId(
            @Parameter(description = "Identificador do veículo", required = true)
            @PathVariable UUID id
    ) {
        var veiculo = buscarVeiculoPorId.executar(id);
        return ResponseEntity.ok(VeiculoResponse.fromDomain(veiculo));
    }

    @Operation(summary = "Lista veículos, opcionalmente filtrando por status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de veículos retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> listar(
            @Parameter(
                    description = "Status do veículo (ex: DISPONIVEL, VENDIDO)",
                    example = "DISPONIVEL"
            )
            @RequestParam(required = false)
            @StatusVeiculoValida
            String status
    ) {
        Optional<StatusVeiculo> statusOptional = Optional.ofNullable(status)
                .map(StatusVeiculo::valueOf);

        List<Veiculo> veiculos = listarVeiculos.executar(statusOptional);
        List<VeiculoResponse> response = veiculos.stream()
                .map(VeiculoResponse::fromDomain)
                .toList();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cadastra um novo veículo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Veículo cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<CadastrarVeiculoResponse> cadastrar(
            @RequestBody @Valid CadastrarVeiculoRequest request
    ) {
        UUID veiculoId = cadastrarVeiculo.executar(request.toCommand());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CadastrarVeiculoResponse(veiculoId));
    }

    @Operation(summary = "Atualiza um veículo existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Veículo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @Parameter(description = "Identificador do veículo", required = true)
            @PathVariable UUID id,
            @RequestBody @Valid AtualizarVeiculoRequest request
    ) {
        atualizarVeiculo.executar(request.toCommand(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove um veículo por id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @Parameter(description = "Identificador do veículo", required = true)
            @PathVariable UUID id
    ) {
        removerVeiculo.executar(id);
        return ResponseEntity.noContent().build();
    }

}
