package ch.scs.jumpstart.pattern.examples.checkers.util;

import static ch.scs.jumpstart.pattern.examples.checkers.dom.Player.PLAYER_RED;
import static ch.scs.jumpstart.pattern.examples.checkers.dom.Player.PLAYER_WHITE;

import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Piece;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Player;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PointsCalculator {

  @SuppressWarnings("PMD.CognitiveComplexity")
  public Map<Player, Integer> calculatePoints(Board board) {
    int pointsPlayerWhite = 0;
    int pointsPlayerRed = 0;
    BoardCoordinates.Row[] rows = BoardCoordinates.Row.values();
    BoardCoordinates.Column[] columns = BoardCoordinates.Column.values();
    for (BoardCoordinates.Row row : rows) {
      for (BoardCoordinates.Column col : columns) {
        BoardCoordinates currentCoordinates = new BoardCoordinates(row, col);
        Optional<Piece> pieceAt = board.getPieceAt(currentCoordinates);
        if (pieceAt.isPresent()) {
          if (pieceAt.get().owner() == PLAYER_WHITE) {
            if (pieceAt.get().isKing()) {
              pointsPlayerWhite += 2;
            } else {
              pointsPlayerWhite += 1;
            }
          } else {
            if (pieceAt.get().isKing()) {
              pointsPlayerRed += 2;
            } else {
              pointsPlayerRed += 1;
            }
          }
        }
      }
    }
    Map<Player, Integer> pointsOnBoard = new HashMap<>();
    pointsOnBoard.put(PLAYER_WHITE, pointsPlayerWhite);
    pointsOnBoard.put(PLAYER_RED, pointsPlayerRed);

    return pointsOnBoard;
  }
}
