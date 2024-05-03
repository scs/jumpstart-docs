package ch.scs.jumpstart.pattern.examples.checkers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates;
import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates.Column;
import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates.Row;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Piece;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Player;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;
import ch.scs.jumpstart.pattern.examples.checkers.movevalidator.MoveValidator;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WinConditionTest {

  private static final Piece WHITE_PAWN = new Piece(Player.PLAYER_WHITE, false);
  private static final Piece RED_PAWN = new Piece(Player.PLAYER_RED, false);
  private MoveValidator moveValidator1;
  private MoveValidator moveValidator2;
  private WinCondition winCondition;
  private Board board;

  @BeforeEach
  void setup() {
    moveValidator1 = mock(MoveValidator.class);
    moveValidator2 = mock(MoveValidator.class);
    winCondition = new WinCondition(List.of(moveValidator1, moveValidator2));

    board = mock(Board.class);
  }

  @Test
  void player_has_won_if_other_player_has_no_pieces() {
    when(board.getPieceAt(notNull())).thenReturn(Optional.empty());
    when(board.getPieceAt(new BoardCoordinates(Row.ROW_4, Column.E)))
        .thenReturn(Optional.of(RED_PAWN));

    assertThat(winCondition.hasPlayerWon(Player.PLAYER_RED, board)).isTrue();
  }

  @Test
  void player_has_not_won_if_other_player_can_move_a_piece() {
    when(board.getPieceAt(new BoardCoordinates(Row.ROW_4, Column.E)))
        .thenReturn(Optional.of(WHITE_PAWN));
    when(moveValidator1.validate(notNull(), notNull())).thenReturn(true);
    when(moveValidator2.validate(notNull(), notNull())).thenReturn(true);

    assertThat(winCondition.hasPlayerWon(Player.PLAYER_RED, board)).isFalse();
  }

  @Test
  void player_has_won_if_other_player_has_a_piece_but_cannot_move_it() {
    when(board.getPieceAt(new BoardCoordinates(Row.ROW_4, Column.E)))
        .thenReturn(Optional.of(WHITE_PAWN));
    when(moveValidator1.validate(notNull(), notNull())).thenReturn(false);
    when(moveValidator2.validate(notNull(), notNull())).thenReturn(true);

    assertThat(winCondition.hasPlayerWon(Player.PLAYER_RED, board)).isTrue();
  }
}
