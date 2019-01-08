package com.ldeshpande.simplecodingtask.utils;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
@Slf4j
public class ParserUtil {
  private static final String PROBABILITIES_INPUT = "probabilities.txt";

  /*
    This method reads player score probabilities from input file
   */
  public static Match parseMatchConfiguration(int remainingBalls) {
    Match match = new Match();
    match.setRemainingBalls(remainingBalls);
    List<Batsman> batsmen = readBatsmensConfiguration();
    match.setBatsmen(batsmen);
    return match;
  }

  private List<Batsman> readBatsmensConfiguration() {
    List<Batsman> batsmen = new ArrayList<>(4);
    try (FileReader fileReader = new FileReader(PROBABILITIES_INPUT);
         CSVReader csvReader = new CSVReader(fileReader)) {
      boolean isHeader = true;
      String[] record;
      while ((record = csvReader.readNext()) != null) {
        if (isHeader) {
          isHeader = false;
        } else {
          Batsman batsman = new Batsman();
          batsman.setName(record[0]);
          Map<Score, Integer> probabilityMap = new HashMap<>();
          int[] index = {1};
          Arrays.stream(Score.values()).forEach(score -> probabilityMap.put(score, index[0]++));
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
