#include <catch/catch.hpp>

#include <memory>
#include <type_traits>

#include "utils/bit_field_set.hpp"

TEST_CASE("BitFieldSet", "[bit_field_set]") {

	SECTION("Basic assignments and reads") {
		BitFieldSet<uint8_t> bfs(5);
		REQUIRE(bfs == 5);

		bfs = 0x23;
		REQUIRE(bfs == 0x23);

		BitFieldSet<uint8_t> second_bfs(0xFF);
		bfs = second_bfs;
		REQUIRE(bfs == 0xFF);
	}

	SECTION("Access too big set") {
		BitFieldSet<uint8_t> bfs(0x70);
		REQUIRE_THROWS_AS(bfs.access<9>(0), std::out_of_range);
	}

	SECTION("Access set out of range") {
		BitFieldSet<uint8_t> bfs(0x70);
		REQUIRE_THROWS_AS(bfs.access<1>(8), std::out_of_range);
	}

	SECTION("All access methods") {
		BitFieldSet<uint8_t> bfs(2);

		SECTION("implicit cast") {
			REQUIRE(bfs.access<3>(1) == 1);
		}

		SECTION("explicit get") {
			REQUIRE(bfs.access<3>(1).get() == 1);
		}

		SECTION("implicit assign") {
			bfs.access<3>(1) = 2;
			REQUIRE(bfs.access<3>(1) == 2);
			REQUIRE(bfs == 4);
		}

		SECTION("explicit set") {
			bfs.access<3>(1).set(7);
			REQUIRE(bfs.access<3>(1) == 7);
			REQUIRE(bfs == 14);
		}
	}

	SECTION("Access const methods") {
		const BitFieldSet<uint8_t> bfs1(1);
		const auto field1 = bfs1.access<1>(0);
		REQUIRE(field1.any());
	}

	SECTION("Selected bit field types") {
		BitFieldSet<int64_t> bfs(1);
		REQUIRE(std::is_same<bool, decltype(bfs.access<1>(0).get())>::value);
		REQUIRE(std::is_same<uint8_t, decltype(bfs.access<2>(0).get())>::value);
		REQUIRE(std::is_same<uint8_t, decltype(bfs.access<8>(0).get())>::value);
		REQUIRE(std::is_same<uint16_t, decltype(bfs.access<9>(0).get())>::value);
		REQUIRE(std::is_same<uint16_t, decltype(bfs.access<16>(0).get())>::value);
		REQUIRE(std::is_same<uint32_t, decltype(bfs.access<17>(0).get())>::value);
		REQUIRE(std::is_same<uint32_t, decltype(bfs.access<32>(0).get())>::value);
		REQUIRE(std::is_same<uint64_t, decltype(bfs.access<33>(0).get())>::value);
		REQUIRE(std::is_same<uint64_t, decltype(bfs.access<64>(0).get())>::value);
	}

	SECTION("Access whole set") {
		BitFieldSet<uint8_t> bfs(43);
		REQUIRE(bfs.access<8>(0) == 43);
		bfs.access<8>(0) = 0xFF;
		REQUIRE(bfs.access<8>(0) == 0xFF);
	}

	SECTION("Access one bit at index 0") {
		BitFieldSet<uint8_t> bfs(1);
		REQUIRE(bfs.access<1>(0));
		bfs.access<1>(0) = false;
		REQUIRE_FALSE(bfs.access<1>(0));
		REQUIRE(bfs == 0);
	}

	SECTION("Access one bit somewhere") {
		BitFieldSet<uint8_t> bfs(16);
		REQUIRE(bfs.access<1>(4));
		bfs.access<1>(4) = false;
		REQUIRE_FALSE(bfs.access<1>(4));
		REQUIRE(bfs == 0);
	}

	SECTION("Access 5 bits") {
		BitFieldSet<uint8_t> bfs(0b01111101);
		REQUIRE(bfs.access<5>(2) == 0b00011111);
		bfs.access<5>(2) = 0b00010101;
		REQUIRE(bfs.access<5>(2) == 0b00010101);
		REQUIRE(bfs == 0b01010101);
	}

	SECTION("Access 12 bits") {
		BitFieldSet<uint16_t> bfs(0b1100000000000011);
		REQUIRE(bfs.access<12>(2) == 0);
		bfs.access<12>(2) = 0b0000110011001100;
		REQUIRE(bfs.access<12>(2) == 0b0000110011001100);
		REQUIRE(bfs == 0b1111001100110011);
	}

	SECTION("Flip 5 bits") {
		BitFieldSet<uint8_t> bfs(0b10101011);
		auto field = bfs.access<5>(2);
		REQUIRE(field == 0b00001010);

		SECTION("test inverse of whole result of get()") {
			REQUIRE(static_cast<uint8_t>(~field.get()) == 0b11110101);
		}
		SECTION("test inverse of only bits in the field") {
			REQUIRE((~field) == 0b00010101);
		}

		field.flip();
		REQUIRE(field == 0b00010101);
		REQUIRE(bfs == 0b11010111);
	}

	SECTION("Clear all 5 bits") {
		BitFieldSet<uint8_t> bfs(0b10101011);
		auto field = bfs.access<5>(2);
		REQUIRE(field == 0b00001010);
		bfs.access<5>(2).reset_all();
		REQUIRE(field == 0b00000000);
		REQUIRE(bfs == 0b10000011);
	}

	SECTION("Set all 5 bits") {
		BitFieldSet<uint8_t> bfs(0b10101011);
		auto field = bfs.access<5>(2);
		REQUIRE(field == 0b00001010);
		bfs.access<5>(2).set_all();
		REQUIRE(field == 0b00011111);
		REQUIRE(bfs == 0b11111111);
	}

	SECTION("Check size of BitField") {
		BitFieldSet<uint8_t> bfs(0b10101011);
		auto field = bfs.access<5>(2);
		REQUIRE(field.size() == 5);
	}

	SECTION("Check the number of bits that are set") {

		SECTION("Various bits in set are 1") {
			const BitFieldSet<uint8_t> bfs1(0b10101011);
			const auto field1 = bfs1.access<5>(2);
			REQUIRE(field1.any() == true);
			REQUIRE(field1.all() == false);
			REQUIRE(field1.none() == false);
		}
		
		SECTION("None bits in set are 1") {
			const BitFieldSet<uint8_t> bfs2(0b10000011);
			const auto field2 = bfs2.access<5>(2);
			REQUIRE(field2.any() == false);
			REQUIRE(field2.all() == false);
			REQUIRE(field2.none() == true);
		}

		SECTION("All bits in set are 1") {
			const BitFieldSet<uint8_t> bfs3(0b01111100);
			const auto field3 = bfs3.access<5>(2);
			REQUIRE(field3.any() == true);
			REQUIRE(field3.all() == true);
			REQUIRE(field3.none() == false);
		}
	}

	SECTION("Bit counting") {
		BitFieldSet<uint8_t> bfs(0b00000000);
		const auto field = bfs.access<5>(2);

		SECTION("No bit set") {
			bfs = 0b00000000;
			REQUIRE(field.bit_count() == 0);
			bfs = 0b10000011;
			REQUIRE(field.bit_count() == 0);
		}

		SECTION("One bit set") {
			bfs = 0b00010000;
			REQUIRE(field.bit_count() == 1);
			bfs = 0b10001011;
			REQUIRE(field.bit_count() == 1);
		}

		SECTION("All bits set") {
			bfs = 0b01111100;
			REQUIRE(field.bit_count() == 5);
			bfs = 0b11111111;
			REQUIRE(field.bit_count() == 5);
		}
	}
}
