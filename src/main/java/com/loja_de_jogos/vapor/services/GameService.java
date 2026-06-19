package com.loja_de_jogos.vapor.services;

import com.loja_de_jogos.vapor.dtos.gameDTO.GameCreationRequestDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameResponseDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameUpdateRequestDTO;
import com.loja_de_jogos.vapor.enums.ErrorMessage;
import com.loja_de_jogos.vapor.exceptions.BaseException;
import com.loja_de_jogos.vapor.helper.GameNormalizer;
import com.loja_de_jogos.vapor.mappers.GameMapper;
import com.loja_de_jogos.vapor.entities.Game;
import com.loja_de_jogos.vapor.repositories.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public GameService(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    @Transactional
    public GameResponseDTO addGame(GameCreationRequestDTO gameCreationRequest){

        String normalizedId = GameNormalizer.generateNormalizedId(
                gameCreationRequest.name(),
                gameCreationRequest.publisher()
        );

        if (gameRepository.existsByNormalizedId(normalizedId)) {
            throw new BaseException(ErrorMessage.GAME_ALREADY_EXISTS);
        }

        Game game = gameMapper.gameCreationDtoToEntity(gameCreationRequest);

        game.setNormalizedId(normalizedId);

        Game savedGame = gameRepository.save(game);

        return gameMapper.gameEntityToResponseDTO(savedGame);
    }

    public GameResponseDTO getGameById(Long id){
        return gameRepository.findById(id).map(gameMapper::gameEntityToResponseDTO)
                .orElseThrow(() -> new BaseException(ErrorMessage.GAME_NOT_FOUND));
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
            .orElseThrow(() -> new BaseException(ErrorMessage.GAME_NOT_FOUND));
    }

    public void deleteGame(Long id){
        if(!gameRepository.existsById(id)){
            throw new BaseException(ErrorMessage.GAME_NOT_FOUND);
        }
        
        gameRepository.deleteById(id);
    }

}
