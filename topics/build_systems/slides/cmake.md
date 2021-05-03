CMake
=====


CMake
-----

* Definiert *High-Level*-Abhängigkeiten
* Generiert *rohes* Build-System
* rohes Build-System *überwacht* Änderungen
* Modernes CMake ab *3.x*

### Modern CMake

* [introduction_modern_cmake]
* [effective_modern_cmake]
* [talk_pfeifer_effective_cmake]


CMake: `example`-Projekt
------------------------

~~~ {.cmake .numberLines}
cmake_minimum_required(VERSION 3.10)
project(example)

# collect sources
file(GLOB_RECURSE src src/*.cpp)

# main app target
add_executable(example_app ${src})
target_link_libraries(example_app
    PRIVATE pthread)

# define compile flags (warnings are fatal / add more checks)
set(cxxflags -Werror -Wall -Wextra -Wconversion -Wpedantic)

# set compiler flags
target_compile_options(example_app PRIVATE ${cxxflags})
~~~


Tool Integration
----------------

* CMake erlaubt *Custom-Commands* und *Custom-Targets*
* Ähnlich wie *Compile*-Schritt
* Kann zu *Abbruch* des Builds führen
* *Abhängigkeiten* in beide Richtungen möglich

~~~ {.cmake .numberLines}
add_custom_command(
    OUTPUT main.cpp.timestamp
    COMMAND cpplint.py main.cpp
        && touch main.cpp.timestamp
    DEPENDS main.cpp
    COMMENT "Linting with cpplint: main.cpp")

add_custom_target(example_app-cpplint
    DEPENDS main.cpp.timestamp
~~~


CMake Targets
-------------

* *Vereinen* Kommandos
* *Abhängigkeiten* untereinander
* Sichtbar oder intern
* *Default* Target (je nach Build-System)


CMake Targets
-------------

\centering
![Build-Targets example-Projekt](images/build_targets_example_project.pdf){height=95%}


Kompilierung ausführen
----------------------

* *Out-of-Source* Builds
* *Keine Durchmischung* von Quell-Daten und Build-Artefakten

~~~ {.bash .numberLines}
cd example
mkdir build
cd build
cmake ..
make -j4
~~~

* IDEs wie CLion *abstrahieren* diesen Prozess
* erzeugen z.B: *`cmake-build-debug`*


Build-Systeme sind produktiver Code
-----------------------------------

* Das Build-System ist *Teil* des Produktes
* Muss *versioniert* werden
* Kann ebenfalls mit Funktionen etc. strukturiert werden:
