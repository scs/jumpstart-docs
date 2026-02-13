from abc import ABC, abstractmethod


class Label(ABC):
    @abstractmethod
    def render(self) -> str:
        pass


class LightLabel(Label):
    def render(self) -> str:
        return "[Light Label]"


class DarkLabel(Label):
    def render(self) -> str:
        return "[Dark Label]"


class LabelFactory:
    def __init__(self, is_dark_theme: bool) -> None:
        self.is_dark_theme = is_dark_theme

    def create_label(self) -> Label:
        if self.is_dark_theme:
            return DarkLabel()
        return LightLabel()


class Header:
    def __init__(self, factory: LabelFactory) -> None:
        self.title_label: Label = factory.create_label()

    def display(self) -> str:
        return f"Header: {self.title_label.render()}"


class MainView:
    def __init__(self, factory: LabelFactory) -> None:
        self.content_label: Label = factory.create_label()

    def display(self) -> str:
        return f"MainView: {self.content_label.render()}"


if __name__ == "__main__":
    print("Light theme:")
    light_factory = LabelFactory(is_dark_theme=False)
    header_light = Header(light_factory)
    main_light = MainView(light_factory)
    print(header_light.display())
    print(main_light.display())

    print("\nDark theme:")
    dark_factory = LabelFactory(is_dark_theme=True)
    header_dark = Header(dark_factory)
    main_dark = MainView(dark_factory)
    print(header_dark.display())
    print(main_dark.display())
