package ch.scs.jumpstart.pattern.examples.checkers.movevalidator;

import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Move;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Piece;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;
import java.util.Optional;

public class StartPieceValid implements MoveValidator {
  @Override
  public boolean validate(Move move, Board board) {
    BoardCoordinates start = move.start();

    BoardCoordinates.Row[] row = BoardCoordinates.Row.values();
    BoardCoordinates.Column[] col = BoardCoordinates.Column.values();

    Optional<Piece> startPiece = board.getPieceAt(start);

    if (startPiece.isEmpty()) {
      return false;
    }

    return startPiece.get().owner() == move.player();
  }
}
