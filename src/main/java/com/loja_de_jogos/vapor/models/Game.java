package com.loja_de_jogos.vapor.models;

import com.loja_de_jogos.vapor.enums.AgeRating;
import com.loja_de_jogos.vapor.enums.Genre;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private String description;
    private Double price;
    private List<Genre> genre;
    private Timestamp releaseDate;
    private Double userRating;
    private String developer;
    private String publisher;
    private Boolean hasDiscount;
    private AgeRating ageRating;

    public Game(String name, String image, String description, Double price, List<Genre> genre,
                Timestamp releaseDate, Double userRating, String developer, String publisher, Boolean hasDiscount,
                AgeRating ageRating) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.developer = developer;
        this.publisher = publisher;
        this.hasDiscount = hasDiscount;
        this.ageRating = ageRating;
    }
}


