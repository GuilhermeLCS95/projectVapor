package com.loja_de_jogos.vapor.entities;

import com.loja_de_jogos.vapor.enums.AgeRating;
import com.loja_de_jogos.vapor.enums.Genre;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "games")
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(name = "normalized_id")
  private String normalizedId;

  @Column(nullable = false)
  private String image;

  @Column(nullable = false, length = 400)
  private String description;

  @Column(nullable = false)
  private Double price;

  @ElementCollection
  @CollectionTable(name = "game_genres", joinColumns = @JoinColumn(name = "game_id"))
  @Column(name = "genre", nullable = false)
  @Enumerated(EnumType.STRING)
  private List<Genre> genre;

  @Column(name = "release_date", nullable = false)
  private Timestamp releaseDate;

  @Column(name = "user_rating")
  private Double userRating;

  @Column(nullable = false)
  private String developer;

  @Column(nullable = false)
  private String publisher;

  @Column(name = "has_discount")
  private Boolean hasDiscount;

  @Column(name = "age_rating", nullable = false)
  @Enumerated(EnumType.STRING)
  private AgeRating ageRating;
}
