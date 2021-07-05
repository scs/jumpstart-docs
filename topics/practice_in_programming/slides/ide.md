Integrated Development Environment (IDE)
========================================


Typische Anforderungen
----------------------

* *Kompletter Workflow*
  * Compilation
  * Deployment
  * Debugging & Disassembly
* Syntax Highlighting
* Smart Code Traversal
* Refactorings
* Shortcuts
* Auto Formatter
* Support für BuildSystem
* etc.

Nachteile:

* spezifisch für eine Programmiersprache
* gross (Speicherplatz) und komplex


IDE vs. (smart) Editor
----------------------

Was will man immer:

* *Projekt*-Management

* *Code Traversal*
  * Projekt wächst
  * (neue) Entwickler kennen sich im Code nicht aus

* Out-of-the-Box *Support* für die entsprechende *Sprache*
  * benötigt sonst viel Konfigurations-Aufwand (Plugins)

* Local History aller Dateiänderungen

Editor (Basiert auf *Dateien*)    IDE (Basiert auf *Projekten*)
------                            ---
Visual Studio Code                Visual Studio IDE
Notepad++                         CLion / IntelliJ / PyCharm


Zusätzliche Tools
-----------------

* *Debugger* (GDB)
  * (conditional) Break-Points
  * Variable-Inspections
  * Stepping
* *Time travel* Debugger (UDB, rr, WinDbg)
* *Tracing* (strace, LTTng)
* *Decompiler*
* *Dynamic* code Checkers (Valgrind, ASan)
* *Static* code Checkers (Clang Analyzer and Clang-Tidy, Cppcheck, IDE built-in linters)
