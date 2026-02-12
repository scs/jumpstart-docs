from abc import ABC, abstractmethod


class PaintBackgroundStrategy(ABC):
    @abstractmethod
    def execute(self) -> None:
        pass


class LightBackgroundStrategy(PaintBackgroundStrategy):
    def execute(self) -> None:
        print("  Painting LIGHT background")
        print("  - Setting background color: #FFFFFF")


class DarkBackgroundStrategy(PaintBackgroundStrategy):
    def execute(self) -> None:
        print("  Painting DARK background")
        print("  - Setting background color: #1E1E1E")


class Label:
    def __init__(self, background_strategy: PaintBackgroundStrategy) -> None:
        self.background_strategy = background_strategy

    def paint_background(self) -> None:
        self.background_strategy.execute()


class LightLabel(Label):
    pass


class DarkLabel(Label):
    pass


class Button:
    def __init__(self, background_strategy: PaintBackgroundStrategy) -> None:
        self.background_strategy = background_strategy

    def paint_background(self) -> None:
        self.background_strategy.execute()


class LightButton(Button):
    pass


class DarkButton(Button):
    pass


if __name__ == "__main__":
    light_strategy = LightBackgroundStrategy()
    dark_strategy = DarkBackgroundStrategy()

    print("Light theme components (using shared strategy):")
    light_label = LightLabel(light_strategy)
    light_button = LightButton(light_strategy)
    print("Label:")
    light_label.paint_background()
    print("Button:")
    light_button.paint_background()

    print("\nDark theme components (using shared strategy):")
    dark_label = DarkLabel(dark_strategy)
    dark_button = DarkButton(dark_strategy)
    print("Label:")
    dark_label.paint_background()
    print("Button:")
    dark_button.paint_background()

    print("\nChanging label strategy at runtime:")
    light_label.background_strategy = dark_strategy
    print("Light label with dark background:")
    light_label.paint_background()
