package com.loja_de_jogos.vapor.services;

import com.loja_de_jogos.vapor.enums.Genre;
import com.loja_de_jogos.vapor.models.Game;
import com.loja_de_jogos.vapor.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public Game addGame(Game game){
        return repository.save(game);
    }

    public Optional<Game> getGameById(Long id){
        return repository.findById(id);
    }

    public List<Game> getAllGames(){
        return repository.findAll();
    }

    public Game updateGame(Long id, Game updatedGame){
        return repository.findById(id)
            .map(game ->{
                game.setName(updatedGame.getName());
                game.setImage(updatedGame.getImage());
                game.setDescription(updatedGame.getDescription());
                game.setPrice(updatedGame.getPrice());
                game.setGenre(updatedGame.getGenre());

                return repository.save(game);
            })
            .orElseThrow(() -> new RuntimeException("Game not found."));
    }

    public void deleteGame(Long id){
        repository.deleteById(id);
    }

}
