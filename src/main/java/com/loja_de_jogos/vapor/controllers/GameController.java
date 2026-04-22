package com.loja_de_jogos.vapor.controllers;

import com.loja_de_jogos.vapor.dtos.gameDTO.GameCreationRequestDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameResponseDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameUpdateRequestDTO;
import com.loja_de_jogos.vapor.models.Game;
import com.loja_de_jogos.vapor.services.GameService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*")
public class GameController {
    private final GameService gameService;

    public GameController(GameService service) {
        this.gameService = service;
    }

    @PostMapping("/create")
    public GameResponseDTO create(@Valid @RequestBody GameCreationRequestDTO gameCreationRequest){
       return gameService.addGame(gameCreationRequest);
    }

    @GetMapping("/get/{id}")
    public Optional<GameResponseDTO> getById(@PathVariable Long id){
        return gameService.getGameById(id);
    }

    @GetMapping("/get")
    public List<GameResponseDTO> getAll(){
        return gameService.getAllGames();
    }

    @PutMapping("/update/{id}")
    public GameResponseDTO update(@Valid @PathVariable Long id, @RequestBody GameUpdateRequestDTO gameUpdateRequest){
        return gameService.updateGame(id,gameUpdateRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        gameService.deleteGame(id);
    }
}
