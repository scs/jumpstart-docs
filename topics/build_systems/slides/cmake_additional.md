CMake - Weiterführendes
=======================


Unterstützende Tools
--------------------

* external project management (`ExternalProject_add`)

* library dependency management (`pkg_search_module`, `find_package`)

* package generation (`cpack`)
  * Nur verwenden, wenn kein übergeordnetes System verfügbar ist: Yocto, Conan etc.

* test management (`ctest`)
  * Management von mehreren Test-Applikationen
  * nicht jeden einzelnen Unit-Test in eine eigene Applikation


Modernes CMake
--------------

* ab Version *3.x* (besser *>3.10*)

* in *Targets* denken und nicht in Variablen

* auch Build-System ist Code und soll strukturiert sein: use *functions/macros*
  * Helper für Parameter-Passing/Parsing: *`cmake_parse_arguments`*

* für viele Core-Funktionen gibt es nicht nur die alten *globalen* sondern auch die neuen *target* Varianten

  ~~~ {.cmake .numberLines}
  # old (applies to global scope)
  include_directories(include_folders)

  # new (applies to TARGET)
  target_include_directories(TARGET include_folders)
  ~~~


Modernes CMake
--------------

so viele *Target*-Features nutzen wie möglich (Reduktion von Variablen)

~~~ {.cmake .numberLines}
# old (uses variables)
pkg_check_modules(testing REQUIRED
    gtest
    gmock)
target_link_libraries(my_unittests
    testing_LIBRARIES)
target_include_directories(my_unittests
    testing_INCLUDE_DIRS)
...

# new (uses imported target -> contains all needed information)
pkg_check_modules(testing REQUIRED IMPORTED_TARGET
    gtest
    gmock)
target_link_libraries(my_unittests
    PkgConfig::testing)
~~~


Zugriffs-Rechte
---------------

Verwendung von *Zugriffs*-Rechten: `PUBLIC`, `INTERFACE`, `PRIVATE`

~~~ {.cmake .numberLines}
# old (same as PUBLIC)
target_include_directories(my_library
    include
    src
    header_only_helper
    unittest)

# new (define transitive behavior)
target_include_directories(my_library
  PUBLIC      # visible to all
    include
  INTERFACE   # visible only to users of my_library
    header_only_helper
  PRIVATE     # visible only to my_library
    src
    unittest)
~~~


Zugriffs-Rechte
---------------

Auch bei *Linking* (Include-Directories werden ebenfalls übernommen)

~~~ {.cmake .numberLines}
# old (same as PUBLIC)
target_link_libraries(my_library
    boost
    Threads::Threads
    flatbuffers)

# new (define transitive behavior)
target_link_libraries(my_library
  PUBLIC      # visible to all
    boost
  INTERFACE   # visible only to users of my_library
    Threads::Threads
  PRIVATE     # visible only to my_library
    flatbuffers)
~~~

Siehe: [cmake_is_like_inheritance]


Anderes
-------

* wenn Test- und echte Applikation den gleichen Code verwenden (üblicherweise) ist eine *interne Library* sinnvoll

* evaluiere den *absoluten Pfad* von Dateien in Funktionen mit: `get_filename_component(<var> <path> ABSOLUTE)`

* temporäres *Debugging* mit: `message(STATUS "my_variable=${my_variable})`

* *generator expression* um Target *properties* nach der Definition noch zu ändern

* beim Googlen auf *modern* achten
  * die meisten Treffer zeigen "alten" Style
