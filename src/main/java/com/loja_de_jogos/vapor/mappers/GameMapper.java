package com.loja_de_jogos.vapor.mappers;

import com.loja_de_jogos.vapor.dtos.gameDTO.GameCreationRequestDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameResponseDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameUpdateRequestDTO;
import com.loja_de_jogos.vapor.models.Game;

public class GameMapper {
    public static Game gameDtoToEntity(GameCreationRequestDTO dto){
        return new Game(
                dto.name(),
                dto.image(),
                dto.description(),
                dto.price(),
                dto.genre(),
                dto.releaseDate(),
                dto.userRating(),
                dto.developer(),
                dto.publisher(),
                dto.hasDiscount(),
                dto.ageRating()
        );

    }

    public static void gameUpdateDtoToEntity(Game game, GameUpdateRequestDTO dto){
        game.setName(dto.name());
        game.setImage(dto.image());
        game.setDescription(dto.description());
        game.setPrice(dto.price());
        game.setGenre(dto.genre());
        game.setReleaseDate(dto.releaseDate());
        game.setUserRating(dto.userRating());
        game.setDeveloper(dto.developer());
        game.setPublisher(dto.publisher());
        game.setHasDiscount(dto.hasDiscount());
        game.setAgeRating(dto.ageRating());
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
