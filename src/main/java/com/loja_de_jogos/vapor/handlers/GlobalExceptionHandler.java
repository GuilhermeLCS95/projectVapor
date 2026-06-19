package com.loja_de_jogos.vapor.handlers;

import com.loja_de_jogos.vapor.dtos.error.ErrorResponse;
import com.loja_de_jogos.vapor.exceptions.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> handleBaseException(
      BaseException ex, HttpServletRequest request) {
    ErrorResponse response =
        new ErrorResponse(
            ex.getErrorCode(),
            ex.getMessage(),
            ex.getStatus().value(),
            Instant.now(),
            request.getRequestURI());

    return ResponseEntity.status(ex.getStatus()).body(response);
  }
}
