package com.loja_de_jogos.vapor.exceptions;

import com.loja_de_jogos.vapor.enums.ErrorMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {
  private final String errorCode;
  private final HttpStatus status;

  public BaseException(ErrorMessage error) {
    super(error.getMessage());
    this.errorCode = error.getCode();
    this.status = error.getStatus();
  }
}
