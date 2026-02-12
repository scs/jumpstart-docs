from abc import ABC, abstractmethod


class Label(ABC):
    @abstractmethod
    def paint_background(self) -> None:
        pass


class LightLabel(Label):
    def paint_background(self) -> None:
        print("  Painting LIGHT background for label")
        print("  - Setting background color: #FFFFFF")


class DarkLabel(Label):
    def paint_background(self) -> None:
        print("  Painting DARK background for label")
        print("  - Setting background color: #1E1E1E")


class Button(ABC):
    @abstractmethod
    def paint_background(self) -> None:
        pass


class LightButton(Button):
    def paint_background(self) -> None:
        print("  Painting LIGHT background for button")
        print("  - Setting background color: #FFFFFF")


class DarkButton(Button):
    def paint_background(self) -> None:
        print("  Painting DARK background for button")
        print("  - Setting background color: #1E1E1E")


if __name__ == "__main__":
    print("Light theme components:")
    light_label = LightLabel()
    light_button = LightButton()
    print("Label:")
    light_label.paint_background()
    print("Button:")
    light_button.paint_background()

    print("\nDark theme components:")
    dark_label = DarkLabel()
    dark_button = DarkButton()
    print("Label:")
    dark_label.paint_background()
    print("Button:")
    dark_button.paint_background()
