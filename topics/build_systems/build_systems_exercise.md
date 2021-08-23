<#include meta/exercise.md>

---
title: "Arbeitsblatt: Build-Systeme"
---



Datei-Struktur
==============

Am Anfang eines Projektes stellt sich die Frage,
wie der Source-Code, die Unit-Tests, andere Ressource-Files, Libraries, etc.
im Repository organisiert werden sollen.
Auch sollte man sich überlegen,
welche Produkte aus einem Repo resultieren sollen;
dies meist in Form von Libraries oder Executables.

Ein üblicher Ansatz kann sein,
das Repo nach einzelnen Deliverables zu organisieren
und darin nach Source-Code, Unit-Test-Code und weiteren Ressourcen aufzuteilen.
Sollte nur ein Deliverable pro Repo verwaltet werden,
kann die erste Hierarchie-Stufe weggelassen werden.
Zudem ergibt es sehr häufig Sinn,
bei wachsender Anzahl Source-Files den Source-Folder thematisch in Unterordner aufzuteilen.


Aufgabe
-------

* Den `code` Unterordner an einem geeigneten Ort entpacken.
* Die File-Struktur analysieren und alle möglichen Deliverables/Binaries versuchen zu erkennen.


<#ifdef solution>

Lösung
------

* Header-Only:
  * `libs/catch/catch.hpp`
  * `src/utils/*.hpp`
* Eigene Library:
  * `src/logging/logger.cpp`
* Binaries:
  * main-app: `src/main.cpp`
  * test-app: `test/main.cpp`

<#endif>



Build-System mit CMake
======================

Üblicherweise bringen die mächtigeren IDEs für C++ ein eigenes Build-System mit.
Dies hat zwar gute Tool-Unterstützung,
ist meist aber nicht für portable oder cross-kompilierte Projekte geeignet.
Und auf Basis von reinem `make` sollte man heute auch nicht mehr arbeiten.
Die Komplexität und je nachdem auch Kompatibilitäts-Probleme nehmen sonst schnell Überhand.

CMake ist ein Meta-Build-System,
welches eine Abstraktionsschicht über rohe Build-Systeme wie `make` legt.
Zudem bietet es Unterstützung für unterschiedliche Tools, wie `gcc`, `vc`, `clang`, `make`, `ninja`, etc.

Seit der Version 2017 verfügt VisualStudio über integrierte Unterstützung für CMake.
Das bedeutet, dass nicht mehr das eigene Build-System von Microsoft zum Einsatz kommt.
Zudem gibt es bereits IDEs, wie CLion, die komplett auf eigene Systeme verzichten
und direkt auf CMake als Projekt-Verwaltung aufbauen.
Natürlich kann ein Projekt aber auch nur mit CMake
und dem entsprechenden Compiler ohne jegliche IDE gebaut werden.

Der typische Workflow, um ein CMake-basiertes Projekt zu kompilieren, sieht folgendermassen aus:

1. CMake-Cache erzeugen.
   Dies geschieht mittels dem `cmake` Tool
   oder der Unterstützung einer entsprechenden IDE.
   Dadurch wird das `CMakeLists.txt` gelesen
   und ein Build-System mit z.B. `ninja` oder `make` erzeugt.
1. Das erzeugte Build-System kann nun das komplette Projekt selbständig kompilieren.
1. Sollte sich die CMake-Konfiguration ändern,
   wird dies ebenfalls durch das erzeugte Build-System erkannt und angepasst.


Aufgabe
-------

* Die Vorlage für die CMake-Konfiguration: `CMakeLists_exercise.txt` im entpackten `code`-Ordner
  zu `CMakeLists.txt` umbenennen
  und mit CLion dieses CMake-Projekt (`code`-Ordner) öffnen.
  Dies sollte automatisch den CMake-Cache erzeugen.
* Das `CMakeLists.txt` so vervollständigen,
  dass die Hauptapplikation gebaut werden kann.
  Die Dokumentation zu folgenden Befehlen könnte helfen: `set`, `target_include_directories`, `add_executable`
* Zu beachten: alle Pfadangaben sind relativ zur Position der aufrufenden Datei: `CMakeLists.txt`.


<#ifdef solution>

Lösung
------

~~~ {.cmake}
cmake_minimum_required(VERSION 3.5)

# project settings
project(bit_fields)
set(BF_TARGET bit-fields)

# main app target
set(BF_TARGET_APP ${BF_TARGET}-app)
add_executable(${BF_TARGET_APP}
  "src/logging/logger.cpp"
  "src/main.cpp"
)
target_include_directories(${BF_TARGET_APP} PUBLIC
  "libs"
  "src"
)
~~~

<#endif>



Globbing vs. statische Datei-Liste
==================================

Wenn die Anzahl Source-Code Dateien wächst,
kann es ohne entsprechende Unterstützung der IDE mühsam sein,
jede `.cpp` Datei einzeln im `CMakeLists.txt` einzutragen.
Deshalb unterstützt CMake Globbing, ähnlich wie dies in den meisten Shells verwendet wird.

Dies verhindert, dass die CMake-Konfiguration jeweils angepasst werden muss,
wenn sich der Verzeichnisbaum verändert.
Allerdings kann dies auch zu Problemen führen,
da CMake nicht selber wissen kann, wenn neue Dateien hinzugekommen sind.
Deshalb muss in solchen Fällen ein erneutes Generieren des CMake-Caches manuell ausgelöst werden.


Aufgabe
-------

* Automatisieren der Erstellung der Source-Datei-Liste im `CMakeLists.txt` mittels Globbing.


<#ifdef solution>

Lösung
------

