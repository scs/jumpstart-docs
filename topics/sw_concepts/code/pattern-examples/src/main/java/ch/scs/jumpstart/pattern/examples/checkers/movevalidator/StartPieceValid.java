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
    Optional<Piece> startPiece = board.getPieceAt(start);
    return startPiece.filter(piece -> piece.owner() == move.player()).isPresent();
  }
}
