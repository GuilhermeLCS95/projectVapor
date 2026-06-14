CREATE TABLE games (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    normalized_id VARCHAR(255) NULL,
    image VARCHAR(255) NOT NULL,
    description VARCHAR(400) NOT NULL,
    price DOUBLE NOT NULL,
    release_date DATETIME(6) NOT NULL,
    user_rating DOUBLE NULL,
    developer VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    has_discount BOOLEAN NULL,
    age_rating VARCHAR(32) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE game_genres (
    game_id BIGINT NOT NULL,
    genre VARCHAR(32) NOT NULL,
    CONSTRAINT fk_game_genres_games
        FOREIGN KEY (game_id)
        REFERENCES games (id)
        ON DELETE CASCADE
);
