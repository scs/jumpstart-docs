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

class StartPieceValidTest {
  static final Piece WHITE_PAWN = new Piece(Player.PLAYER_WHITE, false);
  static final Piece RED_PAWN = new Piece(Player.PLAYER_RED, false);

  private StartPieceValid startPieceValid;
  private Board board;

  @BeforeEach
  void setup() {
    startPieceValid = new StartPieceValid();
    board = mock(Board.class);
  }

  @Test
  void return_false_if_no_piece() {
    when(board.getPieceAt(notNull())).thenReturn(Optional.empty());

    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, B));

    assertThat(startPieceValid.validate(move, board)).isFalse();
  }

  @Test
  void return_true_if_piece_belongs_to_player_red() {
    when(board.getPieceAt(notNull())).thenReturn(Optional.of(RED_PAWN));

    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, B));

    assertThat(startPieceValid.validate(move, board)).isTrue();
  }

  @Test
  void return_true_if_piece_belongs_to_player_white() {
    when(board.getPieceAt(notNull())).thenReturn(Optional.of(WHITE_PAWN));

    Move move =
        Move.of(
            Player.PLAYER_WHITE, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, B));

    assertThat(startPieceValid.validate(move, board)).isTrue();
  }

  @Test
  void return_false_if_piece_belongs_to_opponent_player_white() {
    when(board.getPieceAt(notNull())).thenReturn(Optional.of(RED_PAWN));

    Move move =
        Move.of(
            Player.PLAYER_WHITE, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, B));

    assertThat(startPieceValid.validate(move, board)).isFalse();
  }

  @Test
  void return_false_if_piece_belongs_to_opponent_player_red() {
    when(board.getPieceAt(notNull())).thenReturn(Optional.of(WHITE_PAWN));

    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, B));

    assertThat(startPieceValid.validate(move, board)).isFalse();
  }
}
