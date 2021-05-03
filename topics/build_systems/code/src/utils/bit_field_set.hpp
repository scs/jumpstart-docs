#pragma once

#include <cassert>

#include <exception>
#include <type_traits>

#include "utils/type_helper.hpp"

/** @defgroup BitFieldValueType Template type that returns the integer type that corresponds to the provided number of bits or bool if only one bit.
 * @{ */
template<size_t NumberOfBits>
struct BitFieldValueType : return_<typename IntegerForBits<NumberOfBits>::type> {};

template<>
struct BitFieldValueType<1> : return_<bool> {};
/** @}*/


/**
 * Handle class for a bit field of variable bit length to the memory of the real variable.
 *
 * Usage:
 * @code{.cpp}
 *  BitFieldSet<uint8_t> bfs(0x23);
 *  bfs.access<5>(2) = 0x10;					// direct write
 *  auto field = bfs.access<5>(2);				// create handle that points to the bit field.
 *  uint8_t read = bfs.access<5>(2).set_all();	// set all bits and also read value.
 * @endcode
 *
 * @tparam Length The number of bits represented by this handle class.
 * @tparam BaseMemory The type of the real variable.
 */
template <size_t Length, typename BaseMemory>
class BitField {
	typedef typename std::remove_const<BaseMemory>::type base_type;
	static const size_t kLength = Length;

public:
	/// The type that can represent the number of bits represented by this handle class.
	typedef typename BitFieldValueType<Length>::type value_type;

	/**
	 * Construct a handle instance that can access the defined bits in the real memory of the base variable.
	 * @param base The real memory where the bit field resists.
	 * @param pos The 0-based position of the bit field.
	 */
	BitField(BaseMemory* base, size_t pos)
		: base_(base)
		, pos_(pos)
		, base_mask_(0)
		, mask_(0)
		, clear_mask_(0)

	{
		// init masks
		for (size_t i = 0; i < kLength; ++i) {
			base_mask_ = (base_mask_ << 1) + 1;
		}
		mask_ = base_mask_ << pos_;
		clear_mask_ = ~mask_;
	}

	/**
	 * Set a new value to the corresponding bits of a bit field.
	 * @param value The 0 bit based value of the bit field to set.
	 */
	void set(const value_type& value) {
		const base_type shifted_value = static_cast<base_type>(value) << pos_;
		const base_type masked_value = shifted_value & mask_;
		*base_ &= clear_mask_;
		*base_ |= masked_value;
	}

	/// @copydoc BitField::set()
	BitField& operator=(const value_type& value) {
		set(value);
		return *this;
	}

	/// Return the value of the bit field based at bit 0.
	value_type get() const {
		const base_type masked_value = *base_ & mask_;
		const base_type shifted_value = masked_value >> pos_;
		return static_cast<value_type>(shifted_value);
	}

	/// @copydoc BitField::get()
	operator value_type() const {
		return get();
	}

	/// Return the inverse of the bit field value based at bit 0. This is the same as if we
	/// use flip and then access the field. Only the bits contained in the field are inversed.
	value_type operator~() const {
		return get() ^ base_mask_;
	}

	/// Invert all bits managed by the bit field.
	BitField& flip() {
		*base_ ^= mask_;
		return *this;
	}

	/// Set all bits of bit field to 1.
	BitField& set_all() {
		*base_ |= mask_;
		return *this;
	}

	/// Set all bits of bit field to 0.
	BitField& reset_all() {
		*base_ &= clear_mask_;
		return *this;
	}

	/// Return number of bits managed by this instance.
	size_t size() const {
		return kLength;
	}

	/// Return number of one bits set in this field.
	size_t bit_count() const {
		auto tmp = get();
		size_t count = 0;
		for (size_t i = kLength; i > 0; --i) {
			if (tmp & 0x01) {
				count += 1;
			}
			tmp >>= 1;
		}
		return count;
	}

	/// Test if any bit of bit field is set.
	bool any() const {
		return (*base_ & mask_) != 0;
	}

	/// Test if all bits of bit field are set.
	bool all() const {
		return (*base_ & mask_) == mask_;
	}

	/// Test if no bits of bit field are set.
	bool none() const {
		return any() == false;
	}

private:
	BaseMemory* const base_;
	size_t pos_;
	base_type base_mask_; 	//!< Bit mask at position 0.
	base_type mask_;		//!< Bit mask at defined position.
	base_type clear_mask_;	//!< Inversed bit mask at defined position.
};


/**
 * An enhanced variant of std::bitset that allows not only the access to single bits but also direct access to
 * bit fields that have various bit length. The access type is automatically deduced by the number of bits of the field.
 * It is always the smallest possible unsigned integer or bool type choosen.
 *
 * @tparam Memory The type of the managed value. Useful only with unsigned integral values other then bool.
 */
template <typename Memory>
class BitFieldSet {
	static_assert(std::is_integral<Memory>::value && !std::is_same<bool, Memory>::value, "only integral types are allowed");

	typedef Memory value_type;

public:
	explicit BitFieldSet(const value_type& value)
		: value_(value)
	{
	}

	/// Get a handle that can access the referenced bits. See BitField.
	template<size_t Length>
	BitField<Length, value_type> access(size_t pos) {
		check<Length>(pos);
		return BitField<Length, value_type>(&value_, pos);
	}

	/// Get a representation of the referenced bits. See BitField.
	template<size_t Length>
	const BitField<Length, const value_type> access(size_t pos) const {
		check<Length>(pos);
		return BitField<Length, const value_type>(&value_, pos);
	}

	/// Set new underlying value.
	BitFieldSet& operator=(const BitFieldSet& set) {
		value_ = set.value_;
		return *this;
	}

	/// Set new underlying value.
	BitFieldSet& operator=(const value_type& value) {
		value_ = value;
		return *this;
	}

	/// Return underlying value.
	operator value_type() const {
		return value_;
	}

private:
	value_type value_;

	/// Check if referenced bits can lie in the underlying memory. If not throw.
	template<size_t Length>
	void check(size_t pos) const {
		if (pos + Length > bitcountoftype<value_type>::value) {
			throw std::out_of_range("bits out of the variable are addressed");
		}
	}
};
