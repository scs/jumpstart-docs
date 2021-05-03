#pragma once

#include <cstdint>
#include <climits>

/// Helper to easily add a return value.
template<size_t N>
struct constant {
	enum { value = N };
};

/// Helper to easily add a return type.
template<typename T>
struct return_ {
    typedef T type;
};


/** @defgroup bitcount Template type that returns the number of bits that are needed to store the provided value.
 * This works by recursively divide the result by 2 and count the iterations.
 * @{ */
template<size_t Value>
struct bitcountofvalue : constant<1 + bitcountofvalue<(Value >> 1)>::value> {};

template<>
struct bitcountofvalue<0> : constant<1> {};

template<>
struct bitcountofvalue<1> : constant<1> {};
/** @}*/


/// Template type that returns the number of bits contained in the provided type.
template<typename _Type>
struct bitcountoftype : constant<sizeof(_Type) * CHAR_BIT> {};


/**
 * Template type that returns the number of bytes that are needed to hold the provided number of bits.
 * This works by rounding up.
 */
template<size_t NumberOfBits>
struct bytecount : constant<((NumberOfBits + CHAR_BIT - 1) / CHAR_BIT)> {};


/** @defgroup bytetype Template type that returns the integer type that corresponds to the provided number of bytes.
 * @{ */
template<size_t _NumberOfBytes>
struct bytetype : return_<uint64_t> {
	static_assert(_NumberOfBytes <= 8, "there is no type that can hold more then 8 bytes");
};

template<>
struct bytetype<4> : return_<uint32_t> {};

template<>
struct bytetype<3> : return_<uint32_t> {};

template<>
struct bytetype<2> : return_<uint16_t> {};

template<>
struct bytetype<1> : return_<uint8_t> {};
/** @}*/


/// Template type that returns the integer type that can hold the provided number of bits.
template<size_t NumberOfBits>
struct IntegerForBits : bytetype<bytecount<NumberOfBits>::value> {};
