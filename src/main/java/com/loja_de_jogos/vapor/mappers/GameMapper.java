package com.loja_de_jogos.vapor.mappers;

import com.loja_de_jogos.vapor.dtos.gameDTO.GameCreationRequestDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameResponseDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameUpdateRequestDTO;
import com.loja_de_jogos.vapor.entities.Game;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GameMapper {
    Game gameCreationDtoToEntity(GameCreationRequestDTO dto);
    GameResponseDTO gameEntityToResponseDTO(Game game);
    void gameUpdateDtoToEntity(GameUpdateRequestDTO dto, @MappingTarget Game game);
}
