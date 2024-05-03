package ch.scs.jumpstart.pattern.examples.checkers.movevalidator;

import static ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates.Column.*;
import static ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates.Row.*;
import static ch.scs.jumpstart.pattern.examples.checkers.dom.JumpGambleResult.*;
import static org.assertj.core.api.Assertions.assertThat;

import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Move;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Player;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoveIsDiagonalTest {

  private MoveIsDiagonal moveIsDiagonal;
  private Board board;

  @BeforeEach
  void setup() {
    moveIsDiagonal = new MoveIsDiagonal();
    board = new Board();
  }

  @Test
  void return_true_for_diagonal_move_up_right() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, B));

    assertThat(moveIsDiagonal.validate(move, board)).isTrue();
  }

  @Test
  void return_true_for_diagonal_move_up_left() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, B), new BoardCoordinates(ROW_2, A));

    assertThat(moveIsDiagonal.validate(move, board)).isTrue();
  }

  @Test
  void return_true_for_diagonal_move_down_right() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_2, A), new BoardCoordinates(ROW_1, B));

    assertThat(moveIsDiagonal.validate(move, board)).isTrue();
  }

  @Test
  void return_true_for_diagonal_move_down_left() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_2, B), new BoardCoordinates(ROW_1, A));

    assertThat(moveIsDiagonal.validate(move, board)).isTrue();
  }

  @Test
  void return_true_for_diagonal_jump_move_up_right() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_3, C));

    assertThat(moveIsDiagonal.validate(move, board)).isTrue();
  }

  @Test
  void return_true_for_diagonal_jump_move_down_left() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_3, C), new BoardCoordinates(ROW_1, A));

    assertThat(moveIsDiagonal.validate(move, board)).isTrue();
  }

  @Test
  void return_false_for_move_to_the_right() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_1, B));

    assertThat(moveIsDiagonal.validate(move, board)).isFalse();
  }

  @Test
  void return_false_for_move_up() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_1, A));

    assertThat(moveIsDiagonal.validate(move, board)).isFalse();
  }

  @Test
  void return_false_for_move_one_up_and_two_right() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, C));

    assertThat(moveIsDiagonal.validate(move, board)).isFalse();
  }
}
