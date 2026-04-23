package com.loja_de_jogos.vapor.services;

import com.loja_de_jogos.vapor.dtos.gameDTO.GameCreationRequestDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameResponseDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameUpdateRequestDTO;
import com.loja_de_jogos.vapor.mappers.GameMapper;
import com.loja_de_jogos.vapor.models.Game;
import com.loja_de_jogos.vapor.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository repository) {
        this.gameRepository = repository;
    }

    public GameResponseDTO addGame(GameCreationRequestDTO gameCreationRequest){
        Game game = GameMapper.gameDtoToEntity(gameCreationRequest);
        Game savedGame = gameRepository.save(game);
        return GameMapper.gameEntityToDTO(savedGame);
    }

    public Optional<GameResponseDTO> getGameById(Long id){
        return gameRepository.findById(id).map(GameMapper::gameEntityToDTO);
    }

    public List<GameResponseDTO> getAllGames(){
        return gameRepository.findAll().stream().map(GameMapper::gameEntityToDTO).toList();
    }

    public GameResponseDTO updateGame(Long id, GameUpdateRequestDTO gameUpdateRequest){
        return gameRepository.findById(id)
            .map(game ->{
                GameMapper.gameUpdateDtoToEntity(game,gameUpdateRequest);
                Game updatedGame = gameRepository.save(game);
                return GameMapper.gameEntityToDTO(updatedGame);
            })
            .orElseThrow(() -> new RuntimeException("Game not found."));
    }

    public void deleteGame(Long id){
        if(!gameRepository.existsById(id)){
            throw new RuntimeException("Game not found.");
        }
        
        gameRepository.deleteById(id);
    }

}
