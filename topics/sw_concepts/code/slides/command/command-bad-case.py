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


class DarkThemeButton(ABC):
    def __init__(self, theme: ThemeObservable) -> None:
        self.theme = theme

    @abstractmethod
    def on_action(self) -> None:
        pass


class DarkDarkThemeButton(DarkThemeButton):
    def on_action(self) -> None:
        print("Dark theme button clicked (in dark UI)")
        self.theme.set_is_dark_theme(True)


class LightDarkThemeButton(DarkThemeButton):
    def on_action(self) -> None:
        print("Dark theme button clicked (in light UI)")
        self.theme.set_is_dark_theme(True)


class LightThemeButton(ABC):
    def __init__(self, theme: ThemeObservable) -> None:
        self.theme = theme

    @abstractmethod
    def on_action(self) -> None:
        pass


class DarkLightThemeButton(LightThemeButton):
    def on_action(self) -> None:
        print("Light theme button clicked (in dark UI)")
        self.theme.set_is_dark_theme(False)


class LightLightThemeButton(LightThemeButton):
    def on_action(self) -> None:
        print("Light theme button clicked (in light UI)")
        self.theme.set_is_dark_theme(False)


if __name__ == "__main__":
    theme = ThemeObservable()

    print("Starting in light mode:")
    dark_btn = LightDarkThemeButton(theme)  # Dark button for light UI
    light_btn = LightLightThemeButton(theme)  # Light button for light UI

    dark_btn.on_action()

    print("\nNow in dark mode, need different button instances:")
    dark_btn_2 = DarkDarkThemeButton(theme)  # Dark button for dark UI
    light_btn_2 = DarkLightThemeButton(theme)  # Light button for dark UI

    light_btn_2.on_action()
