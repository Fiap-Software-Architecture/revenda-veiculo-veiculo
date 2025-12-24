package br.com.fiap.veiculo.infrastructure.adapter.output.persistence;

import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.domain.model.Veiculo;
import org.springframework.stereotype.Component;

@Component
public class VeiculoPersistenceAdapter implements VeiculoRepositoryPort {

    private final VeiculoRepositoryJpa repository;

    public VeiculoPersistenceAdapter(VeiculoRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public Veiculo salvar(Veiculo veiculo) {
        VeiculoJpaEntity entity = VeiculoJpaEntity.fromDomain(veiculo);
        VeiculoJpaEntity entitySalva = repository.save(entity);
        return entitySalva.toDomain();
    }
}

