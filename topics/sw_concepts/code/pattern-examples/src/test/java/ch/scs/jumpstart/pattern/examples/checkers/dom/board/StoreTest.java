package ch.scs.jumpstart.pattern.examples.checkers.dom.board;

import static ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates.Column.*;
import static ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates.Row.*;
import static org.assertj.core.api.Assertions.assertThat;

import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Piece;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Player;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StoreTest {
  private static final Piece WHITE_PIECE = new Piece(Player.PLAYER_WHITE, false);
  private static final BoardCoordinates EMPTY_COORDINATES_AT_START = new BoardCoordinates(ROW_4, A);
  private static final BoardCoordinates OCCUPIED_COORDINATES_AT_START =
      new BoardCoordinates(ROW_6, B);
  private static final Piece RED_PIECE = new Piece(Player.PLAYER_RED, false);
  private Store store;

  @BeforeEach
  void setup() {
    store = new Store();
  }

  @Test
  void add_piece_on_empty_place() {
    assertThat(store.getPieceAt(EMPTY_COORDINATES_AT_START)).isEmpty();
    store.addPiece(EMPTY_COORDINATES_AT_START, WHITE_PIECE);
    assertThat(store.getPieceAt(EMPTY_COORDINATES_AT_START)).isEqualTo(Optional.of(WHITE_PIECE));
  }

  @Test
  void add_piece_on_occupied_place() {
    assertThat(store.getPieceAt(OCCUPIED_COORDINATES_AT_START)).isEqualTo(Optional.of(RED_PIECE));
    store.addPiece(OCCUPIED_COORDINATES_AT_START, WHITE_PIECE);
    assertThat(store.getPieceAt(OCCUPIED_COORDINATES_AT_START)).isEqualTo(Optional.of(WHITE_PIECE));
  }

  @Test
  void remove_piece_at_empty_place() {
    store.removePiece(EMPTY_COORDINATES_AT_START);
    assertThat(store.getPieceAt(EMPTY_COORDINATES_AT_START)).isEmpty();
  }

  @Test
  void remove_piece_at_occupied_place() {
    store.removePiece(OCCUPIED_COORDINATES_AT_START);
    assertThat(store.getPieceAt(OCCUPIED_COORDINATES_AT_START)).isEmpty();
  }

  @Test
  void get_piece_of_empty_place() {
    assertThat(store.getPieceAt(EMPTY_COORDINATES_AT_START)).isEmpty();
  }

  @Test
  void get_piece_at_occupied_place() {
    assertThat(store.getPieceAt(OCCUPIED_COORDINATES_AT_START)).isEqualTo(Optional.of(RED_PIECE));
  }
}
