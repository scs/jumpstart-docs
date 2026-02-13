from abc import ABC, abstractmethod
from enum import StrEnum, auto


class ClothesWasher(ABC):
    @abstractmethod
    def wash(self, item: str) -> None:
        pass


class DishWasher(ABC):
    @abstractmethod
    def wash(self, item: str) -> None:
        pass


class MachineClothesWasher(ClothesWasher):
    def wash(self, item: str) -> None:
        print(f"Machine washing clothes: {item}.")


class MachineDishWasher(DishWasher):
    def wash(self, item: str) -> None:
        print(f"Machine washing dishes: {item}.")


class HandClothesWasher(ClothesWasher):
    def wash(self, item: str) -> None:
        print(f"Hand washing clothes: {item}.")


class HandDishWasher(DishWasher):
    def wash(self, item: str) -> None:
        print(f"Hand washing dishes: {item}.")


class WasherFactory(ABC):
    @abstractmethod
    def create_clothes_washer(self) -> ClothesWasher:
        pass

    @abstractmethod
    def create_dish_washer(self) -> DishWasher:
        pass


class MachineWasherFactory(WasherFactory):
    def create_clothes_washer(self) -> ClothesWasher:
        return MachineClothesWasher()

    def create_dish_washer(self) -> DishWasher:
        return MachineDishWasher()


class HandWasherFactory(WasherFactory):
    def create_clothes_washer(self) -> ClothesWasher:
        return HandClothesWasher()

    def create_dish_washer(self) -> DishWasher:
        return HandDishWasher()


class ThingsToWash(StrEnum):
    PANTS = auto()
    SHIRT = auto()
    SPOON = auto()
    PLATE = auto()
    FORK = auto()


def wash_things(factory: WasherFactory, things: list[ThingsToWash]) -> None:
    clothes_washer = factory.create_clothes_washer()
    dish_washer = factory.create_dish_washer()

    for thing in things:
        if thing in (ThingsToWash.PANTS, ThingsToWash.SHIRT):
            clothes_washer.wash(str(thing))
        elif thing in (ThingsToWash.SPOON, ThingsToWash.PLATE, ThingsToWash.FORK):
            dish_washer.wash(str(thing))


def get_washer_factory(can_wash_fragile_things: bool) -> WasherFactory:
    if can_wash_fragile_things:
        return HandWasherFactory()
    return MachineWasherFactory()


def main() -> None:
    factory = get_washer_factory(can_wash_fragile_things=True)

    wash_things(
        factory,
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
