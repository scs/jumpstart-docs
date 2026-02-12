from abc import ABC, abstractmethod


class Washer(ABC):
    @abstractmethod
    def wash_clothes(self) -> None:
        pass

    @abstractmethod
    def wash_dishes(self) -> None:
        pass


class HandWasher(Washer):
    def wash_clothes(self) -> None:
        print("Hand washing clothes in basin")

    def wash_dishes(self) -> None:
        print("Hand washing dishes in sink")


class DishWashingMachine(Washer):
    def wash_dishes(self) -> None:
        print("Machine washing dishes with hot water and detergent")

    def wash_clothes(self) -> None:
        raise NotImplementedError(
            "DishWashingMachine cannot wash clothes! This method should not exist for this class."
        )


if __name__ == "__main__":
    print("HandWasher (flexible, can do both):")
    hand_washer = HandWasher()
    hand_washer.wash_clothes()
    hand_washer.wash_dishes()

    print("\nDishWashingMachine (specialized):")
    dish_machine = DishWashingMachine()
    dish_machine.wash_dishes()

    print("\nTrying to wash clothes with dish machine:")
    try:
        dish_machine.wash_clothes()
    except NotImplementedError as e:
        print(f"Error: {e}")
