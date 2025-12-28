package br.com.fiap.veiculo.infrastructure.config;

import br.com.fiap.veiculo.application.port.input.AtualizarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.input.CadastrarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.input.ListarVeiculosUseCase;
import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.domain.factory.VeiculoFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class BeanConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(BeanConfiguration.class)
            .withBean(VeiculoRepositoryPort.class, () -> mock(VeiculoRepositoryPort.class))
            .withBean(VeiculoFactory.class, () -> mock(VeiculoFactory.class));

    @Test
    void deveCriarBeanCadastrarVeiculoUseCase() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(CadastrarVeiculoUseCase.class);
            assertThat(context.getBean(CadastrarVeiculoUseCase.class)).isNotNull();
        });
    }

    @Test
    void deveCriarBeanAtualizarVeiculoUseCase() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(AtualizarVeiculoUseCase.class);
            assertThat(context.getBean(AtualizarVeiculoUseCase.class)).isNotNull();
        });
    }

    @Test
    void deveCriarBeanListarVeiculosUseCase() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(ListarVeiculosUseCase.class);
            assertThat(context.getBean(ListarVeiculosUseCase.class)).isNotNull();
        });
    }
}
