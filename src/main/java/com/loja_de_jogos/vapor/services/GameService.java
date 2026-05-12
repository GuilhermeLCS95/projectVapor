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
    private final GameMapper gameMapper;

    public GameService(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    public GameResponseDTO addGame(GameCreationRequestDTO gameCreationRequest){
        Game game = gameMapper.gameCreationDtoToEntity(gameCreationRequest);
        Game savedGame = gameRepository.save(game);
        return gameMapper.gameEntityToResponseDTO(savedGame);
    }

    public Optional<GameResponseDTO> getGameById(Long id){
        return gameRepository.findById(id).map(gameMapper::gameEntityToResponseDTO);
    }

    public List<GameResponseDTO> getAllGames(){
        return gameRepository.findAll().stream().map(gameMapper::gameEntityToResponseDTO).toList();
    }

    public GameResponseDTO updateGame(Long id, GameUpdateRequestDTO gameUpdateRequest){
        return gameRepository.findById(id)
            .map(game ->{
                gameMapper.gameUpdateDtoToEntity(gameUpdateRequest, game);
                Game updatedGame = gameRepository.save(game);
                return gameMapper.gameEntityToResponseDTO(updatedGame);
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
