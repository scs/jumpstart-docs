package ch.scs.jumpstart.pattern.examples.checkers.movevalidator;

import static ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates.*;
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

class MoveLengthTest {
  private MoveLength moveLength;
  private Board board;

  @BeforeEach
  void setup() {
    moveLength = new MoveLength();
    board = new Board();
  }

  @Test
  void return_true_if_player_moves_1_row() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, A));
    assertThat(moveLength.validate(move, board)).isTrue();
  }

  @Test
  void return_true_if_player_moves_2_row() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_3, A));
    assertThat(moveLength.validate(move, board)).isTrue();
  }

  @Test
  void return_false_if_player_moves_3_row() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_4, A));
    assertThat(moveLength.validate(move, board)).isFalse();
  }

  @Test
  void return_true_if_player_moves_7_row() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_8, A));
    assertThat(moveLength.validate(move, board)).isFalse();
  }

  @Test
  void return_true_if_player_moves_1_col() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_1, B));
    assertThat(moveLength.validate(move, board)).isTrue();
  }

  @Test
  void return_true_if_player_moves_2_col() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_1, C));
    assertThat(moveLength.validate(move, board)).isTrue();
  }

  @Test
  void return_false_if_player_moves_3_col() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_1, D));
    assertThat(moveLength.validate(move, board)).isFalse();
  }

  @Test
  void return_true_if_player_moves_7_col() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_1, H));
    assertThat(moveLength.validate(move, board)).isFalse();
  }

  @Test
  void return_true_if_player_moves_1_row_back() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_2, A), new BoardCoordinates(ROW_1, A));
    assertThat(moveLength.validate(move, board)).isTrue();
  }

  @Test
  void return_true_if_player_moves_1_col_back() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, B), new BoardCoordinates(ROW_1, A));
    assertThat(moveLength.validate(move, board)).isTrue();
  }

  @Test
  void return_true_if_player_moves_1_col_and_2_rows() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, B), new BoardCoordinates(ROW_3, A));
    assertThat(moveLength.validate(move, board)).isTrue();
  }

  @Test
  void return_false_if_move_does_not_change_position() {
    Move move =
        Move.of(Player.PLAYER_RED, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_1, A));
    assertThat(moveLength.validate(move, board)).isFalse();
  }
}
