package com.loja_de_jogos.vapor.controllers;

import com.loja_de_jogos.vapor.models.Game;
import com.loja_de_jogos.vapor.services.GameService;
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
    public Game create(@RequestBody Game game){
       return service.addGame(game);
    }

    @GetMapping("/get/{id}")
    public Optional<Game> getById(@PathVariable Long id){
        return service.getGameById(id);
    }

    @GetMapping("/get")
    public List<Game> getAll(){
        return service.getAllGames();
    }

    @PutMapping("/update/{id}")
    public Game update(@PathVariable Long id, @RequestBody Game game){
        return service.updateGame(id,game);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        service.deleteGame(id);
    }
}
