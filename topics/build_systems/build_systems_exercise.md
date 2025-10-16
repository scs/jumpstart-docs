<#include meta/exercise.md>

---
title: "Arbeitsblatt: Build-Systeme"
---



Vorbereitung
============

Diese Übung zeigt an einem kleinen Beispielprojekt,
wie man mit CMake ein C++ Programm und dessen Unittests kompilieren und managen kann.
Als Erstes müssen wir die Entwicklungsumgebung
und die Projekt-Basis vorbereiten.

Für einfacheres Copy-Paste des Codes kann die Ursprungsdatei auf Github verwendet werden:
[build_systems_exercise.md](https://github.com/scs/jumpstart-docs/blob/main/topics/build_systems/build_systems_exercise.md)


Aufgabe
-------

* [Codespace "Jumpstart CMake exercise" starten](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=351816701&skip_quickstart=true)
* Die File-Struktur im Unterordner `topics/build_systems/code` analysieren.


Lösung
------

* Unittest-Framework: `libs/catch/catch.hpp`
* Hauptapplikation: `src/main.cpp`
* Eigene Library: `src/logging/*` und `src/utils/*`
* Unittests zur eigenen Library: `test/*`



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

Die meisten heutigen IDEs haben Unterstützung für CMake.
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

* Im Codespace links den Tab `CMake` öffnen und dort unter `Konfigurieren` das Kit auswählen: `GCC ...`.
* Nun sollte vscode automatisch das noch leere CMake-Projekt (im `topics/build_systems/code`-Ordner) laden
  und den CMake-Cache erzeugen.
* Das `CMakeLists.txt` so vervollständigen,
  dass die Hauptapplikation gebaut werden kann.
  Vscode erkennt Änderungen automatisch, sobald das `CMakeLists.txt` gespeichert wird.
* Zu beachten: alle Pfadangaben sind relativ zur Position der aufrufenden Datei: `CMakeLists.txt`.


Lösung
------

Wir definieren nun ein erstes CMake-Target mit dem Namen `bit-fields-app`.
Dieser Name wird mit den Variablen `BF_TARGET` und `BF_TARGET_APP` erzeugt
und im `add_executable` verwendet.
Das `target_include_directories` wird benötigt,
damit der Kompiler weiss, wo er Header-Files suchen muss.

~~~ {.cmake}
cmake_minimum_required(VERSION 3.10)

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

Jetzt kann das CMake-Target `bit-fields-app` (auch links im `CMake` Tab) kompiliert und auch gestartet werden.



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


Lösung
------

~~~ {.cmake}
# search all relevant files
file(GLOB_RECURSE BF_SRC_ALL "src/*.c*")
...
add_executable(${BF_TARGET_APP} ${BF_SRC_ALL})
~~~



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
* Nur `main.cpp` soll für die Applikation kompiliert werden.
  Alle anderen Dateien im `src` Ordner sollen zur Library gehören.
* Ausführen der Applikation über die Konsole.


Lösung
------

Zuerst müssen wir unterschiedliche Listen mit den Source-Files erstellen.
Dazu verwenden wir erneut Globbing.
Da das `main.cpp` ebenfalls im `src` Ordner liegt,
entfernen wir es mit `REMOVE_ITEM` nachträglich aus der Liste `BF_SRC_ALL`.

Bei der Definition des Library-Targets mit `add_library` verwenden hier eine `STATIC` Library,
da wir die Applikation als ein einziges Binary liefern
und keine Laufzeitabhängigkeiten haben möchten.
Dasselbe gilt für das Test-Binary.

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



Unittest-Framework Catch2 einrichten
====================================

Um Unittests möglichst ausführlich aber doch einfach schreiben zu können,
existieren viele unterschiedliche Frameworks, wie z.B: `googletest`, `boost.test` oder `catch2`.
Catch2 ist dabei das modernste aber auch sehr schlanke Pendant aus dieser Liste.
Wenn kein mächtiges Mocking-Framework oder ähnliche weiterführenden Mechaniken benötigt werden,
reicht dies vollkommen aus.
Zudem ist es als Header-Only-Implementation verfügbar
und lässt sich entsprechend einfach im Build-System einbinden.


Aufgabe
-------

* Erweiterung des Build-Systems durch das Test-Binary.
  Dies kann ähnlich wie bei der Hauptapplikation bewerkstelligt werden.
* Ausführen der Test-Applikation über die Konsole: `./bit-fields-test`.


Lösung
------

CMakeLists.txt

~~~ {.cmake}
file(GLOB_RECURSE BF_TEST_ALL "test/*.c*")
...
# unit test target
set(BF_TARGET_TEST ${BF_TARGET}-test)
add_executable(${BF_TARGET_TEST} ${BF_TEST_ALL})
target_link_libraries(${BF_TARGET_TEST} PRIVATE ${BF_TARGET_LIB})
~~~



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
* Ausführen der Tests entweder direkt durch `make test` oder über den `Test` Tab links in vscode.


Lösung
------

~~~ {.cmake}
enable_testing()
add_test(unittest ${BF_TARGET_TEST})
~~~



Endversion von CMakeLists.txt
=============================

\lstinputlisting{code/CMakeLists_solution.txt}

