from abc import ABC, abstractmethod


class Label(ABC):
    @abstractmethod
    def render(self) -> str:
        pass


class Button(ABC):
    @abstractmethod
    def render(self) -> str:
        pass


class LightLabel(Label):
    def render(self) -> str:
        return "Light Label"


class DarkLabel(Label):
    def render(self) -> str:
        return "Dark Label"


class LightButton(Button):
    def render(self) -> str:
        return "Light Button"


class DarkButton(Button):
    def render(self) -> str:
        return "Dark Button"


class Header:
    def __init__(self, darkmode: bool) -> None:
        if darkmode:
            self.label: Label = DarkLabel()
        else:
            self.label = LightLabel()

    def display(self) -> str:
        return f"Header: {self.label.render()}"


class MainView:
    def __init__(self, darkmode: bool) -> None:
        if darkmode:
            self.label: Label = DarkLabel()
            self.button: Button = DarkButton()
        else:
            self.label = LightLabel()
            self.button = LightButton()

    def display(self) -> str:
        return f"MainView: {self.label.render()}, {self.button.render()}"


class Bootstrap:
    def __init__(self, darkmode: bool) -> None:
        self.darkmode = darkmode
        self.header = Header(darkmode)
        self.main_view = MainView(darkmode)

    def run(self) -> None:
        print(self.header.display())
        print(self.main_view.display())


if __name__ == "__main__":
    print("Light mode:")
    light_app = Bootstrap(darkmode=False)
    light_app.run()

    print("\nDark mode:")
    dark_app = Bootstrap(darkmode=True)
    dark_app.run()
