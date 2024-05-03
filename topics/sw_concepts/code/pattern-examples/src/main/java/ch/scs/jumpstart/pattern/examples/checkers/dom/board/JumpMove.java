package ch.scs.jumpstart.pattern.examples.checkers.dom.board;

import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Piece;
import ch.scs.jumpstart.pattern.examples.checkers.util.Tuple;

@SuppressWarnings("ClassCanBeRecord")
public class JumpMove implements Command {
  private final Store store;
  private final Tuple<BoardCoordinates, Piece> start;
  private final Tuple<BoardCoordinates, Piece> pieceBetween;
  private final Tuple<BoardCoordinates, Piece> end;

  public JumpMove(
      Store store,
      Tuple<BoardCoordinates, Piece> start,
      Tuple<BoardCoordinates, Piece> pieceBetween,
      Tuple<BoardCoordinates, Piece> end) {
    this.store = store;
    this.start = start;
    this.pieceBetween = pieceBetween;
    this.end = end;
  }

  @Override
  public void execute() {
    store.removePiece(start.getKey());
    store.removePiece(pieceBetween.getKey());
    store.addPiece(end.getKey(), end.getValue());
  }

  @Override
  public void undo() {
    store.addPiece(start.getKey(), start.getValue());
    store.addPiece(pieceBetween.getKey(), pieceBetween.getValue());
    store.removePiece(end.getKey());
  }
}
