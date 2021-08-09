<#include meta/slides.md>

Testing
==============

Ziel
-------

* Du hast für eine Applikation Unittests geschrieben
* Du kannst TDD und BDD anwenden, und kennst die Unterschiede
* Du kennst die Unterschiede zwischen Unittest, Integrationstest und Systemtest
* Du kennst folgende Techniken, um Tests wartbarer zu gestalten:
  * Setup/Teardown für Tests
  * Parametrisierte Tests
  * Enclosed Tests

Teil 1
-------

\huge
> TDD

Was helfen einem Tests?
-------

* Feedback Cycle kurz halten
* Reproduzierbare Resultate
* Dokumentation
  * des Interfaces
  * des Requirements
* Reduziert (bis zu eliminiert) manuelle Regressionstests
* Ermöglicht kürzere Release/Deployment Cycles
* Erhöht die Zuversicht nach Änderungen
* Features "schützen"
* Hilft beim Design
  * Unittests sagen nicht, dass ein Design gut ist
  * Aber nicht testbares Design ist sicher schlecht
* Je mehr Code automatisiert ausgeführt wird, desto besser

TDD
-------

* Alias Detroit/Chicago School, Classicist Approach
* Test from inside out
* Avoid Mocks
* Design wächst bei der Implementierung, könnte Overengineering verhindern.

Ablauf:

1. Test schreiben
1. Klasse schreiben, damit der Test kompiliert und passed
1. Refactor
1. Weiteren Test schreiben
1. Klasse erweitern, damit der Test durchkommt
1. Refactor, falls die Klasse jetzt zu gross ist, zusätzliche Klassen extrahieren

Red Green Refactor
-------

![TDD](images/tdd.pdf){height=95%}

Definition Unittest TDD
-------

* Setup ist günstig
* Verwendet keine shared Ressourcen (Datenbanken, Ports, Filesystem)  
Ausser natürlich Memory und CPU
* Testet eine Unit (Klasse in OO, Funktion in FP)  
Dependencies wurden schon von anderen Unittest getestet.
* Rule of Thumb: Laufzeit < 1s

Naming von Tests
-------

1. Variante:  
`public void testStackPopsNullWhenEmpty()`  
Schlecht lesbar da Wörter zusammenkleben.  
Kein visueller Unterschied zwischen Leerzeichen und zusammengesetztem Wort.  
Kein visueller Unterschied zwischen Arrange, Act und Assert.

2. Variante:  
`public void whenStackEmpty_thenPopsNull()`  
Schlecht lesbar da Wörter zusammenkleben.  
Kein visueller Unterschied zwischen Leerzeichen und zusammengesetztem Wort.  
Das Resultat ist eigentlich wichtiger als das Act und das Assert, es sollte zuerst kommen.

Naming von Tests
-------

In diesem Block präferierte Variante:  
`public void pops_null_when_empty()`  
Gut lesbar, mit dem Testnamen sogar ein englischer Satz.  
Das wichtigste ist zuerst.  
Zeigt einem schneller, wenn die Klasse zuviel macht.

Demo Unittest einer Funktion (MyStack)
-------

Zusammenfassung Demo Unittest einer Funktion (MyStack)
-------

* Mit möglichst einfachem Verhalten starten, dann zu komplexeren erweitern
* Mit dem Testnamen das getestete Verhalten beschreiben
* Von der Assertion starten, und den Test rückwärts aufbauen, um unnötigen Code zu vermeiden
* Alles was nicht zum Test gehört in Konstanten auslagern

Übung Unittest einer Funktion (RomanNumerals)
-------

Demo Parametrized Tests (RomanNumerals)
-------

Parametrized Tests in anderen Sprachen: PHP + PHPUnit
-------

\lstinputlisting[language=PHP, firstline=228, lastline=252, basicstyle=\tiny]{assets/CampCollaborationTest.php}

Parametrized Tests in anderen Sprachen: JavaScript + Jest 1
-------

\lstinputlisting[language=C, firstline=17, lastline=44, basicstyle=\tiny]{assets/EDatePicker.spec.js}

Parametrized Tests in anderen Sprachen: JavaScript + Jest 2
-------

\lstinputlisting[language=C, firstline=45, lastline=70, basicstyle=\tiny]{assets/EDatePicker.spec.js}

Übung Unittest einer Funktion Parametrized (LeapYear)
-------

Demo Nested verwenden (LeapYear)
-------

Zusammenfassung Nested verwenden (LeapYear)
-------

* Mit nested Tests können unterschiedliche Verhalten gruppiert werden
* Ziel: Nur mit der Signatur des Tests kann das Verhalten der Klasse aufgezeigt werden

Arrange, Act, Assert
-------

* Arrange, Act und Assert in Tests trennen mit einer Leerzeile.
* Arrange: Setup für den Test, je nachdem Manipulation des States.
* Act: Trigger, der das zu testende Verhalten auslöst.
* Assert: Überprüfen, dass nur das gewünschte Verhalten ausgelöst wurde.
* *Kontrovers:* Diese Teile können auch wiederholt werden, falls ein Verhalten mit mehreren Schritten getestet wird.

