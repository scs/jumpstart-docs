package ch.scs.jumpstart.pattern.examples.checkers.util;

import static ch.scs.jumpstart.pattern.examples.checkers.dom.Player.PLAYER_RED;
import static ch.scs.jumpstart.pattern.examples.checkers.util.CoinTosser.Result.HEADS;
import static ch.scs.jumpstart.pattern.examples.checkers.util.CoinTosser.Result.TAILS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ch.scs.jumpstart.pattern.examples.checkers.dom.Player;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoinTosserTest {

  private PointsCalculator pointsCalculator;
  private Random random;
  private Board board;

  @BeforeEach
  void setup() {
    pointsCalculator = mock(PointsCalculator.class);
    random = mock(RandomWrapper.class);
    board = mock(Board.class);
  }

  @Test
  void return_heads_and_tails_evenly_distributed() {
    when(pointsCalculator.calculatePoints(notNull()))
        .thenReturn(Map.of(PLAYER_RED, 1, Player.PLAYER_WHITE, 1));
    CoinTosser coinTosser = new CoinTosser(pointsCalculator);
    long count =
        IntStream.range(0, 100)
            .mapToObj(__ -> coinTosser.toss(board, PLAYER_RED))
            .filter(HEADS::equals)
            .count();
    // use assume because of the random element
    assumeTrue(count < 70);
    assumeTrue(count > 30);
  }

  @Test
  void use_even_chances_if_points_are_evenly_distributed() {
    when(pointsCalculator.calculatePoints(notNull()))
        .thenReturn(Map.of(PLAYER_RED, 1, Player.PLAYER_WHITE, 1));
    CoinTosser coinTosser = new CoinTosser(pointsCalculator, random);

    when(random.nextFloat()).thenReturn(0.5f);
    assertThat(coinTosser.toss(board, PLAYER_RED)).isEqualTo(HEADS);

    when(random.nextFloat()).thenReturn(0.51f);
    assertThat(coinTosser.toss(board, PLAYER_RED)).isEqualTo(TAILS);
  }

  @Test
  void increase_the_chance_for_the_weaker_player() {
    when(pointsCalculator.calculatePoints(notNull()))
        .thenReturn(Map.of(PLAYER_RED, 1, Player.PLAYER_WHITE, 2));
    CoinTosser coinTosser = new CoinTosser(pointsCalculator, random);

    when(random.nextFloat()).thenReturn(0.6666f);
    assertThat(coinTosser.toss(board, PLAYER_RED)).isEqualTo(HEADS);

    when(random.nextFloat()).thenReturn(0.66667f);
    assertThat(coinTosser.toss(board, PLAYER_RED)).isEqualTo(TAILS);
  }

  @Test
  void decrease_the_chance_for_the_stronger_player() {
    when(pointsCalculator.calculatePoints(notNull()))
        .thenReturn(Map.of(PLAYER_RED, 2, Player.PLAYER_WHITE, 1));
    CoinTosser coinTosser = new CoinTosser(pointsCalculator, random);

    when(random.nextFloat()).thenReturn(0.3333f);
    assertThat(coinTosser.toss(board, PLAYER_RED)).isEqualTo(HEADS);

    when(random.nextFloat()).thenReturn(0.33334f);
    assertThat(coinTosser.toss(board, PLAYER_RED)).isEqualTo(TAILS);
  }

  private static class RandomWrapper extends Random {}
}
