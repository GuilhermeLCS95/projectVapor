package com.loja_de_jogos.vapor.mappers;

import com.loja_de_jogos.vapor.dtos.gameDTO.GameCreationRequestDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameResponseDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameUpdateRequestDTO;
import com.loja_de_jogos.vapor.models.Game;

public class GameMapper {
    public static Game gameDtoToEntity(GameCreationRequestDTO gameCreationRequest){
        return new Game(
                gameCreationRequest.name(),
                gameCreationRequest.image(),
                gameCreationRequest.description(),
                gameCreationRequest.price(),
                gameCreationRequest.genre(),
                gameCreationRequest.releaseDate(),
                gameCreationRequest.userRating(),
                gameCreationRequest.developer(),
                gameCreationRequest.publisher(),
                gameCreationRequest.hasDiscount(),
                gameCreationRequest.ageRating()
        );

    }

    public static void gameUpdateDtoToEntity(Game game, GameUpdateRequestDTO gameUpdateRequest){
        game.setName(gameUpdateRequest.name());
        game.setImage(gameUpdateRequest.image());
        game.setDescription(gameUpdateRequest.description());
        game.setPrice(gameUpdateRequest.price());
        game.setGenre(gameUpdateRequest.genre());
        game.setReleaseDate(gameUpdateRequest.releaseDate());
        game.setUserRating(gameUpdateRequest.userRating());
        game.setDeveloper(gameUpdateRequest.developer());
        game.setPublisher(gameUpdateRequest.publisher());
        game.setHasDiscount(gameUpdateRequest.hasDiscount());
        game.setAgeRating(gameUpdateRequest.ageRating());
    }

    public static GameResponseDTO gameEntityToDTO(Game game){
        return new GameResponseDTO(
                game.getName(),
                game.getImage(),
                game.getDescription(),
                game.getPrice(),
                game.getGenre(),
                game.getReleaseDate(),
                game.getUserRating(),
                game.getDeveloper(),
                game.getPublisher(),
                game.getHasDiscount(),
                game.getAgeRating()
        );
    }

}
