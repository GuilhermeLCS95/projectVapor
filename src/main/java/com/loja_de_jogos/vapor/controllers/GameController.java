package com.loja_de_jogos.vapor.controllers;

import com.loja_de_jogos.vapor.dtos.gameDTO.GameCreationRequestDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameResponseDTO;
import com.loja_de_jogos.vapor.dtos.gameDTO.GameUpdateRequestDTO;
import com.loja_de_jogos.vapor.services.GameService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*")
public class GameController {
    private final GameService gameService;

    public GameController(GameService service) {
        this.gameService = service;
    }

    @PostMapping
    public GameResponseDTO create(@Valid @RequestBody GameCreationRequestDTO gameCreationRequest){
       return gameService.addGame(gameCreationRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @GetMapping
    public List<GameResponseDTO> getAll(){
        return gameService.getAllGames();
    }

    @PutMapping("/{id}")
    public GameResponseDTO update(@PathVariable Long id, @Valid @RequestBody GameUpdateRequestDTO gameUpdateRequest){
        return gameService.updateGame(id,gameUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        gameService.deleteGame(id);
    }
}
