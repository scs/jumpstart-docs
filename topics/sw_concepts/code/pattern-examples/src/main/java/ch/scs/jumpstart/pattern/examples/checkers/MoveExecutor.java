package ch.scs.jumpstart.pattern.examples.checkers;

import static ch.scs.jumpstart.pattern.examples.checkers.util.CoinTosser.Result.HEADS;
import static ch.scs.jumpstart.pattern.examples.checkers.util.CoinTosser.Result.TAILS;

import ch.scs.jumpstart.pattern.examples.checkers.dom.JumpGambleResult;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Move;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;
import ch.scs.jumpstart.pattern.examples.checkers.util.CoinTosser;
import ch.scs.jumpstart.pattern.examples.checkers.util.Console;

@SuppressWarnings("ClassCanBeRecord")
public class MoveExecutor {
  private final Board board;
  private final CoinTosser coinTosser;
  private final Console console;

  public MoveExecutor(Board board, CoinTosser coinTosser, Console console) {
    this.board = board;
    this.coinTosser = coinTosser;
    this.console = console;
  }

  public Move executeMove(Move move) {
    if (!move.isJumpMove()) {
      board.executeMove(move);
      return move;
    }
    console.print(move.player() + " is making a jump move. Now " + move.player() + " may gamble.");
    console.print("If " + move.player() + " does not gamble, the jump move is executed normally.");
    console.print("If " + move.player() + " does gamble, a coin will be tossed.");
    console.print(
        "If the coin toss is "
            + HEADS
            + ", then the jump move will be executed, but "
            + move.player()
            + " gets another turn.");
    console.print(
        "If the coin toss is "
            + TAILS
            + ", then the jump move fails and the piece at "
            + move.start()
            + ", which would have been used for the jump move, is removed.");
    GambleChoice gambleChoice = null;
    do {
      console.print(
          move.player() + " type \"yes\" to gamble, \"no\" to execute the jump move normally");
      String userInput = console.getUserInput();
      try {
        gambleChoice = GambleChoice.valueOf(userInput.trim().toUpperCase());
      } catch (IllegalArgumentException e) {
        console.print("The input of " + move.player() + " was invalid. Input was: " + userInput);
      }
    } while (gambleChoice == null);
    if (gambleChoice == GambleChoice.NO) {
      board.executeMove(move);
      return move;
    }
    CoinTosser.Result tossResult = coinTosser.toss(board, move.player());
    JumpGambleResult jumpGambleResult =
        tossResult == HEADS ? JumpGambleResult.WON : JumpGambleResult.LOST;
    console.print("Coin toss resulted in " + tossResult + ", the gamble was: " + jumpGambleResult);
    Move newMove = move.withJumpGambleResult(jumpGambleResult);
    board.executeMove(newMove);
    return newMove;
  }

  private enum GambleChoice {
    @SuppressWarnings("unused")
    YES,
    NO
  }
}
