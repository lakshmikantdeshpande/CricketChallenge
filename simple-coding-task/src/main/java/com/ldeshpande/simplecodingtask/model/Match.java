package com.ldeshpande.simplecodingtask.model;

import com.ldeshpande.simplecodingtask.enums.Score;
import com.ldeshpande.simplecodingtask.exception.ServiceException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

@Getter
@Setter
public class Match {
  private int remainingBalls;
  private int scoreTarget;
  private Queue<Batsman> batsmen = new LinkedList<>();

  @Setter(AccessLevel.NONE)
  private boolean isMatchOver = false;
  @Setter(AccessLevel.NONE)
  private int currentOver = 0;
  @Setter(AccessLevel.NONE)
  private int currentBall = 1;
  @Setter(AccessLevel.NONE)
  private Map<Batsman, Statistics> statisticsMap = new HashMap<>();

  public boolean isOverComplete() {
    return remainingBalls % 6 == 0;
  }

  private boolean isMatchOver() {
    return remainingBalls == 0 || isMatchOver;
  }

  private void beginMatch() {
    if (batsmen == null || batsmen.size() < 2 || remainingBalls < 0) {
      throw new ServiceException("Invalid match");
    }
    System.out.printf("%d overs left. %d runs to win%n", remainingBalls / 6, scoreTarget);
  }

  public void play() {
    beginMatch();
    Batsman first = batsmen.poll();
    Batsman second = batsmen.poll();

    while (!isMatchOver()) {
      remainingBalls--;
      Score score = faceBall(first);

      switch (score) {
        case OUT:
          first = batsmen.poll();
          if (first == null) {
            isMatchOver = true;
          }
          break;

        case ONE:
        case THREE:
        case FIVE:
          Batsman temp = first;
          first = second;
          second = temp;
          break;

        default:
          break;
      }
      if (scoreTarget <= 0) {
        isMatchOver = true;
        System.out.printf("Bengaluru won by %d wicket and %d balls remaining%n", batsmen.size(), remainingBalls);
      } else if (isMatchOver()) {
        System.out.printf("Chennai won by %d runs%n", scoreTarget);
      }
    }
    printStatistics();
  }

  private void printStatistics() {
    System.out.println();
    statisticsMap.values().forEach(System.out::println);
  }

  private Score faceBall(Batsman batsman) {
    while (true) {
      int randomNumber = new Random().nextInt(100) + 1;
      List<Map.Entry<Score, Integer>> list = new ArrayList<>(batsman.getProbabilityMap().entrySet());
      Collections.shuffle(list);

      for (Map.Entry<Score, Integer> entry : list) {
        if (entry.getValue() < randomNumber) {
          updateStatistics(batsman, entry.getKey());
          return entry.getKey();
        }
      }
    }
  }

  private void updateStatistics(Batsman batsman, Score score) {
    Statistics statistics = statisticsMap.getOrDefault(batsman, new Statistics(batsman.getName(), 0, 0, false));
    if (score == Score.OUT) {
      statistics.setOut(true);
    } else {
      statistics.setScore(statistics.getScore() + score.getScore());
      scoreTarget -= score.getScore();
    }
    statistics.setBallsFaced(statistics.getBallsFaced() + 1);
    comment(batsman, score);
    statisticsMap.put(batsman, statistics);
  }

  private void comment(Batsman batsman, Score score) {
    boolean overComplete = false;
    currentBall++;
    if (currentBall == 7) {
      overComplete = true;
    }

    if (score == Score.OUT) {
      System.out.printf("%d.%d %s is out%n", currentOver, currentBall - 1, batsman.getName());
    } else {
      System.out.printf("%d.%d %s scores %d run%n", currentOver, currentBall - 1, batsman.getName(), score.getScore());
    }
    if (overComplete) {
      System.out.printf("%d overs left. %d runs to win%n", remainingBalls / 6, scoreTarget);
      currentBall = 1;
      currentOver++;
    }
  }
}