~~~ {.cmake}
# search all relevant files
file(GLOB_RECURSE BF_SRC_ALL "src/*.c*")
...
add_executable(${BF_TARGET_APP} ${BF_SRC_ALL})
~~~

<#endif>



Vorbereitung für Unittesting
============================

Ein häufig erster Schritt in einem neuen Projekt ist die Einrichtung eines Unittest-Frameworks.
Da die damit erstellten Tests in C/C++ üblicherweise zu einem eigenen Binary/Delivery kompiliert werden,
muss dieses auch im Build-System eingebaut werden.

Damit der Source-Code für die Applikation und die Tests jeweils nur einmal gebaut werden müssen,
ergibt es meist Sinn, den produktiven Code in eine Library auszulagern.
Diese Library kann dann einerseits in die Applikation, andererseits in das Test-Binary gelinkt werden.


Aufgabe
-------

* Auftrennen der Kompilierung in separate Applikation und Library.
  Folgende Befehle sind dazu nützlich: `add_library`, `target_link_libraries`.
* Analysieren der unterschiedlichen Optionen von `add_library`.
  Welcher Library-Typ ist am geeignetsten?
* Nur `main.cpp` soll für die Applikation kompiliert werden.
  Alle anderen Dateien im `src` Ordner sollen zur Library gehören.
* Ausführen der Applikation über die Konsole.


<#ifdef solution>

Lösung
------

* Eine `STATIC` Library ist hier am geeignetsten,
  da wir die Applikation als ein einziges Binary liefern
  und keine Laufzeitabhängigkeiten haben möchten.
  Dasselbe gilt für das Test-Binary.
  Meist muss man dies aber gar nicht erst angeben.
  Dann kann der Aufrufer selber definieren,
  wie interne Libraries gelinkt werden:
  [`BUILD_SHARED_LIBS`](https://cmake.org/cmake/help/latest/variable/BUILD_SHARED_LIBS.html)

~~~ {.cmake}
# search all relevant files
file(GLOB_RECURSE BF_SRC_MAIN "src/main.c*")
file(GLOB_RECURSE BF_SRC_ALL "src/*.c*")
list(REMOVE_ITEM BF_SRC_ALL ${BF_SRC_MAIN})

# internal library target
set(BF_TARGET_LIB ${BF_TARGET}-lib)
add_library(${BF_TARGET_LIB} STATIC ${BF_SRC_ALL})
target_include_directories(${BF_TARGET_LIB} PUBLIC "libs" "src")

# main app target
set(BF_TARGET_APP ${BF_TARGET}-app)
add_executable(${BF_TARGET_APP} ${BF_SRC_MAIN})
target_link_libraries(${BF_TARGET_APP} PRIVATE ${BF_TARGET_LIB})
~~~

<#endif>



Unittest-Framework Catch2 einrichten
====================================

Um Unittests möglichst ausführlich aber doch einfach schreiben zu können,
existieren viele unterschiedliche Frameworks, wie z.B: `googletest`, `boost.test` oder `catch2`.
Catch2 ist dabei das modernste aber auch sehr schlanke Pendant aus dieser Liste.
Wenn kein mächtiges Mocking-Framework oder ähnliche weiterführenden Mechaniken benötigt werden,
reicht dies vollkommen aus.
Zudem ist es als Header-Only-Implementation verfügbar
und lässt sich entsprechend einfach im Build-System einbinden.

Zuerst muss die Datei `test/main.cpp` erstellt und darin eine entsprechende Main-Funktion implementiert werden.
Dieses neue Main kann als Basis für das Test-Binary fungieren.
Die vorbereitete Library kann zu diesem Binary gelinkt werden.


Aufgabe
-------

* Studieren der Dokumentation von Catch2 betreffend
  [main()-Implementation](https://github.com/catchorg/Catch2/blob/devel/docs/configuration.md#main-implementation)
  und Implementation von `main.cpp` für die Tests.
* Erweiterung des Build-Systems durch das Test-Binary.
  Dies kann ähnlich wie bei der Hauptapplikation bewerkstelligt werden.
* Ausführen der Test-Applikation über die Konsole.


<#ifdef solution>

Lösung
------

main.cpp

\lstinputlisting{code/test/main.cpp}


CMakeLists.txt

~~~ {.cmake}
file(GLOB_RECURSE BF_TEST_ALL "test/*.c*")
...
# unit test target
set(BF_TARGET_TEST ${BF_TARGET}-test)
add_executable(${BF_TARGET_TEST} ${BF_TEST_ALL})
target_link_libraries(${BF_TARGET_TEST} PRIVATE ${BF_TARGET_LIB})
~~~

<#endif>



Unittest-Integration in CMake
=============================

CMake ist neben seiner Aufgabe als Build-System aber noch mehr.
Es bietet z.B. auch Unterstützung für die Paketierung und das Deployment von Applikationen.
Allerdings werden diese Funktionen häufig durch übergeordnete Systeme verwaltet, wie z.B. der `Yocto`-Toolchain.

Trotzdem kann es für kleinere Projekte nützlich sein,
gewisse solche erweiterten Funktionen von CMake zu verwenden.
Die eingerichteten Unittests können z.B. für CMake so deklariert werden,
dass sie direkt durch CMake gestartet werden können.


Aufgabe
-------

* Deklaration des Test-Binary für CMake als Test.
  Relevante Befehle: `enable_testing`, `add_test`.
* Ausführen Tests entweder direkt durch `make test` oder über das Configuration-DropDown in CLion.


<#ifdef solution>

Lösung
------

~~~ {.cmake}
enable_testing()
add_test(unittest ${BF_TARGET_TEST})
~~~

<#endif>



<#ifdef solution>

Endversion von CMakeLists.txt
=============================

\lstinputlisting{code/CMakeLists.txt}

<#endif>
