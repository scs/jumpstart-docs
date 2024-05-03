package ch.scs.jumpstart.pattern.examples.checkers.dom.board;

import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Piece;
import ch.scs.jumpstart.pattern.examples.checkers.util.Tuple;

@SuppressWarnings("ClassCanBeRecord")
class JumpGambleLostMove implements Command {
  private final Store store;
  private final Tuple<BoardCoordinates, Piece> start;

  JumpGambleLostMove(Store store, Tuple<BoardCoordinates, Piece> start) {
    this.store = store;
    this.start = start;
  }

  @Override
  public void execute() {
    store.removePiece(start.getKey());
  }

  @Override
  public void undo() {
    store.addPiece(start.getKey(), start.getValue());
  }
}
