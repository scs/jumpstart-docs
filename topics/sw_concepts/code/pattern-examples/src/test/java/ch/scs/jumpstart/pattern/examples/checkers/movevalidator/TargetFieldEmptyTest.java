package ch.scs.jumpstart.pattern.examples.checkers.movevalidator;

import static ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates.Column.*;
import static ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates.Row.*;
import static ch.scs.jumpstart.pattern.examples.checkers.dom.JumpGambleResult.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Move;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Piece;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Player;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TargetFieldEmptyTest {
  public static final Piece PIECE = new Piece(Player.PLAYER_WHITE, false);

  private TargetFieldEmpty targetFieldEmpty;
  private Board board;

  @BeforeEach
  void setup() {
    targetFieldEmpty = new TargetFieldEmpty();
    board = mock(Board.class);
  }

  @Test
  void return_true_if_target_field_empty() {
    when(board.getPieceAt(notNull())).thenReturn(Optional.empty());

    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, A));
    assertThat(targetFieldEmpty.validate(move, board)).isTrue();
  }

  @Test
  void return_false_if_target_field_not_empty() {
    when(board.getPieceAt(notNull())).thenReturn(Optional.of(PIECE));

    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, A));
    assertThat(targetFieldEmpty.validate(move, board)).isFalse();
  }
}
