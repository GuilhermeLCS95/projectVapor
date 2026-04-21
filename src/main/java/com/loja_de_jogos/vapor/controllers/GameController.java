package com.loja_de_jogos.vapor.controllers;

import com.loja_de_jogos.vapor.dtos.GameRequestDTO;
import com.loja_de_jogos.vapor.dtos.GameResponseDTO;
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
    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Game create(@Valid @RequestBody GameRequestDTO dto){
       return service.addGame(dto);
    }

    @GetMapping("/get/{id}")
    public Optional<GameResponseDTO> getById(@PathVariable Long id){
        return service.getGameById(id);
    }

    @GetMapping("/get")
    public List<GameResponseDTO> getAll(){
        return service.getAllGames();
    }

    @PutMapping("/update/{id}")
    public Game update(@Valid @PathVariable Long id, @RequestBody GameRequestDTO dto){
        return service.updateGame(id,dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        service.deleteGame(id);
    }
}
