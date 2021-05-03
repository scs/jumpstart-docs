#include <cstdint>

#include <iostream>
#include <string>

#include "utils/type_helper.hpp"
#include "utils/bit_field_set.hpp"

using std::cout;
using std::endl;

template<typename T>
void PrintFieldStats(const T& field) {
	cout << "decimal: " << static_cast<int>(field.get()) << endl;
	cout << field.bit_count() << " bits are set" << endl;
	cout << endl;
}

int main(int argc, char* argv[]) {
	if (argc != 2) {
		cout << "Provide exactly one dezimal integer parameter!" << endl;
		return 1;
	}

	const std::string input_string{argv[1]};
	const int input = stoi(input_string);

	using NumberType = uint8_t;
	BitFieldSet<NumberType> input_bit_field(static_cast<NumberType>(input));

	for (size_t i = 0; i < bitcountoftype<NumberType>::value; ++i) {
		cout << "bit " << i << ": " << static_cast<int>(input_bit_field.access<1>(i)) << endl;
	}
	cout << endl;

	PrintFieldStats(input_bit_field.access<8>(0));
	PrintFieldStats(input_bit_field.access<4>(0));
	PrintFieldStats(input_bit_field.access<4>(4));

	return 0;
}