import math
from dataclasses import dataclass

TAX_RATE = 0.19
DISCOUNT_THRESHOLD = 50.0
DISCOUNT_RATE = 0.10

EXCHANGE_RATES: dict[str, float] = {
    "CHF": 1.0,
    "USD": 1.08,
}


@dataclass
class Product:
    name: str
    price: float


@dataclass
class LineItem:
    product: Product
    quantity: int

    @property
    def subtotal(self) -> float:
        return self.quantity * self.product.price


class Order:
    def __init__(self, customer: str, items: list[LineItem], currency: str = "CHF") -> None:
        self.customer = customer
        self.items = items
        self.currency = currency

    @property
    def subtotal(self) -> float:
        return sum(item.subtotal for item in self.items)

    @property
    def discount(self) -> float:
        if self.subtotal > DISCOUNT_THRESHOLD:
            return self.subtotal * DISCOUNT_RATE
        return 0.0

    @property
    def taxable_amount(self) -> float:
        return self.subtotal - self.discount

    @property
    def tax(self) -> float:
        return self.taxable_amount * TAX_RATE

    @property
    def total(self) -> float:
        raw = self.taxable_amount + self.tax
        return _floor_cents(raw * EXCHANGE_RATES[self.currency])


def _floor_cents(value: float) -> float:
    return math.floor(value * 100) / 100


def print_receipt(order: Order) -> None:
    print("=" * 40)
    print("RECEIPT")
    print("=" * 40)
    for item in order.items:
        print(f"  {item.quantity}x {item.product.name} @ {item.product.price} = {item.subtotal}")
    if order.discount > 0:
        print(f"  Discount ({DISCOUNT_RATE:.0%}): -{order.discount:.2f}")
    print(f"  Tax ({TAX_RATE:.0%}): {order.tax:.2f}")
    print(f"  TOTAL: {order.total} {order.currency}")
    print("=" * 40)


def main() -> None:
    widget = Product("Widget", 9.99)
    gadget = Product("Gadget", 24.99)

    orders = [
        Order("Alice", [LineItem(widget, 3), LineItem(gadget, 1)]),
        Order("Bob", [LineItem(widget, 5)]),
        Order("Charlie", [LineItem(widget, 4), LineItem(gadget, 2)], currency="USD"),
    ]

    for i, order in enumerate(orders, start=1):
        print(f"Order {i} ({order.customer}): {order.total} {order.currency}")

    print()
    print_receipt(orders[0])


if __name__ == "__main__":
    main()
