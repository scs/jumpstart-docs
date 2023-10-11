Reviews
=======


Warum Reviews?
--------------

* Reviews *fördern*:
  * gute Architektur
  * Code-Qualität
  * Verteilung von Know-How
* stellen einen gemeinsamen *Workflow* sicher
* neue Mitarbeitende werden während dem produktiven Arbeiten *geschult*


Was sollte nicht im Review angeschaut werden?
---------------------------------------------

* Code-Style (wenn möglich $\to$ lenkt vom Inhalt ab)
  * Style *definieren* (z.B. auf Basis von [google_code_style_guides])
  * *Linter* einsetzten
* andere Änderungen/Features *wünschen*
  * neues Issue öffnen
  * nur auf gemachte Dinge und geforderte Dinge eingehen


Was ist ein Pull/Merge-Request?
-------------------------------

* PRs sind ein Tool für einen strukturierten *Review*-Prozess
* es wird eine *saubere und klare* Git-History vorausgesetzt
* das Team muss ein *gemeinsames Verständnis* für den Prozess haben
* ein PR sollte *gut separiert* und nicht zu gross sein
* jeder Commit kann als einzelner *logischer Schritt* für das Erreichen des Ziels betrachtet werden


Strukturieren eines PR
----------------------

* bearbeitet ein Feature $\to$ genau das was im *Issue-Task* definiert ist
* ein separater *Feature-Branch* (je nach Grösse auch mehrere)
* möglichst *nicht zu gross* $\to$ Task in mehrere PRs aufteilen
* basiert auf main/development Branch
* basiert bei Bedarf auf anderem Feature-Branch (je nach Workflow nicht `rebase` sondern `merge`)


Vor dem Öffnen eines PR
-----------------------

* *Aufräumen* der History
* Gute *Beschreibung* und Links hinzufügen
* auf main (oder development) rebasen
* Das alles ermöglicht Reviews auf *höherem Level*, ohne sich mit Details abzulenken
* Wir machen die saubere Git History nicht nur für die History, sondern um:
  * ein *effizientes Review* machen zu können
  * später Bugs zu suchen
  * ähnliche Arbeiten später wieder ähnlich zu machen


Saubere History - von Anfang an
-------------------------------

wenn ein *fehlendes Refactoring* entdeckt wird:

1. *parkieren* der unfertigen Änderungen (Branch oder Stash)
1. durchführen des *Refactorings*
1. parkierte Änderungen *rebasen/unstashen*
1. weiterarbeiten

\vspace{0.5cm}

\colBegin{0.5}

*Branch*

~~~ {.bash .numberLines}
git checkout -b tmp
git commit -m "WIP"
git checkout my_feature
...
git checkout tmp
git rebase my_feature
git checkout my_feature
git branch -D tmp
~~~

\colNext{0.5}

*Stash*

~~~ {.bash .numberLines}
git stash
...
git stash pop
~~~

\colEnd


Aufräumen der History
---------------------

* alle Commits zusammen `squash`en, die zu einer *einzelnen logischen Änderung* gehören
  * einzelne Schritte zum Ziel, nicht einzelne Issues
  * jeder Commit muss konsistent, kompilierbar sein und muss die CI-Tests erfüllen
  * Änderungen an mehreren Files derselben logischen Änderung gehören in denselben Commit
* möglichst *kleine und konsistente* Commits
* auf gute Commit *Beschreibungen* achten

\vspace{0.5cm}

Tools:

* `git commit --amend`
* `git rebase -i`
* `git fix` (siehe "Aliases")
* `git gui` for line/block staging


Iterationen
-----------

*Review*

* Reviewer:in schaut den PR als ganzes an, um einen *Überblick* zu erhalten
* Reviewer:in geht Schritt für Schritt durch alle *Commits* (chronologisch)
  und erstellt *Kommentare* oder *Fragen*
* Reviewer:in *akzeptiert oder lehnt den PR ab* $\to$ Zeichen, dass das Review beendet ist.

*Rework*

* *Antworten* von Fragen direkt im Tool
  * dies dient als *Dokumentation*
* *Verbesserungen* umsetzten
  * mittels Kommentar-Antwort als *bearbeitet* markieren (z.B. `fixed`)
  * evtl. die gewählte Lösung erwähnen

*Repeat*

bis alle Verbesserungen umgesetzt und PR akzeptiert ist


Abschluss
---------

Der Prozess ist beendet, nachdem alle/genügend Reviewer:innen den PR *akzeptiert* haben.

1. Der:Die Ersteller:in *rebased* den Feature-Branch auf den aktuellen main/development Branch
1. `git push --force-with-lease`
1. Mittels "Merge"-Button in PR-GUI (Bitbucket, etc.) *abschliessen*
1. Dabei kann der Feature-Branch direkt *entfernt* werden

Das lokale Git kann folgendermassen von fehlenden remote Branches geräumt werden:

~~~ {.bash}
git remote prune origin
~~~


Fazit
-----

\large

* PRs und Reviews sind *sinnvoll* bei Projekt-Teams mit mehr als einem Mitglied
  * evtl. sogar als Methode um sein eigenes Review zu verbessern
* das Team muss einen *klaren* Workflow definiert haben
* nutze die *Tools* der entsprechenden Code-Platform (Bitbucket) oder unabhängige Tools (gerrit)
* *lerne und verbessere* den Prozess von Reviews

\vspace{0.5cm}

*Denke auch immer an die Person auf der anderen Seite des Tools!*

* Kommentare gehen an eine Person $\to$ Höflich
  * Das gilt auch für den:die Empfänger:in
* Nicht alles persönlich nehmen. Auch bei ungünstiger Formulierung. Sprache ist schwierig.
* Bei wirklicher Beleidigung o.Ä. sofort mündlich Kontakt aufnehmen.
