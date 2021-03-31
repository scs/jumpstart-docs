cmake_minimum_required(VERSION 3.10)

project(jumpstart-docs NONE)

list(APPEND CMAKE_MODULE_PATH ${CMAKE_SOURCE_DIR}/cmake)

option(JS_WITH_CODE "Enable the compilation of code examples" ON)

add_subdirectory(topics)
