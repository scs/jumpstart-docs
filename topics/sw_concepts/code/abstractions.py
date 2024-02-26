#!/usr/bin/env python
import errno
import math
import os
import sys
import time
from optparse import OptionParser

# Censored hardware prototype2 and series (tsl2550) value range: 0 - 4015, daylight: 544
# Censored hardware v3 (opt3001) value range: 0 - 36157, daylight: 290
INPUT_MIN = 0
INPUT_MAX_TSL2550 = 544
INPUT_MAX_OPT3001 = 290
INPUT_THRESHOLD = 11

# Censored hardware prototype2 (Intel backlight) value range: 0 - 937
# Censored hardware series Device (Intel backlight) value range: 0 - 7500
OUTPUT_MIN_FACTOR = 0.2
OUTPUT_CHANGE_MAX_FACTOR = 0.005

sensor_value_lux_approx_map = [
    0, 1, 2, 3, 4, 5, 6, 7,
    8, 9, 10, 11, 12, 13, 14, 15,
    16, 18, 20, 22, 24, 26, 28, 30,
    32, 34, 36, 38, 40, 42, 44, 46,
    49, 53, 57, 61, 65, 69, 73, 77,
    81, 85, 89, 93, 97, 101, 105, 109,
    115, 123, 131, 139, 147, 155, 163, 171,
    179, 187, 195, 203, 211, 219, 227, 235,
    247, 263, 279, 295, 311, 327, 343, 359,
    375, 391, 407, 423, 439, 455, 471, 487,
    511, 543, 575, 607, 639, 671, 703, 735,
    767, 799, 831, 863, 895, 927, 959, 991,
    1039, 1103, 1167, 1231, 1295, 1359, 1423, 1487,
    1551, 1615, 1679, 1743, 1807, 1871, 1935, 1999,
    2095, 2223, 2351, 2479, 2607, 2735, 2863, 2991,
    3119, 3247, 3375, 3503, 3631, 3759, 3887, 4015
]


def main():
    input_max = INPUT_MAX_TSL2550
    output_last_value = None

    parser = OptionParser()
    _, args = parser.parse_args()

    if len(args) != 3:
        raise Exception("wrong number of arguments")

    path = args[0]
    input_path = args[1]
    output_path = args[2]
    opt3001 = "in_illuminance_input" in os.readlink(input_path)

    input_last_value = INPUT_MIN - INPUT_THRESHOLD
    with open(path) as f:
        output_max = int(f.readline().strip())
    output_min = int(math.ceil(output_max * OUTPUT_MIN_FACTOR))
    output_change_max = int(math.ceil(output_max * OUTPUT_CHANGE_MAX_FACTOR))

    while True:
        try:
            if opt3001:
                try:
                    with open(input_path) as f1:
                        input_value = float(f1.readline().strip())
                except IOError:
                    # This driver generates a read error if very little light is present
                    input_value = INPUT_MIN
            else:
                with open(input_path) as f:
                    input_value = int(f.readline().strip())
                if 0 <= input_value < len(sensor_value_lux_approx_map):
                    input_value = sensor_value_lux_approx_map[input_value]
                else:
                    input_value = input_last_value

            input_value = min(input_value, input_max)

            # Ignore small input value changes
            if abs(input_value - input_last_value) < INPUT_THRESHOLD:
                input_value = input_last_value

            a = (input_value - INPUT_MIN) / float(input_max - INPUT_MIN)
            value1 = int(a * float(output_max - output_min) + output_min)
            output_value = min(value1, output_max)

            if output_last_value is None:
                output_value = output_value
            elif output_value >= output_last_value:
                output_value = min(output_value, output_last_value + output_change_max)
            else:
                output_value = max(output_value, output_last_value - output_change_max)
            dimmed_value = output_value

            if output_value != output_last_value:
                print(f"input: %4i (%4.1f%%), output: %4i (%4.1f%%), dimmed: %4i (%4.1f%%)",
                      input_value, 100 * (input_value - INPUT_MIN) / float(input_max - INPUT_MIN),
                      output_value, 100 * (output_value - output_min) / float(output_max - output_min),
                      dimmed_value, 100 * (dimmed_value - output_min) / float(output_max - output_min))
                sys.stdout.flush()

            with open(output_path, "w") as f:
                output_last_value = output_value
                f.write(str(output_value))

            input_last_value = input_value
        except IOError as e:
            # Ignore EGAIN errors which may happen if the I2C bus is busy
            if e.errno != errno.EAGAIN:
                raise e

        time.sleep(0.01)

    return 0


if __name__ == "__main__":
    sys.exit(main())
