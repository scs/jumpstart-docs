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


class Header:
    def __init__(self, is_dark_theme: bool) -> None:
        if is_dark_theme:
            self.title_label: Label = DarkLabel()
        else:
            self.title_label = LightLabel()

    def display(self) -> str:
        return f"Header: {self.title_label.render()}"


class MainView:
    def __init__(self, is_dark_theme: bool) -> None:
        if is_dark_theme:
            self.content_label: Label = DarkLabel()
        else:
            self.content_label = LightLabel()

    def display(self) -> str:
        return f"MainView: {self.content_label.render()}"


if __name__ == "__main__":
    print("Light theme:")
    header_light = Header(is_dark_theme=False)
    main_light = MainView(is_dark_theme=False)
    print(header_light.display())
    print(main_light.display())

    print("\nDark theme:")
    header_dark = Header(is_dark_theme=True)
    main_dark = MainView(is_dark_theme=True)
    print(header_dark.display())
    print(main_dark.display())
