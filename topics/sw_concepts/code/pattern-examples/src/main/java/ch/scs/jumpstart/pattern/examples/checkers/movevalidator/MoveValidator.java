package ch.scs.jumpstart.pattern.examples.checkers.movevalidator;

import ch.scs.jumpstart.pattern.examples.checkers.dom.Move;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;

public interface MoveValidator {
  boolean validate(Move move, Board board);
}
