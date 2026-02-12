from abc import ABC, abstractmethod


class Bucket:
    def __init__(self, capacity: int) -> None:
        self.capacity = capacity

    def __repr__(self) -> str:
        return f"Bucket(capacity={self.capacity}L)"


class Washer(ABC):
    @abstractmethod
    def wash_clothes(self) -> None:
        pass

    @abstractmethod
    def wash_dishes(self) -> None:
        pass


class HandWasher(Washer):
    def __init__(self, bucket: Bucket) -> None:
        self.bucket = bucket

    def wash_clothes(self) -> None:
        print(f"Washing clothes using {self.bucket}")

    def wash_dishes(self) -> None:
        print(f"Washing dishes using {self.bucket}")


if __name__ == "__main__":
    bucket = Bucket(capacity=10)
    hand_washer = HandWasher(bucket)
    hand_washer.wash_clothes()
    hand_washer.wash_dishes()
