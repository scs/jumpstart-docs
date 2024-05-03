package ch.scs.jumpstart.pattern.examples.gui;

@SuppressWarnings("unused")
public class RoundedButtonFactory implements ButtonFactory {
  @Override
  public Button createBackButton() {
    return new RoundedButton("back");
  }

  @Override
  public Button createForwardButton() {
    return new RoundedButton("forward");
  }
}
