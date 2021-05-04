Laufzeitumgebungen
==================


Wo "läuft" meine Applikation?
-----------------------------

* Software-Level
  * C/C++ Binary
    $\break\to$ Assembler *direkt auf HW* und Runtime-Libraries
  * Java/.Net Byte-Code
    $\break\to$ *JIT-Compiler* in JRE/CLR und Runtime-Libraries
  * Python Code
    $\break\to$ *Interpreter* (Zeile für Zeile) in PVM und Runtime-Libraries

* Hardware-Level
  * *Kernel-Space* $\to$ Ring 0
    * voller Befehlssatz
  * *User-Space* $\to$ Ring 3
    * Eingeschränkter Befehlssatz
  * Memory-Zugriff nur über *MMU* (Memory Management Unit)


Kompilierung/Runtime im Vergleich
---------------------------------

C/C++

* kompiliert zu *Objectcode*
* Optimierungen zur *Compiletime*
* Linking zur *Compile*- oder *Runtime*

Java/.Net

* kompiliert zu *Bytecode*
* Optimierungen zur *Runtime*
* Linking zur *Runtime*

Python

* kompiliert zu *Bytecode*
* "keine" Optimierungen
* Kompilieren und Linking zur *Runtime*


Kompilierung/Runtime im Vergleich
---------------------------------

\centering
![compilation](images/compilation.pdf){height=95%}


Bemerkungen zu Kompilierung
---------------------------

* Kompilierung braucht *Zeit*
  * inkrementelle Builds $\to$ Build-System
  * Vorkompilierung $\to$ precompiled Headers (C++)
  * Compiler Cache $\to$ `ccache` (C/C++)

* Inkrementelle Kompilierung produziert *Fehler*?
  * Build-System erkennt Abhängigkeiten nicht?
    * im System *oder* Projekt?
    * *Fehler* im Build System?
  * Lösung/Workaround: Clean Build: `make clean`, `mvn clean`


Reproduzierbarkeit & Nachvollziehbarkeit
----------------------------------------

*Warum* ist das wichtig?

* *Sicherheit*
  * garantiert Kompilat von vertrauenswürdiger Quelle
    $\break\to$ kein Kompilat von manipuliertem Code
* *Debugging*
  * Log-Files
  * Stack-Traces


Reproduzierbarkeit & Nachvollziehbarkeit
----------------------------------------

*Wie* stellt man das sicher?

* Releases mit [semantic_versioning]
* Reproduzierbare Kompilierung [reproducible_builds]

Wie weit *funktioniert* Reproduzierbarkeit überhaupt?

* ist ein eher schwieriges Problem
* hat viel Aufmerksamkeit bekommen, z.B: [yocto_reproducible_builds]
* je nach Definition
  * *Hard*: Hashes müssen passen
  * *Soft:* alter Stand muss kompilieren


OS-Level Runtimes
-----------------

* *Container*
  * Docker, Podman, LXC
  * nur Kernel wird abstrahiert
  * damit automatisch auch die Hardware (optimalerweise)
  * Software plus alle Runtime Libraries werden mitgeliefert

* *Virtual Machine*
  * virtualbox, VMware, HyperV, Xen, etc.
  * komplette Hardware wird abstrahiert (optimiert mit HW-Features)
  * komplettes OS muss mitgeliefert werden

|                          | C/C++  | JVM/CLR/PVM | Container   | VM          |
|--------------------------|--------|-------------|-------------|-------------|
| Hardware                 | direct | single      | single      | single      |
| Kernel                   | direct | single      | single      | virtualized |
| Filesystem               | direct | direct      | virtualized | virtualized |
| Distro/Runtime Libraries | direct | direct      | separated   | separated   |
