package com.loja_de_jogos.vapor.services;

import com.loja_de_jogos.vapor.dtos.gameDTO.GameCreationRequestDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameResponseDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameUpdateRequestDTO;
import com.loja_de_jogos.vapor.enums.AgeRating;
import com.loja_de_jogos.vapor.enums.Genre;
import com.loja_de_jogos.vapor.mappers.GameMapper;
import com.loja_de_jogos.vapor.models.Game;
import com.loja_de_jogos.vapor.repositories.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private GameService gameService;

    @Test
    void addGameShouldSaveMappedEntityAndReturnResponse() {
        GameCreationRequestDTO request = creationRequest();
        Game gameToSave = gameWithoutId();
        Game savedGame = gameWithId(1L);
        GameResponseDTO response = response();

        when(gameMapper.gameCreationDtoToEntity(request)).thenReturn(gameToSave);
        when(gameRepository.save(gameToSave)).thenReturn(savedGame);
        when(gameMapper.gameEntityToResponseDTO(savedGame)).thenReturn(response);

        GameResponseDTO result = gameService.addGame(request);

        assertThat(result).isEqualTo(response);
        verify(gameMapper).gameCreationDtoToEntity(request);
        verify(gameRepository).save(gameToSave);
        verify(gameMapper).gameEntityToResponseDTO(gameToSave);
    }

    @Test
    void getGameByIdShouldReturnMappedGameWhenFound() {
        Game game = gameWithId(1L);
        GameResponseDTO response = response();

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(gameMapper.gameEntityToResponseDTO(game)).thenReturn(response);

        Optional<GameResponseDTO> result = gameService.getGameById(1L);

        assertThat(result).contains(response);
        verify(gameRepository).findById(1L);
        verify(gameMapper).gameEntityToResponseDTO(game);
    }

    @Test
    void getGameByIdShouldReturnEmptyWhenNotFound() {
        when(gameRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<GameResponseDTO> result = gameService.getGameById(99L);

        assertThat(result).isEmpty();
        verify(gameRepository).findById(99L);
        verifyNoInteractions(gameMapper);
    }

    @Test
    void getAllGamesShouldReturnMappedGames() {
        Game firstGame = gameWithId(1L);
        Game secondGame = gameWithId(2L);
        GameResponseDTO firstResponse = response();
        GameResponseDTO secondResponse = new GameResponseDTO(
            "Celeste",
            "https://example.com/celeste.jpg",
            "A precise platform game about climbing a mountain and overcoming personal challenges.",
            19.99,
            List.of(Genre.RPG),
            releaseDate(),
            9.0,
            "Maddy Makes Games",
            "Maddy Makes Games",
            false,
            AgeRating.TEN
        );

        when(gameRepository.findAll()).thenReturn(List.of(firstGame, secondGame));
        when(gameMapper.gameEntityToResponseDTO(firstGame)).thenReturn(firstResponse);
        when(gameMapper.gameEntityToResponseDTO(secondGame)).thenReturn(secondResponse);

        List<GameResponseDTO> result = gameService.getAllGames();

        assertThat(result).containsExactly(firstResponse, secondResponse);
        verify(gameRepository).findAll();
    }

    @Test
    void updateGameShouldUpdateExistingGameAndReturnResponse() {
        GameUpdateRequestDTO request = updateRequest();
        Game existingGame = gameWithId(1L);
        Game updatedGame = gameWithId(1L);
        GameResponseDTO response = response();

        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));
        when(gameRepository.save(existingGame)).thenReturn(updatedGame);
        when(gameMapper.gameEntityToResponseDTO(updatedGame)).thenReturn(response);

        GameResponseDTO result = gameService.updateGame(1L, request);

        assertThat(result).isEqualTo(response);
        verify(gameRepository).findById(1L);
        verify(gameMapper).gameUpdateDtoToEntity(request, existingGame);
        verify(gameRepository).save(existingGame);
        verify(gameMapper).gameEntityToResponseDTO(updatedGame);
    }

    @Test
    void updateGameShouldThrowWhenGameDoesNotExist() {
        GameUpdateRequestDTO request = updateRequest();

        when(gameRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gameService.updateGame(99L, request))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Game not found.");

        verify(gameRepository).findById(99L);
    }

    @Test
    void deleteGameShouldDeleteWhenGameExists() {
        when(gameRepository.existsById(1L)).thenReturn(true);

        gameService.deleteGame(1L);

        verify(gameRepository).existsById(1L);
        verify(gameRepository).deleteById(1L);
    }

    @Test
    void deleteGameShouldThrowWhenGameDoesNotExist() {
        when(gameRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> gameService.deleteGame(99L))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Game not found.");

        verify(gameRepository).existsById(99L);
    }

    private GameCreationRequestDTO creationRequest() {
        return new GameCreationRequestDTO(
            "Hollow Knight",
            "https://example.com/hollow-knight.jpg",
            "A challenging atmospheric action adventure through a vast interconnected world.",
            29.99,
            List.of(Genre.RPG),
            releaseDate(),
            9.5,
            "Team Cherry",
            "Team Cherry",
            false,
            AgeRating.TEN
        );
    }

    private GameUpdateRequestDTO updateRequest() {
        return new GameUpdateRequestDTO(
            "Hollow Knight: Voidheart Edition",
            "https://example.com/hollow-knight-voidheart.jpg",
            "An expanded edition of the atmospheric action adventure through Hallownest.",
            34.99,
            List.of(Genre.RPG),
            releaseDate(),
            9.7,
            "Team Cherry",
            "Team Cherry",
            true,
            AgeRating.TEN
        );
    }

    private GameResponseDTO response() {
        return new GameResponseDTO(
            "Hollow Knight",
            "https://example.com/hollow-knight.jpg",
            "A challenging atmospheric action adventure through a vast interconnected world.",
            29.99,
            List.of(Genre.RPG),
            releaseDate(),
            9.5,
            "Team Cherry",
            "Team Cherry",
            false,
            AgeRating.TEN
        );
    }

    private Game gameWithoutId() {
        return Game.builder()
            .name("Hollow Knight")
            .image("https://example.com/hollow-knight.jpg")
            .description("A challenging atmospheric action adventure through a vast interconnected world.")
            .price(29.99)
            .genre(List.of(Genre.RPG))
            .releaseDate(releaseDate())
            .userRating(9.5)
            .developer("Team Cherry")
            .publisher("Team Cherry")
            .hasDiscount(false)
            .ageRating(AgeRating.TEN)
            .build();
    }

    private Game gameWithId(Long id) {
        Game game = gameWithoutId();
        game.setId(id);
        return game;
    }

    private Timestamp releaseDate() {
        return Timestamp.from(Instant.parse("2017-02-24T00:00:00Z"));
    }
}
