package ch.scs.jumpstart.pattern.examples.gui;

@SuppressWarnings("unused")
public class FlatButtonFactory implements ButtonFactory {
  @Override
  public Button createBackButton() {
    return new FlatButton("back");
  }

  @Override
  public Button createForwardButton() {
    return new FlatButton("forward");
  }
}
