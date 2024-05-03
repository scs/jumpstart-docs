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

class NoOtherMoveToJumpPossibleTest {
  private static final Piece WHITE_PAWN = new Piece(Player.PLAYER_WHITE, false);
  private static final Piece RED_PAWN = new Piece(Player.PLAYER_RED, false);
  private static final Piece RED_KING = new Piece(Player.PLAYER_RED, true);

  private NoOtherMoveToJumpPossible noOtherMoveToJumpPossible;
  private Board board;

  @BeforeEach
  void setup() {
    noOtherMoveToJumpPossible =
        new NoOtherMoveToJumpPossible(
            new MoveIsForwardIfNotKing(), new OpponentPieceBetweenJump(), new TargetFieldEmpty());
    board = mock(Board.class);
  }

  @Test
  void return_true_if_no_other_pieces_exist() {
    when(board.getPieceAt(notNull())).thenReturn(Optional.empty());

    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, B));
    assertThat(noOtherMoveToJumpPossible.validate(move, board)).isTrue();
  }

  @Test
  void return_true_if_move_is_jump_move() {
    when(board.getPieceAt(notNull())).thenReturn(Optional.empty());

    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_3, C));
    assertThat(noOtherMoveToJumpPossible.validate(move, board)).isTrue();
  }

  @Test
  void does_not_crash_if_piece_is_at_edge() {
    when(board.getPieceAt(new BoardCoordinates(ROW_1, A))).thenReturn(Optional.of(WHITE_PAWN));
    when(board.getPieceAt(new BoardCoordinates(ROW_8, A))).thenReturn(Optional.of(WHITE_PAWN));
    when(board.getPieceAt(new BoardCoordinates(ROW_1, G))).thenReturn(Optional.of(WHITE_PAWN));
    when(board.getPieceAt(new BoardCoordinates(ROW_8, G))).thenReturn(Optional.of(WHITE_PAWN));

    Move move =
        Move.of(
            Player.PLAYER_WHITE, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, B));
    assertThat(noOtherMoveToJumpPossible.validate(move, board)).isTrue();
  }

  @Test
  void return_false_if_other_jump_move_possible_for_white() {
    when(board.getPieceAt(new BoardCoordinates(ROW_4, E))).thenReturn(Optional.of(WHITE_PAWN));
    when(board.getPieceAt(new BoardCoordinates(ROW_5, F))).thenReturn(Optional.of(RED_PAWN));
    when(board.getPieceAt(new BoardCoordinates(ROW_6, G))).thenReturn(Optional.empty());

    Move move =
        Move.of(
            Player.PLAYER_WHITE, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, B));
    assertThat(noOtherMoveToJumpPossible.validate(move, board)).isFalse();
  }

  @Test
  void return_false_if_other_jump_move_possible_for_red() {
    when(board.getPieceAt(new BoardCoordinates(ROW_6, G))).thenReturn(Optional.of(RED_PAWN));
    when(board.getPieceAt(new BoardCoordinates(ROW_5, F))).thenReturn(Optional.of(WHITE_PAWN));
    when(board.getPieceAt(new BoardCoordinates(ROW_4, E))).thenReturn(Optional.empty());

    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, B));
    assertThat(noOtherMoveToJumpPossible.validate(move, board)).isFalse();
  }

  @Test
  void return_false_if_other_jump_move_with_king_possible_for_red() {
    when(board.getPieceAt(new BoardCoordinates(ROW_4, E))).thenReturn(Optional.of(RED_KING));
    when(board.getPieceAt(new BoardCoordinates(ROW_5, F))).thenReturn(Optional.of(WHITE_PAWN));
    when(board.getPieceAt(new BoardCoordinates(ROW_6, G))).thenReturn(Optional.empty());

    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, B));
    assertThat(noOtherMoveToJumpPossible.validate(move, board)).isFalse();
  }
}
