Was ist hier schlecht?
-------

Code Beispiel: [01-intro.py](code/exercise/01-intro.py)


intro.py Zusammenfassung
-------

* Fast keine Strukturierung durch Methoden/Funktionen/Datenmodelle
* Code duplication
* Magic numbers
* Business Logik und Präsentation vermischt

-> Besser: [01-intro-refactored.py](code/solution/01-intro-refactored.py)


Abstraktion: Nachteile
-------

* Erhöht Komplexität
* Änderung der Abstraktion haben Auswirkungen auf alle User
* Die Abstraktion am falschen Ort (Vertikal)
* Die Abstraktion versteckt wichtige Features
