package ch.scs.jumpstart.pattern.examples.checkers.dom.board;

interface Command {
  void execute();

  void undo();
}
