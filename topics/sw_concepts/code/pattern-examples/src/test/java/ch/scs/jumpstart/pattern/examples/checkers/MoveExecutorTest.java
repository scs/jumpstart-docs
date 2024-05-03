package ch.scs.jumpstart.pattern.examples.checkers;

import static ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates.Column.*;
import static ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates.Row.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ch.scs.jumpstart.pattern.examples.checkers.dom.BoardCoordinates;
import ch.scs.jumpstart.pattern.examples.checkers.dom.JumpGambleResult;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Move;
import ch.scs.jumpstart.pattern.examples.checkers.dom.Player;
import ch.scs.jumpstart.pattern.examples.checkers.dom.board.Board;
import ch.scs.jumpstart.pattern.examples.checkers.util.CoinTosser;
import ch.scs.jumpstart.pattern.examples.checkers.util.CoinTosser.Result;
import ch.scs.jumpstart.pattern.examples.checkers.util.Console;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoveExecutorTest {

  private static final Move NORMAL_MOVE =
      Move.of(Player.PLAYER_WHITE, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_2, B));
  private static final Move JUMP_MOVE =
      Move.of(Player.PLAYER_WHITE, new BoardCoordinates(ROW_1, A), new BoardCoordinates(ROW_3, C));
  private static final Move JUMP_WITH_WON_GAMBLE_RESULT =
      JUMP_MOVE.withJumpGambleResult(JumpGambleResult.WON);
  private static final Move JUMP_WITH_LOST_GAMBLE_RESULT =
      JUMP_MOVE.withJumpGambleResult(JumpGambleResult.LOST);
  private CoinTosser coinTosser;
  private Board board;
  private MoveExecutor moveExecutor;
  private Console console;

  @BeforeEach
  void setup() {
    coinTosser = mock(CoinTosser.class);
    board = mock(Board.class);
    console = mock(Console.class);
    moveExecutor = new MoveExecutor(board, coinTosser, console);
  }

  @Test
  void execute_move_normally_if_no_jump_move() {
    Move move = NORMAL_MOVE;

    Move executedMove = moveExecutor.executeMove(move);

    verify(board).executeMove(same(move));
    assertThat(executedMove).isSameAs(move);
  }

  @Test
  void ask_player_if_he_wants_to_gamble_for_jump_move() {
    when(console.getUserInput()).thenThrow(RuntimeException.class);

    Assertions.assertThrows(RuntimeException.class, () -> moveExecutor.executeMove(JUMP_MOVE));

    verify(console).getUserInput();
  }

  @Test
  void ask_player_if_he_wants_to_gamble_until_he_types_yes() {
    when(console.getUserInput()).thenReturn("blabla").thenReturn("invalid").thenReturn("yes");

    moveExecutor.executeMove(JUMP_MOVE);

    verify(console, times(3)).getUserInput();
  }

  @Test
  void ask_player_if_he_wants_to_gamble_until_he_types_no() {
    when(console.getUserInput()).thenReturn("blabla").thenReturn("invalid").thenReturn("no");

    moveExecutor.executeMove(JUMP_MOVE);

    verify(console, times(3)).getUserInput();
  }

  @Test
  void execute_move_normally_if_player_types_no() {
    when(console.getUserInput()).thenReturn("no");

    Move executedMove = moveExecutor.executeMove(JUMP_MOVE);

    verify(board).executeMove(same(JUMP_MOVE));
    assertThat(executedMove).isSameAs(JUMP_MOVE);
  }

  @Test
  void execute_gamble_win_move_if_player_types_yes_and_wins() {
    when(console.getUserInput()).thenReturn("yes");
    when(coinTosser.toss(notNull(), notNull())).thenReturn(Result.HEADS);

    Move executedMove = moveExecutor.executeMove(JUMP_MOVE);

    verify(board).executeMove(eq(JUMP_WITH_WON_GAMBLE_RESULT));
    assertThat(executedMove).isEqualTo(JUMP_WITH_WON_GAMBLE_RESULT);
  }

  @Test
  void execute_gamble_lost_move_if_player_types_yes_and_loses() {
    when(console.getUserInput()).thenReturn("yes");
    when(coinTosser.toss(notNull(), notNull())).thenReturn(Result.TAILS);

    Move executedMove = moveExecutor.executeMove(JUMP_MOVE);

    verify(board).executeMove(eq(JUMP_WITH_LOST_GAMBLE_RESULT));
    assertThat(executedMove).isEqualTo(JUMP_WITH_LOST_GAMBLE_RESULT);
  }
}
