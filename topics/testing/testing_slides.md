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
> Test Driven Development (TDD)

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
1. Tests für die extrahierten Klassen in die Unittests für die extrahierten Klassen verschieben.

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

Übung Unittest einer Funktion (LeapYear)
-------

Übung Unittest einer Funktion (RomanNumerals)
-------

Demo Parametrized Tests (RomanNumerals)
-------

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

Übung TDD mit mehreren Klassen (PasswordValidator)
-------

Zusammenfassung TDD mit mehreren Klassen (PasswordValidator)
-------

* Funktionalität in neue Klasse auslagern + gleich integrieren, Test bleibt grün
* Neue Tests zur ausgelagerten Klasse hinzufügen, alle Tests bleiben grün
* Refactoring dazwischen nicht vergessen
* Am Schluss Tests für detailliertes Verhalten an den Ort der Implementation verschieben

Übung Mocking
-------

Zusammenfassung Mocking 1
-------

* Wenn man eine Methode eines Mocks nicht konfiguriert, dann gibt sie einen default zurück  
(null, 0, false oder leere collection)
* Mit  
`verify(mock[, verificationMode]).method(argumentMatcher);`  
verifizieren, dass eine void Methode aufgerufen wurde
* Mit  
`when(mock.method(argumentMatcher)).thenReturn(returnValue)`  
eine Methode stubben.  
Wenn die Methode mit einem Argument aufgerufen wird, auf das `argumentMatcher` matched,
dann gibt sie von jetzt an `returnValue` zurück.

Zusammenfassung Mocking 2
-------

Mit

```
when(mock.method(argumentMatcher)).thenReturn(returnValue, returnValue2)
```

oder

```
when(mock.method(argumentMatcher))
    .thenReturn(returnValue)
    .thenReturn(returnValue2)
```

kann man beim ersten Aufruf returnValue und beim zweiten returnValue2 zurückgeben.

Zusammenfassung Mocking 3
-------

Mit

```
  verifyNoMoreInteractions(mock[, mock2...]);
```

für keine weiteren Interaktionen
oder

```
  verifyNoInteractions(mock[, mock2...]);
```

für gar keine Interaktionen
prüfen, ob keine unbeabsichtigten Interaktionen mit Objekten passieren.


Zusammenfassung Mocking 4
-------

Mit

```
    var inOrder = inOrder(mock);
    inOrder.verify(mock).method(argumentMatcher1);
    inOrder.verify(mock).method(argumentMatcher2);
```

Kann die Reihenfolge der Aufrufe geprüft werden.

Wording im Zusammenhang mit Mocking 1
-------

* *Dummy Object*: Ein Parameter oder Abhängigkeit die im Test nicht verwendet wird.  
Das Verhalten des Objekts ändert den Test nicht. (z.b. das Object wird einfach durchgereicht)
* *Fake Object*: Eine Implementation des gleichen Interfaces wie die reale Implementation,
aber möglichst einfach. z.b. Wird nur das Verhalten implementiert, das auch im Test gebraucht wird.
(z.b. implementation eines DB Repositories mit einer Liste)
* *Stubs*: Das Verhalten einer Methode wird vorgegeben für einen oder mehrere Tests.
Dies wird meistens nicht zwischen mehreren Testklassen geteilt, sondern für einzelne Testklassen
neu definiert. Dafür wird meist ein Framework verwendet, das dies Unterstützt. (z.b. Mockito, Jest)

Quelle: Effective Software Testing by Mauricio Aniche

Wording im Zusammenhang mit Mocking 2
-------

* *Mocks*: Wie *Stubs*, sie speichern aber auch, wie oft sie aufgerufen wurden. Danach kann überprüft werden,
wie oft eine Methode mit welchen Parametern aufgerufen wurde.
* *Spies*: Wrapped das reale Objekt, damit man Interaktionen damit aufzeichenn kann.
Das verhalten des realen Objekts wird nicht geändert. Danach kann überprüft werden,
wie oft eine Methode mit welchen Parametern aufgerufen wurde.
* *Simulator*: Versucht das Verhalten des originalen Interfaces mit einfacheren Mitteln zu simulieren.
Je nach dem kann das Verhalten auch zur Laufzeit mittels manuellem User Input geändert werden.
z.b. das Repository für eine Entity mit einer Liste in Memory implementieren.


Quelle (ausser Simulator): Effective Software Testing by Mauricio Aniche

Implementation von Mockito
-------

* Mockito.mock(javaClassOrInterface.class): Implementiert das Verhalten von *Mocks*
* Mockito.spy(javaObject): Implementiert das Verhalten von *Spies*
* Mockito.when(mockOrSpy): Implementiert das Verhalten von *Stubs* für *Mocks* oder *Spies*
(*Spy* und *Stub* widersprechen sich eigentlich, ist aber so bei Mockito)
* Mockito.verify(mockOrSpy): Wird verwendet, um die Aufgezeichneten Interaktionen zu verifizieren.

Teil 2
-------

\huge
> Behaviour Driven Development (BDD)

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

Links
-------
[Effective Software Testing]: https://www.manning.com/books/effective-software-testing

[Designing Data Intensive Applications]:https://dataintensive.net/
[Pragmatic Programmer]:https://pragprog.com/titles/tpp20/the-pragmatic-programmer-20th-anniversary-edition/
[Your code as a crime scene]:https://pragprog.com/titles/atcrime/your-code-as-a-crime-scene/
[Kevlin Henney Youtube Query]:https://www.youtube.com/results?search_query=kevlin+henney
[Dave Farley]:https://www.youtube.com/@ContinuousDelivery
[Fireship]:https://www.youtube.com/@Fireship
[The Primeagen Youtube channel]:https://www.youtube.com/@ThePrimeagen
[ArjanCodes Youtube channel]:https://www.youtube.com/@ArjanCodes
[Sandro Mancuso Youtube channel]:https://www.youtube.com/@SandroMancuso
[Sandro Mancuso Youtube query]:https://www.youtube.com/results?search_query=sandro+mancuso
[Devvox]: https://www.youtube.com/channel/UCsVPQfo5RZErDL41LoWvk0A
[InfoQ]: https://www.youtube.com/@infoq
[Martin Fowler The Practical Test Pyramid]:https://martinfowler.com/articles/practical-test-pyramid.html
[Design Patterns]:https://github.com/iluwatar/java-design-patterns


* Bücher
  * [Effective Software Testing]
  * [Designing Data Intensive Applications]
  * [Pragmatic Programmer]
  * [Your code as a crime scene]
* Youtube
  * Kevlin Henney [Kevlin Henney Youtube Query]
  * Dave Farley [Dave Farley]
  * Fireship (Frontend) [Fireship]
  * The Primeagen (JS + Rust) [The Primeagen Youtube channel]
  * ArjanCodes (Python) [ArjanCodes Youtube channel]
  * Sandro Mancuso [Sandro Mancuso Youtube channel],  
  [Sandro Mancuso Youtube query]
  * [Devvox]
  * [InfoQ]

Links 2
-------

* Websites and Blogs
  * Martin Fowler, e.g. [Martin Fowler The Practical Test Pyramid]
  * Design patterns, one example: [Design Patterns]
