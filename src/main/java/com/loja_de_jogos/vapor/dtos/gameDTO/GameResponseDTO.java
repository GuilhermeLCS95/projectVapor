package com.loja_de_jogos.vapor.dtos.gameDTO;

import com.loja_de_jogos.vapor.enums.AgeRating;
import com.loja_de_jogos.vapor.enums.Genre;

import java.sql.Timestamp;
import java.util.List;

public record GameResponseDTO(String name, String image, String description, Double price,
                              List<Genre> genre, Timestamp releaseDate, Double userRating,
                              String developer, String publisher, Boolean hasDiscount, AgeRating ageRating) {
    }
