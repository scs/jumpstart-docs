package ch.scs.jumpstart.stack.solution;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolutionMyStackTest {

  public static final String ELEM_1 = "1";
  public static final String ELEM_2 = "2";
  public static final String ELEM_3 = "3";
  private SolutionMyStack<Object> stack;

  @BeforeEach
  public void setup() {
    stack = new SolutionMyStack<>();
  }

  @Test
  public void a_new_stack_is_empty() {
    assertThat(stack.pop(), nullValue());
  }

  @Test
  public void pop_elements_in_reverse_add_order() {
    Stream.of(ELEM_1, ELEM_2, ELEM_3).forEach(stack::add);

    assertThat(List.of(stack.pop(), stack.pop(), stack.pop()), is(List.of(ELEM_3, ELEM_2, ELEM_1)));
    assertThat(stack.pop(), nullValue());
  }
}
