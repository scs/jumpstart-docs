from abc import ABC, abstractmethod
from enum import StrEnum, auto


class Washer(ABC):
    @abstractmethod
    def wash_clothes(self, item: str):
        pass

    @abstractmethod
    def wash_dishes(self, item: str):
        pass


class MachineWasher:
    def wash_clothes(self, item: str):
        print(f"Machine washing {item}.")

    def wash_dishes(self, item: str):
        print(f"Machine washing {item}.")


class HandWasher:
    def wash_clothes(self, item: str):
        print(f"Hand washing {item}.")

    def wash_dishes(self, item: str):
        print(f"Hand washing {item}.")


class ThingsToWash(StrEnum):
    PANTS = auto()
    SHIRT = auto()
    SPOON = auto()
    PLATE = auto()
    FORK = auto()


def wash_things(washer: Washer, things: list[ThingsToWash]) -> None:
    for thing in things:
        if thing in (ThingsToWash.PANTS, ThingsToWash.SHIRT):
            washer.wash_clothes(str(thing))
        elif thing in (ThingsToWash.SPOON, ThingsToWash.PLATE, ThingsToWash.FORK):
            washer.wash_dishes(str(thing))


def washer_factory(can_wash_fragile_things: bool) -> Washer:
    if can_wash_fragile_things:
        return HandWasher()
    return MachineWasher()


def main() -> None:
    washer = washer_factory(can_wash_fragile_things=True)

    wash_things(
        washer,
        [
            ThingsToWash.PLATE,
            ThingsToWash.SHIRT,
            ThingsToWash.SPOON,
            ThingsToWash.PANTS,
            ThingsToWash.FORK,
        ],
    )


if __name__ == "__main__":
    main()
