package com.ldeshpande.simplecodingtask.model;

import com.ldeshpande.simplecodingtask.enums.Score;
import com.ldeshpande.simplecodingtask.exception.MissingProbabilityException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(of = "name")
@ToString
public class Batsman {
  private String name;
  private Map<Score, Integer> probabilityMap = new HashMap<>();

  public Integer getProbability(Score score) {
    Integer probability = probabilityMap.get(score);
    if (probability == null) {
      throw new MissingProbabilityException("Fatal exception: Probability of this score is missing");
    }
    return probability;
  }
}
