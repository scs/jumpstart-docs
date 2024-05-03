package ch.scs.jumpstart.pattern.examples.checkers.dom.board;

import ch.scs.jumpstart.pattern.examples.checkers.dom.*;
import ch.scs.jumpstart.pattern.examples.checkers.util.Tuple;
import java.util.*;

public class Board {
  private final List<BoardObserver> boardObservers = new ArrayList<>();
  private final Store store = new Store();
  private final List<Tuple<Player, Command>> executedCommands = new ArrayList<>();

  public void executeMove(Move move) {
    Command command = createCommand(move);
    command.execute();
    executedCommands.add(Tuple.of(move.player(), command));
    boardObservers.forEach(boardObserver -> boardObserver.boardChanged(this));
  }

  public Player undoLastTurn() throws NoPreviousMovesException {
    if (executedCommands.isEmpty()) {
      throw new NoPreviousMovesException();
    }
    Tuple<Player, Command> lastCommandTuple = executedCommands.get(executedCommands.size() - 1);
    for (int i = executedCommands.size() - 1; i >= 0; i--) {
      Tuple<Player, Command> currentCommandTuple = executedCommands.get(i);
      if (!currentCommandTuple.getKey().equals(lastCommandTuple.getKey())) {
        break;
      }
      currentCommandTuple.getValue().undo();
      executedCommands.remove(i);
    }
    boardObservers.forEach(boardObserver -> boardObserver.boardChanged(this));
    return lastCommandTuple.getKey();
  }

  public Optional<Piece> getPieceAt(BoardCoordinates boardCoordinates) {
    return store.getPieceAt(boardCoordinates);
  }

  public void registerObserver(BoardObserver boardObserver) {
    boardObservers.add(boardObserver);
  }

  private Command createCommand(Move move) {
    Piece pieceAtStart = store.getPieceAt(move.start()).orElseThrow();
    Tuple<BoardCoordinates, Piece> start = Tuple.of(move.start(), pieceAtStart);
    if (move.jumpGambleResult() == JumpGambleResult.LOST) {
      return new JumpGambleLostMove(store, start);
    }

    boolean convertToKing = isConvertToKing(move);
    Piece pieceAtEnd = new Piece(move.player(), pieceAtStart.isKing() || convertToKing);
    Tuple<BoardCoordinates, Piece> end = Tuple.of(move.end(), pieceAtEnd);
    if (move.isJumpMove()) {
      Piece pieceBetween = move.getCoordinatesBetween().flatMap(store::getPieceAt).orElseThrow();
      return new JumpMove(
          store, start, Tuple.of(move.getCoordinatesBetween().orElseThrow(), pieceBetween), end);
    } else {
      return new SimpleMove(store, start, end);
    }
  }

  private boolean isConvertToKing(Move move) {
    if (move.player() == Player.PLAYER_WHITE) {
      return move.end().row().isLastRow();
    }
    return move.end().row().isFirstRow();
  }

  public static class NoPreviousMovesException extends Exception {
    private NoPreviousMovesException() {
      super();
    }
  }
}
