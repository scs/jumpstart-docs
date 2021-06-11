package ch.scs.jumpstart.stack.solution;

import java.util.ArrayList;
import java.util.List;

public class SolutionMyStack<T> {
  private final List<T> stack = new ArrayList<>();

  public T pop() {
    if (stack.size() == 0) {
      return null;
    }
    return stack.remove(stack.size() - 1);
  }

  public void add(T test) {
    stack.add(test);
  }
}
