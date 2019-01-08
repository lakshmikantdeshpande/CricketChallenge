package com.ldeshpande.simplecodingtask.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Score {
  DOT(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), OUT(-1);

  private Integer score;
}
