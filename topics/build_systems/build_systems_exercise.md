<#include meta/exercise.md>

---
author: Christian Lang (clang)
title: "Arbeitsblatt: Build-Systeme"
---


Projekt strukturieren und aufsetzen
===================================

In dieser Aufgabe sollen Sie explizit nicht das die CMake-Funktion `prcpp_code` verwenden,
sondern von Grund auf ein neues Projekt aufsetzen.
Gliedern Sie dies auch in das `workspace`-Repo ein.
Damit dieses Sub-Projekt auch in das Hauptprojekt aufgenommen wird,
müssen Sie im Top-Level `CMakeLists.txt` einen entsprechenden weiteren `add_subdirectory()` Eintrag erstellen.

Wir verwenden in dieser Übung kein `cpplint`. Zudem definieren wir das CMake Target für die Applikation
und die Tests komplett unabhängig und deshalb ohne eigene Basis-Library.

Aufgabe
-------

* Erstellen Sie einen Unterordner `build_system` im `workspace` Repo.
* Erstellen Sie eine `CMakeLists.txt`-Datei in welcher Sie das Projekt definieren.
  Orientieren Sie sich am `example`-Projekt.
* Strukturieren Sie ihr Projekt in weiteren Unterordner.
  Diese sollen bereits jetzt Dateien für ihr Hauptprogramm (`src`) und Unittests (`unittest`) vorsehen.
* Folgende Dateien sollen im Projekt enthalten sein:
  * `main.cpp`
  * `student.h` und `student.cpp`
  * `module.h` und `module.cpp`
* Definieren Sie ein Executable-Target mit dem Namen `build_system_app`.
  Tragen Sie die dazugehörigen Source-Dateien einzeln ein.
* Definieren Sie ihren Unterordner als Include-Ordner.
* Setzten Sie die bereits definierten Compiler-Flags in der Variable `cxxflags` in ihrem Executable-Target.

Bevor Sie das Projekt kompilieren können, müssen Sie zuerst die Implementation der nächsten Aufgabe erledigen.

<#ifdef solution>

Lösung
------

CMakeLists.txt

\lstinputlisting[firstline=1, lastline=14]{code/CMakeLists.txt}


<#endif>



Implementation
==============

Nun sollen Sie die vorbereiteten Dateien mit Inhalt füllen. Die Aufgabe ist, die öffentlichen Klassen `Student` und
`Module` zu implementieren und in einem Executable zu verwenden. Das Executable soll folgenden Output zur Runtime
generieren:

~~~
Module: prcpp, ECTS: 3
Module: sysad, ECTS: 3
~~~

Die Klasse `Module` ist ein purer Datencontainer mit öffentlichen Attributen und evtl. passenden Konstruktoren.

Die Klasse `Student` soll einen Default-Konstruktor und folgende Methoden anbieten:

~~~ {.cpp}
size_t GetModuleCount();
Module GetModule(size_t index);
bool AddModule(Module new_module);
bool RemoveModule(size_t index);
~~~

Die interne Verwaltung der Module soll mittels statischem Array implementiert werden:

~~~ {.cpp}
static constexpr size_t kMaxModules = 4;
Module modules_[kMaxModules];
~~~

Die Klasse `Module` soll eine Member-Variable für die Anzahl ECTS-Punkte und eine für den Modul-Namen besitzen.
Verwenden sie `std::string` für den Namen.

Aufgabe
-------

* Definieren und Implementieren Sie die Klassen `Student` und `Module`.
* Verwenden Sie `#pragma once` in allen Header-Dateien als erste Instruktion um Mehrfach-Inkludierung des
  Präprozessors zu verhindern.
* Verwenden Sie `#include <cassert>` um bei Bedarf Assertions zu schreiben.
* Implementiere Sie `main.cpp`.
  Die Applikation soll einen Studenten instanziieren und die zwei dazugehörigen Module einfügen.
  Danach sollen alle Module des Studenten auf der Konsole ausgedruckt werden.
  Nun können Sie die Applikation kompilieren und starten.


<#ifdef solution>

