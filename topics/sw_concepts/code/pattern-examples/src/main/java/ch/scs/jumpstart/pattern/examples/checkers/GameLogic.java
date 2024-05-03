package ch.scs.jumpstart.pattern.examples.checkers;

import ch.scs.jumpstart.pattern.examples.checkers.dom.*;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;
import ch.scs.jumpstart.pattern.examples.checkers.movevalidator.MoveValidator;
import ch.scs.jumpstart.pattern.examples.checkers.movevalidator.NoOtherMoveToJumpPossible;
import ch.scs.jumpstart.pattern.examples.checkers.util.Console;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("ClassCanBeRecord")
public class GameLogic {
  private final Console console;
  private final Board board;
  private final List<MoveValidator> moveValidators;
  private final MoveExecutor moveExecutor;
  private final NoOtherMoveToJumpPossible noOtherMoveToJumpPossible;
  private final WinCondition winCondition;

  public GameLogic(
      Console console,
      Board board,
      List<MoveValidator> moveValidators,
      MoveExecutor moveExecutor,
      NoOtherMoveToJumpPossible noOtherMoveToJumpPossible,
      WinCondition winCondition) {
    this.console = console;
    this.board = board;
    this.moveValidators = moveValidators;
    this.moveExecutor = moveExecutor;
    this.noOtherMoveToJumpPossible = noOtherMoveToJumpPossible;
    this.winCondition = winCondition;
  }

  public void run() {
    Player currentPlayer = Player.PLAYER_RED;
    while (true) {
      Player otherPlayer =
          currentPlayer == Player.PLAYER_RED ? Player.PLAYER_WHITE : Player.PLAYER_RED;
      console.print(
          currentPlayer
              + ", make your move. Or type 'undo' to go back to the start of the turn of "
              + otherPlayer);
      if (doPlayerMove(currentPlayer)) {
        return;
      }
      if (currentPlayer == Player.PLAYER_WHITE) {
        currentPlayer = Player.PLAYER_RED;
      } else {
        currentPlayer = Player.PLAYER_WHITE;
      }
    }
  }

  @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.CognitiveComplexity"})
  private boolean doPlayerMove(Player player) {
    BoardCoordinates startCoordinatesForMultipleJump = null;
    while (true) {
      String userInput = console.getUserInput();
      if ("undo".equals(userInput.toLowerCase().trim())) {
        try {
          Player playerOfUndoneMove = board.undoLastTurn();
          if (playerOfUndoneMove.equals(player)) {
            continue;
          } else {
            return false;
          }
        } catch (Board.NoPreviousMovesException e) {
          console.print("There were no previous moves to undo. Please make a move.");
          continue;
        }
      }
      Move move;
      try {
        move = Move.parse(player, userInput);
      } catch (IllegalArgumentException e) {
        console.print("Invalid input, try again");
        continue;
      }
      if (startCoordinatesForMultipleJump != null
          && !startCoordinatesForMultipleJump.equals(move.start())) {
        console.print("For a multiple jump move, the same piece has to be used. Try again");
        continue;
      }
      Optional<Piece> pieceAtStart = board.getPieceAt(move.start());
      if (!moveValidators.stream().allMatch(moveValidator -> moveValidator.validate(move, board))) {
        console.print("Invalid move, try again");
        continue;
      }

      Move executedMove = moveExecutor.executeMove(move);

      boolean hasPlayerWon = winCondition.hasPlayerWon(player, board);
      if (hasPlayerWon) {
        console.print("Congratulations, player " + player + " has won");
        return true;
      }

      if (executedMove.jumpGambleResult() == JumpGambleResult.WON) {
        console.print("The gamble has been won, " + player + " can play again.");
        console.print(
            player + ", make your move. Or type 'undo' to go back to the start of your turn.");
        continue;
      }

      Optional<Piece> pieceAtEnd = board.getPieceAt(move.end());
      boolean wasKingCreated = wasKingCreated(pieceAtStart.orElse(null), pieceAtEnd.orElse(null));
      if (wasKingCreated) {
        return false;
      }
      if (!move.isJumpMove()
          || !noOtherMoveToJumpPossible.jumpMovePossibleFrom(move.end(), board)) {
        return false;
      }
      console.print("Multiple jump move for " + player + ". Enter your next jump.");
      console.print("Or type 'undo' to go back to the start of your turn.");
      startCoordinatesForMultipleJump = move.end();
    }
  }

  private boolean wasKingCreated(Piece pieceAtStart, Piece pieceAtEnd) {
    if (pieceAtStart == null || pieceAtEnd == null) {
      return false;
    }
    return !pieceAtStart.isKing() && pieceAtEnd.isKing();
  }
}
