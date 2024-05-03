package ch.scs.jumpstart.pattern.examples.checkers.movevalidator;

import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Move;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Piece;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;
import java.util.Optional;

public class OpponentPieceBetweenJump implements MoveValidator {
  @Override
  public boolean validate(Move move, Board board) {
    Optional<BoardCoordinates> coordinatesBetween = move.getCoordinatesBetween();
    if (coordinatesBetween.isEmpty()) {
      return true;
    }
    Optional<Piece> pieceBetweenJump = board.getPieceAt(coordinatesBetween.get());
    if (pieceBetweenJump.isEmpty()) {
      return false;
    }
    return pieceBetweenJump.get().owner() != move.player();
  }
}
