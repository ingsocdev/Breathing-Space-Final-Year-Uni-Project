package com.tom.breathingspace.spring;

public class BreathingSpaceException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private String exceptionMessage;

  public BreathingSpaceException(final String message) {
    this.exceptionMessage = message;
  }

  public String getExceptionMessage() {
    return this.exceptionMessage;
  }

  public void setExceptionMessage(final String message) {
    this.exceptionMessage = message;
  }
}
