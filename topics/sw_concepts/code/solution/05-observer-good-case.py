from abc import ABC, abstractmethod


class ThemeObserver(ABC):
    @abstractmethod
    def on_theme_changed(self, is_dark_theme: bool) -> None:
        pass


class ThemeObservable:
    def __init__(self) -> None:
        self._is_dark_theme: bool = False
        self._observers: list[ThemeObserver] = []

    def add_observer(self, observer: ThemeObserver) -> None:
        if observer not in self._observers:
            self._observers.append(observer)

    def remove_observer(self, observer: ThemeObserver) -> None:
        if observer in self._observers:
            self._observers.remove(observer)

    def set_is_dark_theme(self, value: bool) -> None:
        if self._is_dark_theme != value:
            self._is_dark_theme = value
            theme_name = "Dark" if value else "Light"
            print(f"[Theme] Theme changed to {theme_name}")
            self._notify_observers()

    def _notify_observers(self) -> None:
        for observer in self._observers:
            observer.on_theme_changed(self._is_dark_theme)


class DarkThemeButton(ThemeObserver):
    def __init__(self, theme_observable: ThemeObservable) -> None:
        self.theme_observable: ThemeObservable = theme_observable
        self.enabled: bool = True

    def on_action(self) -> None:
        if not self.enabled:
            return

        print("Dark theme button clicked")
        self.theme_observable.set_is_dark_theme(True)

    def on_theme_changed(self, is_dark_theme: bool) -> None:
        self.set_enable(not is_dark_theme)

    def set_enable(self, enable: bool) -> None:
        self.enabled = enable
        status = "enabled" if enable else "disabled"
        print(f"  Dark theme button is now {status}")


class LightThemeButton(ThemeObserver):
    def __init__(self, theme_observable: ThemeObservable) -> None:
        self.theme_observable: ThemeObservable = theme_observable
        self.enabled: bool = False

    def on_action(self) -> None:
        if not self.enabled:
            return

        print("Light theme button clicked")
        self.theme_observable.set_is_dark_theme(False)

    def on_theme_changed(self, is_dark_theme: bool) -> None:
        self.set_enable(is_dark_theme)

    def set_enable(self, enable: bool) -> None:
        self.enabled = enable
        status = "enabled" if enable else "disabled"
        print(f"  Light theme button is now {status}")


def main() -> None:
    theme: ThemeObservable = ThemeObservable()

    dark_button: DarkThemeButton = DarkThemeButton(theme)
    light_button: LightThemeButton = LightThemeButton(theme)

    theme.add_observer(dark_button)
    theme.add_observer(light_button)

    # Simulate user interactions
    print("User clicks dark theme button:")
    dark_button.on_action()

    print("\nUser clicks light theme button:")
    light_button.on_action()

    print("\nUser clicks dark theme button again:")
    dark_button.on_action()


if __name__ == "__main__":
    main()
