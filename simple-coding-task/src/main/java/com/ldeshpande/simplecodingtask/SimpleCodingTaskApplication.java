package com.ldeshpande.simplecodingtask;

import com.ldeshpande.simplecodingtask.model.Match;
import com.ldeshpande.simplecodingtask.utils.ParserUtil;

public class SimpleCodingTaskApplication {

  private static final Integer OVER = 6;

  public static void main(String[] args) {
    Match match = ParserUtil.parseMatchConfiguration(OVER * 4);
  }

}

