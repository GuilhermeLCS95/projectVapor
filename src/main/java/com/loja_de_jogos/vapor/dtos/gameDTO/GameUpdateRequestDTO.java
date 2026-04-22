package com.loja_de_jogos.vapor.dtos.gameDTO;

import com.loja_de_jogos.vapor.enums.AgeRating;
import com.loja_de_jogos.vapor.enums.Genre;
import jakarta.validation.constraints.*;

import java.sql.Timestamp;
import java.util.List;

public record GameUpdateRequestDTO(
        @NotBlank(message = "Name cannot be null.")
        String name,

        @NotBlank(message = "Image cannot be blank.")
        String image,

        @Size(min = 20, max = 400, message = "It must be between 20 and 400 characters.")
        @NotBlank(message = "Description cannot be blank.")
        String description,

        @NotNull(message = "Price is required.")
        @PositiveOrZero(message = "Price cannot be negative.")
        Double price,

        @NotEmpty(message = "Genre cannot be empty.")
        List<Genre> genre,

        @NotNull(message = "Release date cannot be null.")
        Timestamp releaseDate,

        @PositiveOrZero
        Double userRating,

        @NotBlank(message = "Developer date cannot be null.")
        String developer,

        @NotBlank(message = "Publisher date cannot be null.")
        String publisher,

        Boolean hasDiscount,

        @NotNull(message = "Age rating cannot be null.")
        AgeRating ageRating) {
}
