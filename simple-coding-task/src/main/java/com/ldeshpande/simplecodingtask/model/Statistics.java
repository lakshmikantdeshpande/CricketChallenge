package com.ldeshpande.simplecodingtask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Statistics {
  private String batsmanName;
  private int ballsFaced;
  private int score;
  private boolean out = false;

  @Override
  public String toString() {
    return String.format("%s - %s (%d balls)", batsmanName, ballsFaced + (out ? "" : "*"), score);
  }
}
