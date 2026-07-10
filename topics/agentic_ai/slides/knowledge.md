Wissensblock
============


Inhalt
------
* Sicherheit
* AGENTS.md
* Skills
* MCP Server
* Modelle und Kosten
* Kontext
* Prompting
  * Was kann ich alles an die KI delegieren?
  * Was für Ergebnisse kann ich erwarten?
  * Wie gehe ich vor, damit die KI mir gute Ergebnisse liefert?
  * Taskgrösse und Detailgrad der Anweisungen


Sicherheit
----------

\Large

*KI-Tools haben Zugriff auf die Shell und können theoretisch alles ausführen, was euer Benutzer auch kann (ssh auf Kundensysteme, terraform etc.)!*

\normalsize

* Prüft jedes Command, was die KI ausführen will. Bis zum Ende scrollen!
* Setzt die Entwicklungsumgebung ggf. in einem Container auf, um den Zugriff auf Umsysteme zu beschränken
* Niemals mit `--yolo`-Flag ausführen

\Large

*GitHub CoPilot darf nur mit dem SCS-Konto verwendet werden.*

\normalsize

Ansonsten funktioniert die Blacklist nicht und die Vereinbarung mit GitHub (DSG, Vertraulichkeit) wird nicht eingehalten.

Ausserdem:
* Keine Secrets von KI-Tools generieren lassen
* Generierten Code immer selber prüfen und testen


Heads up
--------

\Large

> *Subject to change: Die Entwicklung im Bereich KI und KI-Tools läuft rasend schnell.
> Dinge können sich von heute auf morgen ändern. Darauf müssen wir uns einstellen, flexibel bleiben und jeweils anpassen!*

\normalsize


AGENTS.md
---------

> AGENTS.md is the README for Agents.
> 
> <cite>https://agents.md/</cite>

* Enthält Kontext und Anweisungen für Agenten, z.B.
  * Kommandos, um Tests auszuführen
  * (Verweise auf) Best Practices
  * Patterns in der Codebase
  * Ziele und Fokus der aktuellen Projektphase
* Kann Technologie- und Projektspezifisch angepasst werden
* Kann individuelle Anweisungen enthalten, z.B. "Gib mir Feedback zum Prompt" (sehr empfehlenswert am Anfang!)
* Github Copilot und co. können AGENTS.md automatisch generieren lassen

Beispiel und Tipps: https://supercomputingsystems.atlassian.net/wiki/spaces/SCSPL/pages/1524334693/Phase+7+Realisierung

*Warum sollte ich AGENTS.md nutzen?*

* Es lässt den Agenten zielgerichteter arbeiten
* Ihr müsst nicht alles im Prompt angeben
* Ihr müsst euch nicht in jeder Session wiederholen

Richtig eingesetzt erhöht AGENTS.md die Qualität der Ergebnisse und kann Kosten reduzieren (direkt oder indirekt).


Skills
------

> A standardized way to give AI agents new capabilities and expertise.
> 
> <cite>https://agentskills.io/home</cite>

* Skills sind Fähigkeiten, die Agenten haben können, z.B. "Code Review", "Design", "Dokumentation schreiben", ...
* Ordner mit SKILL.md, SKILL.md enthält mindestens `name` und `description`
* Kann z.B. Anweisungen zu Workflows oder Kontext zum Unternehmen, Projekt oder Codebase enthalten

*Was ist der Unterschied zwischen AGENTS.md und Skills?*

* AGENTS.md ist allgemein, beschreiben das *Was*
* Skills sind *spezifisch für einen Aufgabentyp*, beschreiben einen Workflow (das *wie*) und enthalten den dafür nötigen Kontext

*Wann sollte ich Skills nutzen?*

* Wenn ihr Agenten Fähigkeiten beibringen wollt, die sie nicht von Haus aus haben
* Wenn ihr den Fokus auf bestimmte Fähigkeiten legen wollt

*Man muss nicht für alles gleich einen Skill nutzen. Manches kann man auch kompakt im Prompt formulieren.*


MCP Server
----------

> MCP (Model Context Protocol) is an open-source standard for connecting AI applications to external systems.
> 
> <cite>https://modelcontextprotocol.io/docs/getting-started/intro</cite>

* Erlaubt Agenten, sich mit externen Datenquellen zu verbinden, z.B. Jira, Confluence, Figma, Datenbanken, ...
* So kann der Agent direkt die Jira-Tasks lesen, die er umsetzen soll und die Views "wie in Figma" implementieren
* SCS unterstützt derzeit nur Figma MCP, bei weiterem Bedarf bitte an TAs wenden

Einrichten: Je nach IDE/Tool unterschiedlich. In IntelliJ mit Github Copilot z.B. Werkzeug-Knopf > "Add more tools".

Im mcp.json eintragen:

```json
{
  "servers": {
    "figma": {
      "type": "http",
      "url": "https://mcp.figma.com/mcp"
    }
  }
}
```


Modelle und Kosten
------------------

