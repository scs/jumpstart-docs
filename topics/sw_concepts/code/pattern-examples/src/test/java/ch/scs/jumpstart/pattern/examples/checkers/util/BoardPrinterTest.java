package ch.scs.jumpstart.pattern.examples.checkers.util;

import static org.mockito.Mockito.*;

import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates;
import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates.Column;
import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates.Row;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Piece;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Player;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

class BoardPrinterTest {

  private Board board;
  private Console console;
  private BoardPrinter boardPrinter;
  private InOrder inOrder;

  @BeforeEach
  void setup() {
    board = spy(new Board());
    console = mock(Console.class);
    boardPrinter = new BoardPrinter(console);
    inOrder = inOrder(console);
  }

  @Test
  void prints_initial_board_state() {
    boardPrinter.printBoard(board);

    inOrder.verify(console).print("        a     b     c     d     e     f     g     h     ");
    inOrder.verify(console).print("    +-------------------------------------------------+");
    inOrder.verify(console).print("8   | [   ] [R_P] [   ] [R_P] [   ] [R_P] [   ] [R_P] |   8");
    inOrder.verify(console).print("7   | [R_P] [   ] [R_P] [   ] [R_P] [   ] [R_P] [   ] |   7");
    inOrder.verify(console).print("6   | [   ] [R_P] [   ] [R_P] [   ] [R_P] [   ] [R_P] |   6");
    inOrder.verify(console).print("5   | [   ] [   ] [   ] [   ] [   ] [   ] [   ] [   ] |   5");
    inOrder.verify(console).print("4   | [   ] [   ] [   ] [   ] [   ] [   ] [   ] [   ] |   4");
    inOrder.verify(console).print("3   | [W_P] [   ] [W_P] [   ] [W_P] [   ] [W_P] [   ] |   3");
    inOrder.verify(console).print("2   | [   ] [W_P] [   ] [W_P] [   ] [W_P] [   ] [W_P] |   2");
    inOrder.verify(console).print("1   | [W_P] [   ] [W_P] [   ] [W_P] [   ] [W_P] [   ] |   1");
    inOrder.verify(console).print("    +-------------------------------------------------+");
    inOrder.verify(console).print("        a     b     c     d     e     f     g     h     ");
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  void print_king() {
    when(board.getPieceAt(new BoardCoordinates(Row.ROW_1, Column.A)))
        .thenReturn(Optional.of(new Piece(Player.PLAYER_WHITE, true)));

    boardPrinter.printBoard(board);

    inOrder.verify(console).print("        a     b     c     d     e     f     g     h     ");
    inOrder.verify(console).print("    +-------------------------------------------------+");
    inOrder.verify(console).print("8   | [   ] [R_P] [   ] [R_P] [   ] [R_P] [   ] [R_P] |   8");
    inOrder.verify(console).print("7   | [R_P] [   ] [R_P] [   ] [R_P] [   ] [R_P] [   ] |   7");
    inOrder.verify(console).print("6   | [   ] [R_P] [   ] [R_P] [   ] [R_P] [   ] [R_P] |   6");
    inOrder.verify(console).print("5   | [   ] [   ] [   ] [   ] [   ] [   ] [   ] [   ] |   5");
    inOrder.verify(console).print("4   | [   ] [   ] [   ] [   ] [   ] [   ] [   ] [   ] |   4");
    inOrder.verify(console).print("3   | [W_P] [   ] [W_P] [   ] [W_P] [   ] [W_P] [   ] |   3");
    inOrder.verify(console).print("2   | [   ] [W_P] [   ] [W_P] [   ] [W_P] [   ] [W_P] |   2");
    inOrder.verify(console).print("1   | [W_K] [   ] [W_P] [   ] [W_P] [   ] [W_P] [   ] |   1");
    inOrder.verify(console).print("    +-------------------------------------------------+");
    inOrder.verify(console).print("        a     b     c     d     e     f     g     h     ");
    inOrder.verifyNoMoreInteractions();
  }
}
