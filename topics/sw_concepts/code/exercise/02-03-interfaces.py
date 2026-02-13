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
    # TODO
    pass


class HandWasher:
    # TODO
    pass


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


def main() -> None:
    machine_washer = MachineWasher()
    hand_washer = HandWasher()

    wash_things(machine_washer, [ThingsToWash.PLATE, ThingsToWash.SHIRT, ThingsToWash.SPOON])
    wash_things(hand_washer, [ThingsToWash.PANTS, ThingsToWash.FORK])


if __name__ == "__main__":
    main()