* Modelle unterscheiden sich in Kontextgrösse, Fähigkeiten, Kosten und Geschwindigkeit
* GitHub Copilot: "Auto" wählt automatisch das passende Modell "for your request based on capacity and performance"
* Claude Opus, GPT 5.5: Teuer, aber sehr leistungsfähig, z.B. für komplexe Aufgaben, die viel Kontext benötigen
* Claude Sonnet, GPT 5.3 Codex, GPT 5.4, Gemini: Ausreichend für die meisten Aufgaben
* Claude Haiku, GPT mini: Günstig, für einfache Aufgaben mit sehr beschränktem Kontext
* GPT Modelle haben ein grösseres Default Kontextfenster als Claude Modelle
* Kontextfenster und Reasoning Effort lassen sich bei den neueren Modellen umstellen.
  * Je grösser, desto langsamer und teurer (da mehr Tokens verbraucht werden)

Jeder SCS-MA hat ein monatliches Budget von 10'000 AI Credits. Dies kann bei Bedarf von TAs erhöht werden.

*Tokendurchsatz ist in der SCS kein Entwickler-KPI!*


Kontext
-------

Der Agent baut sich über die Zeit einen Kontext auf. Dort fliesst ein:
* Der Prompt
* Files, die ihr der KI mitgebt
* Files, die die KI im Rahmen der Aufgabe liest
* Die Antwort, die die KI generiert

Auf das richtige Mass kommt es an!

* Zu wenig: Die KI versteht die Aufgabe oder die bestehende Codebase nicht und liefert unbrauchbare Ergebnisse
* Zu viel: Die KI verliert den Fokus, die Ergebnisse verlieren an Qualität und die Kosten steigen unnötig

Darum:
* Neues Chatfenster für neuen Task
* Bei related Tasks ggf. im gleichen Chatfenster bleiben, Kontext aufzubauen verbraucht Tokens
* Nicht zu viele FIles mitgeben, viele relevante Files kann die KI selbst finden


Prompting
---------

Grundsätzlich:
* Passendes Modell wählen (oder "Auto" benutzen)
* Kontext mitgeben (AGENTS.md, Skills, relevante Files, ...)
* Aufgabe formulieren
* Reasoning verfolgen
  * Mitdenken und abbrechen, wenn die KI falsch abbiegt/sich im Kreis dreht
* Ergebnis prüfen
  * ggf. Erklären lassen, kritisch nachfragen, nachbessern (lassen)
* *Code review durch weitere Person immer noch sinnvoll*
 

Was kann ich alles an die KI delegieren?
----------------------------------------

* Architektur entwerfen
* Dokumentation/Spezifikation
* Task-Planung
* Implementierung
* Refactoring
* Tests schreiben
* Debugging
* Code Review
* Ideen, Vorschläge, Alternativen entwickeln


Was für Ergebnisse kann ich erwarten?
-------------------------------------

* Normalerweise: Kompilierender Code, der
  * ungefähr die Aufgabe erfüllt
  * bestehende Patterns in der Codebase einhält
  * halbwegs State-of-the-Art ist und best practices einhält
* Dinge, die die KI nicht automatisch nebenbei macht:
  * Refactoring (z.B. Code-Duplikate reduzieren, Code vereinfachen, Komponenten/Funktionen auslagern, ...)"
    * *KI neigt zur Redundanz, Code wird mehr statt weniger → Auge drauf haben und aufräumen (lassen)!*
  * Code Review durchführen
  * Dokumentation schreiben
  * Strukturiert vorgehen, Workflows definieren
  * Kreativ werden, Alternativen vorschlagen
  * Sonstiges, was nicht direkt mit der Aufgabe zu tun hat


Wie gehe ich vor, damit die KI mir gute Ergebnisse liefert?
-----------------------------------------------------------

Überlegt euch:
* Welche Eigenschaften hat ein "gutes Ergebnis" für diese Aufgabe?
* Was für Ansprüche habe ich/das Team?

Dies vom Agenten explizit einfordern, z.B. über:
* Entsprechende Regeln in AGENTS.md oder Skills
* Entsprechende Anweisungen im Prompt
* Entsprechende Anweisungen in der Task-Planung/Kontext
* Weitere Prompts zum nachbessern und Fokus lenken, ggf. mit einem zweiten Agenten (Developer + Reviewer)

*Je mehr der Agent im gleichen Prompt beachten und erledigen soll, desto eher verliert er den Fokus und "vergisst" Dinge.*

Daher: Lieber klaren Fokus pro Prompt/Session setzen und mit weiteren Prompt/Agenten nachbessern.


Taskgrösse und Detailgrad der Anweisungen
-----------------------------------------

Taskgrösse und Detailgrad müssen nicht korrelieren.

* Je *strikter/konkreter die Zielvorgabe*, desto detaillierter muss der Prompt sein
* Je weniger Details im Prompt, desto mehr Spielraum hat der Agent für "Annahmen"
* *Statt sich auf eine Lösung einzuschiessen, kann es hilfreich sein, die KI um Vorschläge und begründete Empfehlungen zu bitten*


* Je grösser der Task, desto grösser die Gefahr, dass etwas untergeht oder missverstanden wird
* Je kleiner die Tasks, desto ineffizienter werden wir (Agent-Micromanagement)
* *Task-Planung mit KI durchführen*: Task beschreiben, Plan einfordern
  * Plan festhalten lassen: `docs/tasks/some_task.md`
  * Drüberlesen und korrigieren lassen!
