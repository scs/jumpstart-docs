class Theme:
    def __init__(self) -> None:
        self._is_dark_theme: bool = False

    @property
    def is_dark_theme(self) -> bool:
        return self._is_dark_theme

    @is_dark_theme.setter
    def is_dark_theme(self, value: bool) -> None:
        self._is_dark_theme = value
        theme_name = "Dark" if value else "Light"
        print(f"[Theme] Theme changed to {theme_name}")


class DarkThemeButton:
    def __init__(self, theme: Theme) -> None:
        self.theme: Theme = theme
        self.light_theme_button: LightThemeButton | None = None
        self.enabled: bool = True

    def on_action(self) -> None:
        if not self.enabled:
            return

        print("Dark theme button clicked")
        self.theme.is_dark_theme = True
        self.set_enable(False)
        if self.light_theme_button:
            self.light_theme_button.set_enable(True)

    def set_enable(self, enable: bool) -> None:
        self.enabled = enable
        status = "enabled" if enable else "disabled"
        print(f"  Dark theme button is now {status}")


class LightThemeButton:
    def __init__(self, theme: Theme) -> None:
        self.theme: Theme = theme
        self.dark_theme_button: DarkThemeButton | None = None
        self.enabled: bool = False

    def on_action(self) -> None:
        if not self.enabled:
            return

        print("Light theme button clicked")
        self.theme.is_dark_theme = False
        self.set_enable(False)
        if self.dark_theme_button:
            self.dark_theme_button.set_enable(True)

    def set_enable(self, enable: bool) -> None:
        self.enabled = enable
        status = "enabled" if enable else "disabled"
        print(f"  Light theme button is now {status}")


def main() -> None:
    theme: Theme = Theme()

    dark_button: DarkThemeButton = DarkThemeButton(theme)
    light_button: LightThemeButton = LightThemeButton(theme)

    dark_button.light_theme_button = light_button
    light_button.dark_theme_button = dark_button

    print("User clicks dark theme button:")
    dark_button.on_action()

    print("\nUser clicks light theme button:")
    light_button.on_action()

    print("\nUser clicks dark theme button again:")
    dark_button.on_action()


if __name__ == "__main__":
    main()
