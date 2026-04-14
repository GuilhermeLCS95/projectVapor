package com.loja_de_jogos.vapor.repositories;

import com.loja_de_jogos.vapor.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
