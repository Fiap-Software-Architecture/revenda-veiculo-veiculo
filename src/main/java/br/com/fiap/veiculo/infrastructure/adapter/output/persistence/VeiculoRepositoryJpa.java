package br.com.fiap.veiculo.infrastructure.adapter.output.persistence;

import br.com.fiap.veiculo.domain.model.StatusVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VeiculoRepositoryJpa extends JpaRepository<VeiculoJpaEntity, UUID> {

    boolean existsByPlaca(String placa);

    boolean existsByPlacaAndIdNot(String placa, UUID id);

    List<VeiculoJpaEntity> findAllByOrderByPrecoAsc();

    List<VeiculoJpaEntity> findByStatusOrderByPrecoAsc(StatusVeiculo status);

}

