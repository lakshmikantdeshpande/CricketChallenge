package com.ldeshpande.simplecodingtask.exception;

import lombok.Getter;

@Getter
public class ParsingException extends RuntimeException {
  private String message;

  public ParsingException(String message) {
    super(message);
    this.message = message;
  }
}