Lösung
------

main.cpp
\lstinputlisting[language=C++]{code/src/main.cpp}

student.h
\lstinputlisting[language=C++]{code/src/student.h}

student.cpp
\lstinputlisting[language=C++]{code/src/student.cpp}

module.h
\lstinputlisting[language=C++]{code/src/module.h}

module.cpp
\lstinputlisting[language=C++]{code/src/module.cpp}

<#endif>



Unit-Tests
==========

Im letzten Teil soll mittels dem verfügbaren Test-Framework Catch2 einige Tests
für die Klasse `Student` geschrieben werden.
Dazu müssen Sie nur den Header `catch2/catch.hpp` in ihrer Test-Datei inkludieren.
Nennen Sie diese Datei `student_test.cpp` und plazieren Sie sie im Ordner `unittest`.
Zudem müssen Sie einen Einstiegspunkt für das Test-Executable definieren.
Dies machen Sie mittels einer weiteren `main.cpp` und folgendem Inhalt:

\lstinputlisting[language=C++]{code/unittest/main.cpp}


Aufgabe
-------

* Erstellen Sie die Datei `main.cpp` im dafür vorbereiteten Unterordner `unittest`.
* Erstellen Sie die Datei `student_test.cpp` und implementieren Sie entsprechenden Tests.
* Ergänzen Sie die Datei `CmakeLists.txt` um das neue Testprojekt. Dies soll ein weiteres Executable-Target sein.


<#ifdef solution>

Lösung
------

Ergänzen Sie `CMakeLists.txt` um folgende Zeilen:
\lstinputlisting[firstline=16]{code/CMakeLists.txt}

student_test.cpp
\lstinputlisting[language=C++]{code/unittest/student_test.cpp}

<#endif>



Memory-Checking mit Valgrind
============================

In C++ operieren Sie auf der echten Maschine ihres Systems und haben deshalb weniger Sicherheits-Mechanismen
als z.B. in einer JVM. Ein übliches Problem ist falscher Code, welcher auf Speicher operiert, welcher
entweder nicht initialisiert wurde oder gar nicht dem aktuellen Prozess gehört. Im ersten Fall erzeugt dies
unter Umständen ein unerwartetes/falsches Verhalten der Applikation, im zweiten Fall führt dies zum Absturz
mit dem Fehler `SIGSEGV` oder eben *Segmentation-Fault*.

Um solche Probleme zu analysieren und zu debuggen gibt es unter Linux das freie Tool `valgrind`.
Im Standard-Modus analysiert es die Alloziierung und Verwendung allen Speichers ihrer Applikation
und meldet eventuelle Probleme.
Dies wird intern so implementiert, dass `valgrind` denn Allocator der C++-Runtime austauscht
und somit jede Memory-Allozierung selber durchführen und überwachen kann.

Sie können `valgrind` entweder über die Konsole laufen lassen oder direkt über CLion.
Konfigurieren müssen Sie dazu nichts. Siehe:
[CLion - Valgrind Memcheck](https://www.jetbrains.com/help/clion/memory-profiling-with-valgrind.html)

Versuchen Sie z.B. die Methode `Student::RemoveModule` so zu manipulieren, dass der Loop auf mehr Zellen
operiert als er eigentlich sollte. Je mehr er vom eigentlichen Memory abweicht, desto wahrscheinlicher
erhalten Sie nicht nur Warnungen sondern einen *Segfault*.



Zukunft
=======

Im weiteren Verlauf des Unterrichts werden Sie viele eigene kleine Projekte erstellen.
Verwenden Sie dazu jeweils nicht mehr selber geschriebenen CMake-Code sondern die CMake-Funktion `prcpp_code`.
Für jedes Projekt können Sie also einen neuen Unterordner im `workspace`-Repo einrichten,
diesen Unterordner im bestehenden Top-Level `CMakeLists.txt` einfügen
und ihr eigenes `CMakeLists.txt` folgendermassen schreiben:

~~~ {.cmake}
include(prcpp_code)
prcpp_code(<ihr_projekt_name>)
~~~
