import math


def main() -> None:
    # --- Order 1: Alice buys 3 Widgets and 1 Gadget ---
    o1_total = 0.0
    o1_total += 3 * 9.99
    o1_total += 1 * 24.99
    if o1_total > 50:
        o1_total *= 0.9
    o1_tax = o1_total * 0.19
    o1_total += o1_tax
    o1_total = math.floor(o1_total * 100) / 100
    print(f"Order 1 (Alice): {o1_total} CHF")

    # --- Order 2: Bob buys 5 Widgets ---
    o2_total = 0.0
    o2_total += 5 * 9.99
    if o2_total > 50:
        o2_total *= 0.9
    o2_tax = o2_total * 0.19
    o2_total += o2_tax
    o2_total = math.floor(o2_total * 100) / 100
    print(f"Order 2 (Bob): {o2_total} CHF")

    # --- Order 3: Charlie buys 2 Gadgets and 4 Widgets, pays in USD ---
    o3_total = 0.0
    o3_total += 4 * 9.99
    o3_total += 2 * 24.99
    if o3_total > 50:
        o3_total *= 0.9
    o3_tax = o3_total * 0.19
    o3_total += o3_tax
    o3_total = math.floor(o3_total * 100) / 100
    o3_total_usd = o3_total * 1.08
    o3_total_usd = math.floor(o3_total_usd * 100) / 100
    print(f"Order 3 (Charlie): {o3_total_usd} USD")

    # --- Print receipt for Alice ---
    print("=" * 40)
    print("RECEIPT")
    print("=" * 40)
    r1_total = 0.0
    r1_total += 3 * 9.99
    r1_total += 1 * 24.99
    print(f"  3x Widget @ 9.99 = {3 * 9.99}")
    print(f"  1x Gadget @ 24.99 = {1 * 24.99}")
    if r1_total > 50:
        print(f"  Discount (10%): -{r1_total * 0.1:.2f}")
        r1_total *= 0.9
    r1_tax = r1_total * 0.19
    r1_total += r1_tax
    r1_total = math.floor(r1_total * 100) / 100
    print(f"  Tax (19%): {r1_tax:.2f}")
    print(f"  TOTAL: {r1_total} CHF")
    print("=" * 40)


if __name__ == "__main__":
    main()
