package com.loja_de_jogos.vapor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    GAME_NOT_FOUND("GAME_001", "Jogo não encontrado", HttpStatus.NOT_FOUND),
    GAME_ALREADY_EXISTS("GAME_002", "Jogo já existe.", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
