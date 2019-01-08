package com.ldeshpande.simplecodingtask.exception;

import lombok.Getter;

@Getter
public class MissingProbabilityException extends RuntimeException {
  private String message;

  public MissingProbabilityException(String message) {
    super(message);
    this.message = message;
  }
}
