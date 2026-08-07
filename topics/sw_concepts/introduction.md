Was ist hier schlecht?
-------

Code Beispiel: [01-intro.py](code/exercise/01-intro.py)


Beobachtungen
-------

* Fast keine Strukturierung durch Klassen/Methoden/Funktionen/Datenmodelle
* Code duplication
* Magic numbers
* Business Logik und Präsentation vermischt
* etc.


Verbesserte Variante
--------------------

Besser: [01-intro-refactored.py](code/solution/01-intro-refactored.py)

Weitere Verbesserungsmöglichkeiten:

* ENUM für die Währung


Abstraktion: Nachteile
----------------------

* Erhöht Komplexität
* Änderung der Abstraktion haben Auswirkungen auf alle User
* Die Abstraktion am falschen Ort (Vertikal)
* Die Abstraktion versteckt wichtige Features


Unterschätzt: Naming
--------------------

* Einer der wichtigsten Aspekte in Software sind gute Namen.
* Leider ist Namensgebung auch einer der schwierigsten Aufgaben!
* Gilt für alles in Software:
  Variablen, Parameter, Funktionen, Klassen, Namespaces, Ordner, Binaries, etc.

### Best Practices

Ein Name soll:

* vollständig aber nicht zu lang sein
* im aktuellen Kontext (Namespace/Klasse/Funktion) verständlich sein
* (meistens) keine Abkürzung sein


Dev-Environment für die Übungen
-------------------------------

Entweder:

* Code Space: *Jumpstart Python 3*
* Beliebige Python-IDE (z.B. *PyCharm*) mit Python $>=$ *3.12*
