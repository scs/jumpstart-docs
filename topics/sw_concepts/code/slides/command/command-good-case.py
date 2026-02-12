from abc import ABC, abstractmethod


class ThemeObservable:
    def __init__(self) -> None:
        self._is_dark_theme: bool = False

    def set_is_dark_theme(self, value: bool) -> None:
        self._is_dark_theme = value
        theme_name = "Dark" if value else "Light"
        print(f"[Theme] Theme changed to {theme_name}")

    def is_dark_theme(self) -> bool:
        return self._is_dark_theme


class Command(ABC):
    @abstractmethod
    def execute(self) -> None:
        pass


class SetDarkThemeCommand(Command):
    def __init__(self, theme: ThemeObservable) -> None:
        self.theme = theme

    def execute(self) -> None:
        print("Executing: Set Dark Theme")
        self.theme.set_is_dark_theme(True)


class SetLightThemeCommand(Command):
    def __init__(self, theme: ThemeObservable) -> None:
        self.theme = theme

    def execute(self) -> None:
        print("Executing: Set Light Theme")
        self.theme.set_is_dark_theme(False)


class DarkButton:
    def __init__(self) -> None:
        self._command: Command | None = None

    def set_command(self, command: Command) -> None:
        self._command = command

    def on_action(self) -> None:
        if self._command:
            print("Dark button clicked")
            self._command.execute()
        else:
            print("Dark button has no command set")


class LightButton:
    def __init__(self) -> None:
        self._command: Command | None = None

    def set_command(self, command: Command) -> None:
        self._command = command

    def on_action(self) -> None:
        if self._command:
            print("Light button clicked")
            self._command.execute()
        else:
            print("Light button has no command set")


if __name__ == "__main__":
    theme = ThemeObservable()

    dark_button = DarkButton()
    light_button = LightButton()

    dark_button.set_command(SetDarkThemeCommand(theme))
    light_button.set_command(SetLightThemeCommand(theme))

    print("Starting in light mode:")
    dark_button.on_action()

    print("\nNow in dark mode:")
    light_button.on_action()

    print("\nBack to dark mode:")
    dark_button.on_action()
