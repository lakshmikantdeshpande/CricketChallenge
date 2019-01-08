package com.ldeshpande.simplecodingtask.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Match {
  private int remainingBalls = 0;
  private List<Batsman> batsmen = new ArrayList<>();

  public boolean isOverComplete() {
    return remainingBalls % 6 == 0;
  }

  public boolean isMatchOver() {
    return remainingBalls == 0;
  }
}
