package br.com.fiap.veiculo.infrastructure.config;

import br.com.fiap.veiculo.application.port.input.AtualizarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.input.BuscarVeiculoPorIdUseCase;
import br.com.fiap.veiculo.application.port.input.CadastrarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.input.ListarVeiculosUseCase;
import br.com.fiap.veiculo.application.port.input.RemoverVeiculoUseCase;
import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.application.service.AtualizarVeiculoService;
import br.com.fiap.veiculo.application.service.BuscarVeiculoPorIdService;
import br.com.fiap.veiculo.application.service.CadastrarVeiculoService;
import br.com.fiap.veiculo.application.service.ListarVeiculosService;
import br.com.fiap.veiculo.application.service.RemoverVeiculoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    CadastrarVeiculoUseCase cadastrarVeiculoUseCase(
            VeiculoRepositoryPort repository
    ) {
        return new CadastrarVeiculoService(repository);
    }

    @Bean
    AtualizarVeiculoUseCase atualizarVeiculoUseCase(
            VeiculoRepositoryPort repository
    ) {
        return new AtualizarVeiculoService(repository);
    }

    @Bean
    ListarVeiculosUseCase listarVeiculosUseCase(
            VeiculoRepositoryPort repository
    ) {
        return new ListarVeiculosService(repository);
    }

    @Bean
    public BuscarVeiculoPorIdUseCase buscarVeiculoPorIdUseCase(
            VeiculoRepositoryPort repositoryPort
    ) {
        return new BuscarVeiculoPorIdService(repositoryPort);
    }

    @Bean
    public RemoverVeiculoUseCase removerVeiculoUseCase(
            VeiculoRepositoryPort repositoryPort
    ) {
        return new RemoverVeiculoService(repositoryPort);
    }

}
