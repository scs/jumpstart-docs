cmake_minimum_required(VERSION 3.10)

# project settings
project(arbeitsblatt_build_system)

# main app target
add_executable(arbeitsblatt_build_system_app
        src/main.cpp
        src/student.cpp
        src/module.cpp
        )
target_include_directories(arbeitsblatt_build_system_app
        PRIVATE src)
target_compile_options(arbeitsblatt_build_system_app PRIVATE ${cxxflags})

# unit unittest target
add_executable(arbeitsblatt_build_system_test
        src/student.cpp
        src/module.cpp
        unittest/main.cpp
        unittest/student_test.cpp
        )
target_include_directories(arbeitsblatt_build_system_test
        PRIVATE src)
target_compile_options(arbeitsblatt_build_system_test PRIVATE ${cxxflags})
