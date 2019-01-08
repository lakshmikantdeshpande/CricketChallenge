package com.ldeshpande.simplecodingtask.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
  private String message;

  public ServiceException(String message) {
    super(message);
    this.message = message;
  }
}
