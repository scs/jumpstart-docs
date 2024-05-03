package ch.scs.jumpstart.pattern.examples.checkers.dom;

import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;

public interface BoardObserver {
  void boardChanged(Board board);
}
