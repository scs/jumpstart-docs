package ch.scs.jumpstart.pattern.examples.checkers.util;

import static ch.scs.jumpstart.pattern.examples.checkers.dom.Player.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

import ch.scs.jumpstart.pattern.examples.checkers.dom.Piece;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointsCalculatorTest {
  private PointsCalculator pointsCalculator;
  private Board board;

  @BeforeEach
  void setup() {
    pointsCalculator = new PointsCalculator();
    board = spy(new Board());
  }

  @Test
  void calculate_points_correctly_at_start() {
    assertThat(pointsCalculator.calculatePoints(board))
        .isEqualTo(Map.of(PLAYER_WHITE, 12, PLAYER_RED, 12));
  }

  @Test
  void calculate_points_if_no_pieces_are_on_board() {
    doReturn(Optional.empty()).when(board).getPieceAt(notNull());
    assertThat(pointsCalculator.calculatePoints(board))
        .isEqualTo(Map.of(PLAYER_WHITE, 0, PLAYER_RED, 0));
  }

  @Test
  void use_2_as_value_for_kings() {
    doReturn(Optional.of(new Piece(PLAYER_WHITE, true)), Optional.of(new Piece(PLAYER_RED, true)))
        .when(board)
        .getPieceAt(notNull());
    assertThat(pointsCalculator.calculatePoints(board))
        .isEqualTo(Map.of(PLAYER_WHITE, 2, PLAYER_RED, 126));
  }
}
