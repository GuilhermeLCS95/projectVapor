package com.loja_de_jogos.vapor.repositories;

import com.loja_de_jogos.vapor.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

    boolean existsByName(String name);

    boolean existsByNormalizedId(String normalizedId);

}
