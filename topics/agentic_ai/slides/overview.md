Überblick
=========


Inhalt
------
* Ziele dieses Moduls
* Was ist "Agentic AI"?
* Chancen und Risiken
* Chancen nutzen, Risiken minimieren - wie?


Ziele dieses Moduls
-------------------

* Ihr wisst, wo und wie ihr KI-Tools in der SCS einsetzen dürft bzw. wann und wen ihr fragen müsst
* Ihr wisst, wie ihr euer Tool im Projekt einrichtet und worauf es im Team ankommt
* Ihr wisst, wie ihr welche Aufgaben an KI-Tools delegiert (Vorgehen, Kontext, Umfang, Detailgrad, Art)
* Ihr wisst, welches Modell für welche Aufgaben geeignet ist
* Ihr wisst, worauf ihr sonst noch achten müsst (Kosten, Sicherheit, Qualität, Burnout)
* Ihr fühlt euch sicher (confident) beim Einsatz von KI-Tools


Was ist "agentic AI"?
---------------------

> Agentic AI is an artificial intelligence system that can accomplish a specific goal with limited supervision. It consists of AI agents—machine learning models that mimic human decision-making to solve problems in real time.
> 
> <cite>https://www.ibm.com/think/topics/agentic-ai</cite>
 
Für uns konkret: Ein Tool, effizienter Code zu schreiben. (Achtung: Mehr Code in weniger Zeit ist nicht besserer Code in weniger Zeit!)


Chancen
-------

* Wir werden schneller
  * weniger Projektüberzüge → mehr Bonus WENN wir die gleiche Anzahl Stunden an den Kunden verkaufen
  * wir werden günstiger (leider nicht unbedingt konkurrenzfähiger, wenn es die Konkurrenz genau so macht)
* Wir haben mehr Spass, wenn wir unliebsame Fleissarbeiten an KI delegieren
* Unterstützung in Lösungsfindung/Brainstorming: Wir finden Wege/Ideen, auf die wir alleine nicht gekommen wären → Ähnlich wie mit einem Kollegen zusammenzusitzen


Risiken
-------

* Sicherheit: Tool mit Zugriff auf die Shell.
* Qualität
  * Die KI bedient sich "häufiger" Patterns. Das sind nicht immer die besten.
  * Im Review neigt man dazu, Code zu akzeptieren, der nur "ok" ist: Man versteht ihn nicht 100%, es hat überflüssige Sachen drin oder sogar Bugs.
* Weniger wartbarer Code: KI neigt zur Redundanz und macht kein Refactoring von alleine.
* Verlernen eigener Fähigkeiten: Fähigkeiten, die man nicht benutzt, stauben ein. Problemlösefähigkeiten (die man im Review nicht nutzt!) sind da keine Ausnahme.
* Mentale Gesundheit: Ich delegiere meinen Job an die KI, produziere immer mehr Code in weniger Zeit, muss mehr Code in weniger Zeit reviewen, den ich immer weniger verstehe, bis ich vollständig von der KI für die Weiterentwicklung der Codebasis abhängig bin.


Chancen nutzen, Risiken minimieren - wie?
-----------------------------------------

* Schätzt wie bisher, bis ihr KI-Tools routiniert einsetzt. Überprüft eure Schätzungen. Erst dann könnt ihr sie anpassen.
* Prüft jedes Command, was die KI ausführen will. Bis zum Ende scrollen!
* Setzt die Entwicklungsumgebung mit KI ggf. in einem Container auf.
* Verfolgt, was die KI macht. Wenn ihr mitdenkt, könnt ihr Irrwege früher erkennen und Ergebnisse besser nachvollziehen.
* Review: Lasst euch den Code erklären. Stellt kritische Fragen. Fordert Refactorings (z.B. "reduce code duplication in....") ein.
* Gebt nicht alles ab - macht selber, was euch Spass macht. Übergebt Fleissarbeiten, bei denen ihr nicht/wenig nachdenken müsst.
* Fragt die KI auch mal nach Ideen, nicht nur nach Code.
