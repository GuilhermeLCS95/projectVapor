package com.loja_de_jogos.vapor.models;

import com.loja_de_jogos.vapor.enums.AgeRating;
import com.loja_de_jogos.vapor.enums.Genre;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
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
}
