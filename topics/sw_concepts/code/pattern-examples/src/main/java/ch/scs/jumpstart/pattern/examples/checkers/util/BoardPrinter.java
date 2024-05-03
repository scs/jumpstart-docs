package ch.scs.jumpstart.pattern.examples.checkers.util;

import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates;
import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates.Column;
import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates.Row;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Piece;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Player;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;
import java.util.*;

public class BoardPrinter {
  private final Console console;

  public BoardPrinter(Console console) {
    this.console = console;
  }

  @SuppressWarnings("PMD.CognitiveComplexity")
  public void printBoard(Board board) {
    StringBuilder header = new StringBuilder();
    header.append("        ");
    for (Column col : Column.values()) {
      header.append(col.name().toLowerCase(Locale.ROOT)).append("     ");
    }
    console.print(header.toString());
    console.print("    +-------------------------------------------------+");

    List<Row> rows = Arrays.asList(Row.values());
    Collections.reverse(rows);
    for (Row row : rows) {
      StringBuilder rowString = new StringBuilder((row.ordinal() + 1) + "   |");
      for (Column col : Column.values()) {
        Optional<Piece> pieceAt = board.getPieceAt(new BoardCoordinates(row, col));
        rowString.append(" ");
        if (pieceAt.isEmpty()) {
          rowString.append("[   ]");
        } else {
          Piece piece = pieceAt.get();
          rowString.append("[");
          if (piece.owner() == Player.PLAYER_WHITE) {
            rowString.append("W");
          } else {
            rowString.append("R");
          }
          rowString.append("_");
          if (piece.isKing()) {
            rowString.append("K");
          } else {
            rowString.append("P");
          }
          rowString.append("]");
        }
      }
      console.print(rowString + " |   " + (row.ordinal() + 1));
    }

    console.print("    +-------------------------------------------------+");
    console.print(header.toString());
  }
}
