from abc import ABC, abstractmethod


class ClothesWasher(ABC):
    @abstractmethod
    def wash_clothes(self) -> None:
        pass


class DishWasher(ABC):
    @abstractmethod
    def wash_dishes(self) -> None:
        pass


class HandWasher(ClothesWasher, DishWasher):
    def wash_clothes(self) -> None:
        print("Hand washing clothes in basin")

    def wash_dishes(self) -> None:
        print("Hand washing dishes in sink")


class DishWashingMachine(DishWasher):
    def wash_dishes(self) -> None:
        print("Machine washing dishes with hot water and detergent")


def clean_dishes(washer: DishWasher) -> None:
    washer.wash_dishes()


def clean_clothes(washer: ClothesWasher) -> None:
    washer.wash_clothes()


if __name__ == "__main__":
    print("HandWasher (flexible, implements both interfaces):")
    hand_washer = HandWasher()
    hand_washer.wash_clothes()
    hand_washer.wash_dishes()

    print("\nDishWashingMachine (specialized, implements only DishWasher):")
    dish_machine = DishWashingMachine()
    dish_machine.wash_dishes()

    print("\nUsing polymorphism with DishWasher interface:")
    clean_dishes(hand_washer)
    clean_dishes(dish_machine)

    print("\nUsing ClothesWasher interface:")
    clean_clothes(hand_washer)
    # dish_machine cannot be passed here - it doesn't implement ClothesWasher!
