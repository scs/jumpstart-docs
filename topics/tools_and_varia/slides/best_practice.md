Best Practice
=============


Edge Case Handling
------------------

\colBegin{0.5}

*Arten:*

* Out of bound (Array)
* Numerisch (z.B. Division durch 0)
* Business-Logik
* Endlos-Schleifen

\colNext{0.5}

*Wie erkennen und handhaben?*

* Return-Werte
* Exceptions
* Assertions
* (Unit-) Tests

\colEnd

### Pro Projekt entscheiden

* Gemeinsame Strategie definieren $\to$ Teil des *Code-Styles*
* *Tools* (Library verwenden oder selber bauen?)
* Welche Checks sind in *welchen* Builds/Releases nötig?


Assertions
----------

* Überprüfen von *Pre-/Post-Conditions*
* Bei Fehler *stoppt* das Programm
* Sollten möglichst genau *loggen*, was passiert ist
* Können unterschiedlich *streng* sein:
  * *immer aktiv*
  * nur im *Debug-Build*
  * immer aktiv aber *nur loggen* und nicht abbrechen

~~~{.cpp}
int ExampleFunction(unsigned index, const std::vector<int>& array) {
  AssertMsg(index < array.size(),
    "Out of bound access: index={} size={}",
    index, array.size());
  
  const auto value = array[index];
  AssertDebugOnly(value > 100);   // busines logic
  return value;
}
~~~


Logging
-------

* Wichtig für *Debugging* von Problemen *im Betrieb*
* Logging ist ein *zentraler* Bestandteil jeder Applikation
  * In Architektur/Design *einplanen*
  * So *wenig invasiv* wie möglich
* Unterschiedliche *Orte* für Log-Daten
  * Datei
  * Log-Dienst (`journald`, `syslog`)
  * Remote-Log (`rsyslog`)

Logging Features
----------------

*System:*

* Log-File-Rotation
* Log Searching/Filtering

*Logger:*

* Statischer Kontext:
  * Klasse/Funktion
  * Dateiname/Zeilennummer
  * Inhalt von konstanten Variablen (z.B: Instanz-Name)
* Dynamischer Kontext:
  * Zeitstempel
  * Inhalt von dynamischen Variablen (z.B: Netzwerk-Port)


Log-Level
---------

----------------------------------   -------------------------------------
Error (Emergency, Alert, Critical)   Not recoverable
Warning                              Not expected but possibly recoverable
Info (Notice)                        Sporadic high level information
Debug                                Sporadic detail information
Trace (not enabled by default)       High rate or even repeating
----------------------------------   -------------------------------------
