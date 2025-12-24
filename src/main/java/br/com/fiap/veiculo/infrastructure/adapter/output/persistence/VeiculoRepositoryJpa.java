package br.com.fiap.veiculo.infrastructure.adapter.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VeiculoRepositoryJpa extends JpaRepository<VeiculoJpaEntity, UUID> {

}

