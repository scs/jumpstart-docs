cmake_minimum_required(VERSION 3.5)

# project settings
project(bit_fields)
set(BF_TARGET bit-fields)

# search all relevant files
file(GLOB_RECURSE BF_SRC_MAIN "src/main.c*")
file(GLOB_RECURSE BF_SRC_ALL "src/*.c*")
file(GLOB_RECURSE BF_TEST_ALL "test/*.c*")
list(REMOVE_ITEM BF_SRC_ALL ${BF_SRC_MAIN})

# internal library target
set(BF_TARGET_LIB ${BF_TARGET}-lib)
add_library(${BF_TARGET_LIB} STATIC ${BF_SRC_ALL})
target_include_directories(${BF_TARGET_LIB} PUBLIC "libs" "src")

# main app target
set(BF_TARGET_APP ${BF_TARGET}-app)
add_executable(${BF_TARGET_APP} ${BF_SRC_MAIN})
target_link_libraries(${BF_TARGET_APP} PRIVATE ${BF_TARGET_LIB})

# unit test target
set(BF_TARGET_TEST ${BF_TARGET}-test)
enable_testing()
add_executable(${BF_TARGET_TEST} ${BF_TEST_ALL})
add_test(unittest ${BF_TARGET_TEST})
target_link_libraries(${BF_TARGET_TEST} PRIVATE ${BF_TARGET_LIB})
