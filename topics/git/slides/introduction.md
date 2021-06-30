Einführung
==========


Was ist Git?
------------

\large
> Dezentrales Version Control System

* *schneller* als alle anderen
* nicht für *binäre* Dateien gedacht
* nicht für *grosse* Dateien gedacht $\to$ `git-lfs`
* Tool für bessere *Zusammenarbeit*


Was macht Git?
--------------

* speichert *kompletten Inhalt* von Files und nicht nur die Unterschiede
* speichert *Objekte* und Trees
* gesamte Daten in *`.git`* Ordner im Root-Ordner des Projekts
* *diffs* und *patches*
* Identifizierung von Objekten, Commits, etc. passiert über *Hashes*
* bei `git push`/`git fetch` wird der Objekt-Store *synchronisiert*
* Netzwerk-Kommunikation ist *komprimiert*

### Wie funktioniert Git intern?

[git_inside_out]


Was ist ein dezentrales VCS?
----------------------------

* *lokale* Branches
* *leichtgewichtige* Branches
* funktioniert *unabhängig* von einem zentralen Server
* *Verbindung* zu Server benötigt kein spezielles Protokoll: SSH reicht
* lokal kann und darf man *alles* ausprobieren