Übung TDD mit mehreren Klassen (MovieRental Teil 1)
-------

Zusammenfassung TDD mit mehreren Klassen (MovieRental Teil 1)
-------

* Funktionalität in neue Klasse auslagern + gleich integrieren, Test bleibt grün
* Neue Tests zur ausgelagerten Klasse hinzufügen, alle Tests bleiben grün
* Refactoring dazwischen nicht vergessen
* Am Schluss Tests für detailliertes Verhalten an den Ort der Implementation verschieben

Teil 2
-------

\huge
> BDD

BDD
-------

* Alias London School, Mockist Approach
* From Outside in
* Interaktion zwischen Units werden getestet
* Design der Interaktion zwischen Klassen wird vorab definiert

BDD Ablauf
-------

Ablauf:

1. Zuerst wird der Test für das Szenario geschrieben
1. Damit der kompiliert, müssen die Klassen/Methoden der Dependencies erstellt werden
1. Dann werden für die Dependencies Tests geschrieben
1. Damit diese kompilieren, müssen wieder Klassen/Methoden der Dependencies erstellt werden
1. Sobald man bei allen Klassen angekommen ist, die keine Dependencies mehr haben fängt man mit der Implementation an
1. Sobald man alle Tests der Units für eine übergeordnete Komponente geschrieben hat,
sollte der übergeordnete Test grün werden.
1. repeat

Definition Unittest BDD
-------

* Setup ist günstig
* Verwendet keine shared Ressourcen (Datenbanken, Ports, Filesystem)  
Ausser natürlich Memory und CPU
* Testet eine Unit (Klasse in OO, Funktion in FP)  
* Dependencies werden gemockt
* Rule of Thumb: Laufzeit < 1s

Red Green Refactor on steroids
-------

![BDD](images/bdd.pdf){height=120%}

Demo Test Klasse mit State (BankKata)
-------

Übung BDD (Bankkata)
-------

Levels for Tests
------

\centering
![testing_scopes](images/testing_scopes.pdf){height=95%}

Integrationstest
-------

* Ziel: Die Hauptfeatures der Komponenten funktionieren auch mit DB, Filesystem oder Sockets
* Es werden nicht mehr alle Fehlerfälle der Subkomponenten getestet
* Abwägen zwischen Laufzeit und Testabdeckung

Demo Integrationstest (RegaGis, AddressLookupServiceIT)
-------

Fragen bei Integrationstests
-------

* Separierung Integrationstests/Unittests
  * Maven:
    * `maven-surefire-plugin (*Test, target: test)`
    * `maven-failsafe-plugin (*IT, target: verify)`
* Welche Aspekte möchte ich testen?
  * z.b. echte WebRequests vs in Memory aufruf von Controllern
  * Muss das Verhalten einer Library auch getestet werden (z.b. wenn man die Library updated)
  * Muss die korrekte Verwendung der Library getestet werden (z.b. DB Transaktion richtig öffnen und schliessen)
* Wie viele Aspekte möchte ich in einem Test testen?
* Wie stelle ich sicher, dass ein definierter Stand beim Teststart da ist?
* Sind am Ende des Tests alle Ressourcen wieder aufgeräumt, auch bei einem Fehlschlag?

Fixtures
-------

* Weniger noise im Testcode
* Durch Fixtures entstehen Abhängigkeiten zwischen den Tests

Beispiel:

* Fixtures mit Dependencies: [CampTestData.php](https://github.com/ecamp/ecamp3/blob/0d3dada43fad56b23e7d7498b3c64430757c718f/backend/module/eCampCore/test/Data/CampTestData.php)
* Abhängigkeiten zwischen Fixtures (Neu 2 Camps) [commit](https://github.com/BacLuc/ecamp3/commit/0d3dada43fad56b23e7d7498b3c64430757c718f)
* GeoObjectStatusControllerIT

Levels for Tests
------

\centering
![testing_scopes](images/testing_scopes.pdf){height=95%}

Systemtests
-------

* Noch weniger Details als beim Integrationstest werden getestet
* Das ganze System wird möglichst wie in Produktion Hochgefahren
  * Richtiges DBMS
  * Echte Web Requests
* Es können auch mehrere Systemteile (z.b. unterschiedliche Services)  
hochgefahren werden.
* Lokale Ausführung oft nicht mehr möglich

Demo Systemtest (PTTS Frontend)
-------

Demo Systemtest (PTTS Backend Editor WebUI)
-------

Fragen bei Systemtests
-------

* Wie interagiere ich mit dem System?
* Wo hänge ich Simulatoren ein?
* Welchen State hat die Applikation, wenn ein Test beginnt?
  * Reset auf definierten Stand vor dem Test
  * Tests hängen voneinander ab
* Wie lade ich Testdaten?
* Laufzeit der Tests?
* Parallelisierung der Tests?

Reserve
-------

Übung BDD (TrailerDetector)
-------
