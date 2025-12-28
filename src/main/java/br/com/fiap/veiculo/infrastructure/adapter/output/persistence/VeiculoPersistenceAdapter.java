package br.com.fiap.veiculo.infrastructure.adapter.output.persistence;

import br.com.fiap.veiculo.application.port.output.VeiculoRepositoryPort;
import br.com.fiap.veiculo.domain.model.Placa;
import br.com.fiap.veiculo.domain.model.Veiculo;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class VeiculoPersistenceAdapter implements VeiculoRepositoryPort {

    private final VeiculoRepositoryJpa repository;

    public VeiculoPersistenceAdapter(VeiculoRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public boolean existePorPlaca(Placa placa) {
        return repository.existsByPlaca(placa.getValue());
    }

    @Override
    public boolean existePorPlacaEIdDiferente(Placa placa, UUID id) {
        return repository.existsByPlacaAndIdNot(placa.getValue(), id);
    }

    @Override
    public Optional<Veiculo> buscarPorId(UUID id) {
        return repository.findById(id).map(VeiculoJpaEntity::toDomain);
    }

    @Override
    public Veiculo salvar(Veiculo veiculo) {
        VeiculoJpaEntity entity = VeiculoJpaEntity.fromDomain(veiculo);
        VeiculoJpaEntity entitySalva = repository.save(entity);
        return entitySalva.toDomain();
    }
    
}

