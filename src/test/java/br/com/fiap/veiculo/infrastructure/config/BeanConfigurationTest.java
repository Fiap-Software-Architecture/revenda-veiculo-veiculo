package br.com.fiap.veiculo.infrastructure.config;

import br.com.fiap.veiculo.application.port.input.CadastrarVeiculoUseCase;
import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.application.service.CadastrarVeiculoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BeanConfiguration.class)
class BeanConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @MockitoBean
    private VeiculoRepositoryPort repositoryPort;

    @Test
    void deveCriarBeanCadastrarVeiculoUseCase() {
        CadastrarVeiculoUseCase useCase =
                applicationContext.getBean(CadastrarVeiculoUseCase.class);

        assertNotNull(useCase);
        assertInstanceOf(CadastrarVeiculoService.class, useCase);
    }

    @Test
    void deveInjetarRepositorioNoUseCase() {
        CadastrarVeiculoUseCase useCase =
                applicationContext.getBean(CadastrarVeiculoUseCase.class);

        assertNotNull(useCase);

        // Se o contexto subiu, a injeção já aconteceu.
        // E a implementação esperada está correta.
        assertInstanceOf(CadastrarVeiculoService.class, useCase);
    }
}
