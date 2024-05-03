package ch.scs.jumpstart.pattern.examples.checkers.util;

import ch.scs.jumpstart.pattern.examples.checkers.dom.Player;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;
import java.util.Map;
import java.util.Random;

public class CoinTosser {
  private final PointsCalculator pointsCalculator;
  private final Random random;

  public CoinTosser(PointsCalculator pointsCalculator) {
    this(pointsCalculator, new Random());
  }

  CoinTosser(PointsCalculator pointsCalculator, Random random) {
    this.pointsCalculator = pointsCalculator;
    this.random = random;
  }

  public Result toss(Board board, Player player) {
    Map<Player, Integer> playerPointsMap = pointsCalculator.calculatePoints(board);
    int totalPoints =
        playerPointsMap.get(Player.PLAYER_RED) + playerPointsMap.get(Player.PLAYER_WHITE);
    float winChance = 1 - playerPointsMap.get(player).floatValue() / totalPoints;
    return random.nextFloat() <= winChance ? Result.HEADS : Result.TAILS;
  }

  public enum Result {
    HEADS,
    TAILS
  }
}
