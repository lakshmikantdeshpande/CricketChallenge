package com.ldeshpande.simplecodingtask.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum Score {
  DOT(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), OUT(-1);

  private Integer score;

  public static List<Score> getValues() {
    return Arrays.asList(DOT, ONE, TWO, THREE, FOUR, FIVE, SIX, OUT);
  }
}
