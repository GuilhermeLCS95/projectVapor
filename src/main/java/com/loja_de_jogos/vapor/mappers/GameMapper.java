package com.loja_de_jogos.vapor.mappers;

import com.loja_de_jogos.vapor.dtos.GameRequestDTO;
import com.loja_de_jogos.vapor.dtos.GameResponseDTO;
import com.loja_de_jogos.vapor.models.Game;

public class GameMapper {
    public static Game toEntity(GameRequestDTO dto){
        return new Game(
                dto.id(),
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

    public static void updateEntity(Game game, GameRequestDTO dto){
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

    public static GameResponseDTO toDTO(Game game){
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
