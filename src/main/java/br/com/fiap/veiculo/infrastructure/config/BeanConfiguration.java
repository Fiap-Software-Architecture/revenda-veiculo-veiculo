package br.com.fiap.veiculo.infrastructure.config;

import br.com.fiap.veiculo.application.port.input.CadastrarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.application.service.CadastrarVeiculoService;
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
}
