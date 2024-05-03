package ch.scs.jumpstart.pattern.examples.checkers.movevalidator;

import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Move;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;

public class MoveIsDiagonal implements MoveValidator {
  @Override
  public boolean validate(Move move, Board board) {
    BoardCoordinates start = move.start();
    BoardCoordinates end = move.end();
    boolean bothDirectionsChange = start.row() != end.row() && start.column() != end.column();

    int rowDiff = Math.abs(start.row().ordinal() - end.row().ordinal());
    int colDiff = Math.abs(start.column().ordinal() - end.column().ordinal());
    return bothDirectionsChange && rowDiff == colDiff;
  }
}
