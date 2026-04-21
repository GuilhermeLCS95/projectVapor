package com.loja_de_jogos.vapor.services;

import com.loja_de_jogos.vapor.dtos.GameRequestDTO;
import com.loja_de_jogos.vapor.dtos.GameResponseDTO;
import com.loja_de_jogos.vapor.enums.AgeRating;
import com.loja_de_jogos.vapor.enums.Genre;
import com.loja_de_jogos.vapor.mappers.GameMapper;
import com.loja_de_jogos.vapor.models.Game;
import com.loja_de_jogos.vapor.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    @Autowired
    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public Game addGame(GameRequestDTO dto){
        Game game = GameMapper.toEntity(dto);
        return repository.save(game);
    }

    public Optional<GameResponseDTO> getGameById(Long id){
        return repository.findById(id).map(GameMapper::toDTO);
    }

    public List<GameResponseDTO> getAllGames(){
        return repository.findAll().stream().map(GameMapper::toDTO).toList();
    }

    public Game updateGame(Long id, GameRequestDTO dto){
        return repository.findById(id)
            .map(game ->{
                GameMapper.updateEntity(game,dto);

                return repository.save(game);
            })
            .orElseThrow(() -> new RuntimeException("Game not found."));
    }

    public void deleteGame(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Game not found.");
        }
        
        repository.deleteById(id);
    }

}
