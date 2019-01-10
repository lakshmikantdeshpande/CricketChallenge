package com.ldeshpande.simplecodingtask.utils;

import com.google.common.io.Resources;
import com.ldeshpande.simplecodingtask.enums.Score;
import com.ldeshpande.simplecodingtask.exception.ParsingException;
import com.ldeshpande.simplecodingtask.exception.ServiceException;
import com.ldeshpande.simplecodingtask.model.Batsman;
import com.ldeshpande.simplecodingtask.model.Match;
import com.opencsv.CSVReader;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@UtilityClass
@Slf4j
public class ParserUtil {
  private static final String PROBABILITIES_INPUT = "probabilities.txt";

  /*
    This method reads player score probabilities from input file
   */
  public static Match parseMatchConfiguration(int remainingBalls, int scoreTarget) {
    Match match = new Match();
    match.setRemainingBalls(remainingBalls);
    match.setScoreTarget(scoreTarget);
    Queue<Batsman> batsmen = readBatsmensConfiguration();
    match.setBatsmen(batsmen);
    return match;
  }

  private Queue<Batsman> readBatsmensConfiguration() {
    Queue<Batsman> batsmen = new LinkedList<>();
    final String filePath = Resources.getResource(PROBABILITIES_INPUT).getPath();
    try (FileReader fileReader = new FileReader(filePath);
         CSVReader csvReader = new CSVReader(fileReader)) {
      boolean isHeader = true;
      String[] record;
      while ((record = csvReader.readNext()) != null) {
        if (isHeader) {
          isHeader = false;
        } else {
          Batsman batsman = new Batsman();
          batsman.setName(record[0]);
          Map<Score, Integer> probabilityMap = new EnumMap<>(Score.class);
          int[] index = {1};
          String[] finalRecord = record;
          Score.getValues().forEach(score -> {
            String token = finalRecord[index[0]++].trim();
            Integer probability = Integer.parseInt(token.substring(0, token.length() - 1));
            probabilityMap.put(score, probability);
          });
          batsman.setProbabilityMap(probabilityMap);
          batsmen.add(batsman);
        }
      }
    } catch (IOException ioexception) {
      log.error("Failed to open {}", PROBABILITIES_INPUT, ioexception);
      throw new ServiceException(String.format("Failed to open %s", PROBABILITIES_INPUT));
    } catch (Exception exception) {
      log.error("Failed to parse {}", PROBABILITIES_INPUT, exception);
      throw new ParsingException(String.format("Failed to parse %s", PROBABILITIES_INPUT));
    }
    return batsmen;
  }
}
