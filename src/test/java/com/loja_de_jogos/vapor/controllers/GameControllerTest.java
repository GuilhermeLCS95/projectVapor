package com.loja_de_jogos.vapor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameCreationRequestDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameResponseDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameUpdateRequestDTO;
import com.loja_de_jogos.vapor.enums.AgeRating;
import com.loja_de_jogos.vapor.enums.ErrorMessage;
import com.loja_de_jogos.vapor.enums.Genre;
import com.loja_de_jogos.vapor.exceptions.BaseException;
import com.loja_de_jogos.vapor.handlers.GlobalExceptionHandler;
import com.loja_de_jogos.vapor.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private GameService gameService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper().findAndRegisterModules();
        mockMvc = standaloneSetup(new GameController(gameService))
                .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void createShouldReturnCreatedGame() throws Exception {
        GameResponseDTO response = response();
        when(gameService.addGame(any(GameCreationRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creationRequest())))
            .andExpect(status().isOk())

            .andExpect(jsonPath("$.name").value("Hollow Knight"))
            .andExpect(jsonPath("$.price").value(29.99))
            .andExpect(jsonPath("$.genre[0]").value("RPG"))
            .andExpect(jsonPath("$.ageRating").value("TEN"));

        ArgumentCaptor<GameCreationRequestDTO> captor = ArgumentCaptor.forClass(GameCreationRequestDTO.class);
        verify(gameService).addGame(captor.capture());
        assertThat(captor.getValue().name()).isEqualTo("Hollow Knight");
    }

    @Test
    void getByIdShouldReturnGameWhenFound() throws Exception {
        when(gameService.getGameById(1L)).thenReturn(response());

        mockMvc.perform(get("/games/{id}", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Hollow Knight"))
            .andExpect(jsonPath("$.developer").value("Team Cherry"));

        verify(gameService).getGameById(1L);
    }

    @Test
    void getByIdShouldReturnNotFoundWhenGameDoesNotExist() throws Exception {
        when(gameService.getGameById(99L)).thenThrow(new BaseException(ErrorMessage.GAME_NOT_FOUND));

        mockMvc.perform(get("/games/{id}", 99L))
            .andExpect(status().isNotFound());

        verify(gameService).getGameById(99L);
    }

    @Test
    void getAllShouldReturnGames() throws Exception {
        when(gameService.getAllGames()).thenReturn(List.of(response()));

        mockMvc.perform(get("/games"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].name").value("Hollow Knight"))
            .andExpect(jsonPath("$[0].publisher").value("Team Cherry"));

        verify(gameService).getAllGames();
    }

    @Test
    void updateShouldReturnUpdatedGame() throws Exception {
        GameResponseDTO response = new GameResponseDTO(
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

        when(gameService.updateGame(any(Long.class), any(GameUpdateRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/games/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Hollow Knight: Voidheart Edition"))
            .andExpect(jsonPath("$.hasDiscount").value(true));

        ArgumentCaptor<GameUpdateRequestDTO> captor = ArgumentCaptor.forClass(GameUpdateRequestDTO.class);
        verify(gameService).updateGame(eq(1L), captor.capture());
        assertThat(captor.getValue().price()).isEqualTo(34.99);
    }

    @Test
    void deleteShouldCallService() throws Exception {
        mockMvc.perform(delete("/games/{id}", 1L))
            .andExpect(status().isOk());

        verify(gameService).deleteGame(1L);
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

    private Timestamp releaseDate() {
        return Timestamp.from(Instant.parse("2017-02-24T00:00:00Z"));
    }
}
