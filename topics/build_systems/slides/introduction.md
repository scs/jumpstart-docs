Einführung
==========


Kompilierung im Vergleich
-------------------------

\colBegin{0.5}

Java/.Net

* kompiliert zu *Bytecode*
* Optimierungen zur *Runtime*
* Linking zur *Runtime*

\colNext{0.5}

C/C++

* kompiliert zu *Objectcode*
* Optimierungen zur *Compiletime*
* Linking zur *Compile*- oder *Runtime*

\colEnd


Kompilierung im Vergleich
-------------------------

\centering
![Vergleich Kompilation Java vs. C/C++](images/compilation.pdf)


Build-Probleme
--------------

* Inkrementelle Builds
* Korrekte Binaries/Bytecode
* Korrekte Abhängigkeiten

![Build-Abhängigkeiten](images/build_dependencies.pdf){height=75%}


Beispiel: C++-Projekt
---------------------

\centering
![Build-Abhängigkeiten example-Projekt](images/build_dependencies_example_project.pdf){height=95%}


Build-Tools
-----------

### Compiler

* gcc
* clang/llvm
* msvc
* javac

### Build-Systeme

* make
* ninja
* Visual Studio

### Meta-Build-Systeme

* cmake
* meson
* maven
