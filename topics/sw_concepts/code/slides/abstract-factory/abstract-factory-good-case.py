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


class ComponentFactory(ABC):
    @abstractmethod
    def create_label(self) -> Label:
        pass

    @abstractmethod
    def create_button(self) -> Button:
        pass


class LightComponentFactory(ComponentFactory):
    def create_label(self) -> Label:
        return LightLabel()

    def create_button(self) -> Button:
        return LightButton()


class DarkComponentFactory(ComponentFactory):
    def create_label(self) -> Label:
        return DarkLabel()

    def create_button(self) -> Button:
        return DarkButton()


class Header:
    def __init__(self, factory: ComponentFactory) -> None:
        self.label: Label = factory.create_label()

    def display(self) -> str:
        return f"Header: {self.label.render()}"


class MainView:
    def __init__(self, factory: ComponentFactory) -> None:
        self.label: Label = factory.create_label()
        self.button: Button = factory.create_button()

    def display(self) -> str:
        return f"MainView: {self.label.render()}, {self.button.render()}"


class Bootstrap:
    def __init__(self, darkmode: bool) -> None:
        if darkmode:
            factory: ComponentFactory = DarkComponentFactory()
        else:
            factory = LightComponentFactory()

        self.header = Header(factory)
        self.main_view = MainView(factory)

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
