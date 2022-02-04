<#include meta/exercise.md>

---
author: Lucius Bachmann
title: "Arbeitsblatt: Testing"
---

Testing
===================================

For a no effort setup (just github account needed), you can use Gitpod with a vscode editor in the browser.  
[https://gitpod.io/#https://github.com/scs/jumpstart-docs](https://gitpod.io/#https://github.com/scs/jumpstart-docs)

Leap Year
--------

Implementiere die Funktion LeapYear::isLeapYear für Jahre > 0 nach folgenden Regeln.  
(Nein, nicht einfach java.time.Year::isLeap aufrufen)

1. Ein Jahr ist ein Schaltjahr, wenn es durch 4 Teilbar ist.
1. Ein Jahr ist trotz Regel 1 kein Schaltjahr, wenn es durch 100 Teilbar ist.
1. Ein Jahr ist trotz Regel 2 ein Schaltjahr, wenn es durch 400 Teilbar ist.

Roman Numerals
--------

Implementiere den RomanNumeralsConverter nach TDD.

1. Fange mit dem einfachsten Verhalten an
1. Schreibe einen Test
1. Implementiere das getestete Verhalten möglichst einfach.
1. Refactore den produktiven Code. Der Test muss grün bleiben.
1. Refactore den Test. Der Test muss grün bleiben.
1. Überlege dir das nächste einfachste Verhalten, das die Klasse haben soll
1. Repetiere ab "Schreibe einen Test", bis die Spezifikation erfüllt ist.

Spezifikation:
Die Methode RomanNumeralsConverter soll für die arabischen Zahlen 1 - 3999 die entsprechenden römischen Zahlen ausgeben.
Für Zahlen > 4000 gäbe es weitere Regeln, die sind momentan nicht wichtig.
Für Zahlen `<=` 3999 gelten folgende Regeln:

\newpage

+ Falls die arabische Zahl einem der folgenden Werte entspricht, ist die römische Zahl die entsprechende einzelne Ziffer.

  | Wert | Ziffer
  | - | -
  |   1 | I
  |   5 | V
  |   10 | X
  |   50 | L
  |   100 | C
  |   500 | D
  |   1000 | M

+ Die Ziffern können durch aneinanderreihen addiert werden. Dafür startet man mit der Ziffer mit dem höchsten Wert,
  und hängt dann falls nötig Ziffern mit tieferem Wert an. Man soll Ziffern mit möglichst hohem Wert verwenden.  
  (XX für 20 statt VVVV)

  Römische Zahl | Addition | Summe
   | - | - | -
   |   II | 1 + 1 | 2
   |   XXVIII | 10 + 10 + 5 + 3 | 28

+ Ausnahme: Subtraktionsregel:
    1. Für 4 und 9 verwendet man die Subtraktionsregel: IV = 5 - 1 = 4, IX = 10 - 1 = 9
    1. Für 40 und 90 auch, dort zieht man 10 ab: XL = 50 - 10 = 40, XC = 100 -10 = 90
    1. für 400 und 900 dasselbe mit 100 abziehen.

MovieRental Teil 1: Refactoring mit TDD
--------

In dieser Übung geht es um einen Videoverleih. Im Laden sind verschiedene Filme, abgebildet durch die Klasse Movie,
verfügbar. Ein:e Kunde:in (Customer) kann einen Film (abgebildet durch Rental) für ein oder mehrere Tage
ausleihen.
Es gibt für die Filme verschiedene Preiskategorien (PriceCode). Für die Kunden:innen möchtest du eine Übersicht
über die momentan offenen Beträge drucken. Auf diese Funktionalität kannst du mit dem CustomerController zugreifen.
Du kannst das ganze auch interaktiv ausprobieren. Wenn du `JumpstartApplication::main` ausführst, dann ist auf
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) ein simples ui, mit dem du Filme,
Kunden:innen und Ausleihungen erfassen und die Quittung ausgeben kannst. (Das UI verwendet die Lösung als Implementation).

Leider ist die Funktionalität für den Belegdruck in der Entity Customer implementiert. Weiter ist die Logik für
die Preisberechnung vermischt mit der Formatierung des Textes für den Beleg. Der Beleg soll jetzt auch
als JSON zur Verfügung gestellt werden, damit es mit einem WebFrontend dargestellt werden kann. Das Interface
für das WebFrontend ist schon definiert, siehe `CustomerController::getJsonInvoice` und `RentalStatement`.
Um Daten als JSON zu serialisieren, verwenden wir in Java Libraries. So können wir einfach eine Klasse definieren,
und die Library (In diesem Fall Spring zusammen mit Jackson) konvertiert diese Klasse automatisch in JSON.

In dieser Übung wirst du geführt zuerst die bestehende Funktionalität unter Test bringen und dann auslagern,
um die zusätzlich gewünschte Funktionalität zu implementieren. Für diese Übung verwenden wir TDD, d.h. es werden
nur Repositories (die Datenbank) gemockt.

### Funktionalität unter Test bringen

Vervollständige die Tests in SolutionCustomerControllerTest, so dass die Code Coverage in
CustomerController und Customer fast 100% beträgt.
(Bis auf die Methode `CustomerController::getJsonInvoice` ist alles getestet.)

### Verschieben des Aufrufs

Der Code für die Preisberechnung und Formatierung ist momentan in der Entity. Entities sollen aber keine Logik
enthalten. Wir wollen die Funktionalität schlussendlich auf die beiden Klassen `RentalStatementFactory` und
`RentalStatementTextFormatter` aufteilen. Dabei wollen wir bei jedem Schritt die Testabdeckung behalten oder sogar
erweitern.  
Implementiere die Funktionalität von `RentalStatementFactory` mit `return customer.statement()`. Übergib dann die Klasse
`RentalStatementFactory` im Konstruktor der Klasse `CustomerController`.  
Ändere dann die Methode `CustomerController::getInvoice`: statt direkt `customer.statement()`
aufzurufen, rufe `RentalStatementFactory::createStatement` auf.  
Lasse den `CustomerControllerTest` laufen. Sind die nötigen Pfade abgedeckt?

### Inline von `Customer::statement`

Jetzt kannst du mit Test Coverage die Methode `RentalStatementFactory::createStatement` umbauen.
Wir fangen an, indem wir die Methode `Customer.statement()` inlinen (`Ctrl + Alt + N`). Dies ist ein von der IDE
unterstütztes Refactoring, somit sollte die Funktionalität nicht ändern. Aber du überprüfst das natürlich, indem du
die Tests laufen lässt. Wir haben jetzt eine neue Klasse mit Funktionalität, aber ohne Unittests. Um die Klasse
`RentalStatementFactory` später refactoren zu können, müssen wir sie auch wieder unter Test bringen. Auch bei TDD
sollte man die Klassen nicht indirekt (z.b. über den `CustomerControllerTest`) testen.  
Schreibe den Unittest für die Klasse `RentalStatementFactory`

### Refactoring von `RentalStatementFactory::createStatement`

Jetzt kannst du die beiden Concerns (Preisberechnung und Formatierung) innerhalb der Methode trennen.
Um die Preisberechnung zu speichern, kannst du schon die Klassen `RentalStatement` und `RentalStatementMovie` verwenden.
Lasse wie immer danach die Tests laufen.

### Extrahieren der Formatierung

Jetzt extrahieren wir den Formatierungsteil aus der Klasse `RentalStatementFactory` in die Klasse
`RentalStatementTextFormatter`. In einem ersten Schritt verwenden wir aber noch `RentalStatementTextFormatter` in der
Klasse `RentalStatementFactory`, damit wir die Unittests von `RentalStatementFactory` noch verwenden können.
Verschiebe die Formatierungslogik von `RentalStatementFactory` in `RentalStatementTextFormatter`, und übergib die Klasse
`RentalStatementTextFormatter` im Konstruktor der Klasse `RentalStatementFactory`. Rufe dann in
`RentalStatementFactory::createStatement` `RentalStatementTextFormatter::format` auf. Jetzt haben wir wieder eine neue
Klasse ohne Unittests. Schreibe Unitests für die Klasse `RentalStatementTextFormatter`. Um dir das Leben einfacher zu
machen, ist schon der `RentalStatementBuilder` vorbereitet. Die Tests für `RentalStatementTextFormatter` sind jetzt
einfacher zu schreiben, da du dir nicht mehr um die Preisberechnung Gedanken machen musst.
Du gibst ein paar Strings und Floats als Input, und überprüfst, dass Newlines und Tabs richtig dazwischen gesetzt
werden.

### Ändern des Interfaces von `RentalStatementFactory`

Wir wollen ja das `RentalStatement` selbst auch auf der API ausgeben können. Deshalb wollen wir, dass
`RentalStatementFactory::createStatement` ein `RentalStatement` zurückgibt. Dann können wir selber im Caller
(`CustomerController`) entscheiden, ob wir das noch formatieren wollen.
Dazu musst du folgendes machen:

1. Entferne den Aufruf von `RentalStatementTextFormatter::format` aus  
   `RentalStatementFactory::createStatement`
1. Das Interface ändert sich, also musst du jetzt die Tests von `RentalStatementTextFormatter` anpassen.
1. In `RentalStatementFactory` brauchst du jetzt keinen `RentalStatementTextFormatter` mehr, du kannst ihn als Konstruktor
   Parameter entfernen.
1. CustomerController braucht jetzt aber `RentalStatementTextFormatter`, füge ihn deshalb hier als Konstruktor Parameter
   hinzu.
1. Ändere den Code in `CustomerController::getInvoice` so, dass du zuerst das `RentalStatement` mit
   `RentalStatementFactory::createStatement` erzeugst, und dann dieses mit `RentalStatementTextFormatter::format`
   formatierst. Jetzt sollte alles wieder kompilieren und alle Tests grün sein.

### Implementieren von `CustomerController::getJsonInvoice`

Implementiere jetzt den Test `CustomerControllerTest::return_json_invoice` und die Methode
`CustomerController::getJsonInvoice`.

### Refactoring der Tests

Jetzt musst du in `CustomerControllerTest` nicht mehr alle Fälle für die Formatierung beachten.
Hier brauchst du die beiden notFound Tests für die `getInvoice` Methode plus einen Erfolgsfall.
Die Spezialfälle für die Preisberechnung kannst du jetzt in `RentalStatementFactoryTest` testen. Die Spezialfälle
für die Formatierung gehören in `RentalStatementTextFormatterTest`. Plus du hast die zusätzliche Funktionalität
implementiert. Überlege, welche Tests du jetzt verschieben/löschen kannst, und mache das dann.

\newpage

BankKata
--------

In dieser Übung sollst du die Transaktionen eines Bankkontos in der Konsole ausgeben.
Und zwar sollen die Transaktionen von neu nach alt aufgelistet werden.
Weiter soll der Kontostand nach jeder Transaktion ausgegeben werden.

Beispiel: Gegeben seien folgende Transaktionen:

1. 2021-01-01 Einzahlung: 100
1. 2021-06-12 Auszahlung: 75
1. 2021-06-15 Einzahlung: 1500
1. 2021-06-16 Auszahlung: 250

Dann soll auf der Konsole folgendes ausgegeben werden:

```shell
Date || Amount || Balance
2021-06-16 || -250 || 1275
2021-06-15 || 1500 || 1525
2021-06-15 || -75 || 25
2021-01-01 || 100 || 100
```

Das Ziel ist, dass du Mockito.mock, Mockito.when, Mockito.verify und Mockito.inOrder verwendest.
Mockito.inOrder brauchst du, um die Reihenfolge der Methodenaufrufe zu überprüfen. Mockito.verify überprüft nur,
ob irgendwann in deinem Test die Methode mit den definierten Parametern aufgerufen wird.
Du brauchst für diese Übung keine MatcherAssert.assertThat von Hamcrest.

\textbf{Es ist nicht erlaubt, das Interface der Klasse Account zu ändern.} Interface nicht ändern heisst: keine public oder
protected Methoden hinzufügen oder entfernen. Die Signatur der Methoden darf nicht geändert werden.
Du darfst einen public Konstruktor hinzufügen.

\lstinputlisting[language=Java]{code/src/main/java/ch/scs/jumpstart/bankkata/exercise/Account.java}

### Grundaufgabe

Vorgehen:

1. Schreibe einen Acceptance Test für die Klasse Account.
    1. Du wirst dafür die Clock mocken müssen. Du musst also eine Wrapper Klasse (z.b. Clock) schreiben,
    bei der die Klasse Account das Datum für die Transaktion abfragen kann.
    1. Du wirst dafür den Konsolenoutput (in Java statisch mit System.out.println) überprüfen müssen. Auch hier brauchst
    du eine Wrapper Klasse.
    1. Überlege dir vor dem Implementieren des Tests das Design. Soll die Klasse Account auch den Konsolen Output
    formattieren? Welche Klasse sorgt dafür, dass die Transaktionen von neu nach alt sortiert werden?
    Welche Klasse summiert den Kontostand? Was sind die Interfaces der verschiedenen Klassen?
1. Implementiere die Klasse Account Test driven. Du brauchst für die Klasse Account jetzt zusätzlich einen Unittest.
1. Implementiere alle weiteren Klassen aus deinem Design Test driven. Wenn du den letzten Unittest der letzten Klasse
implementierst, sollte der Acceptance Test auch grün werden. Danach das Refactoring nicht vergessen.

### Zusatzaufgabe: Formatierung

#### Datum formatieren
\
Formatiere das Datum so, wie im deutschen Sprachgebrauch üblich:  
06.06.2016 statt 2016-06-06

#### Zahlen ausrichten
\
Richte die Zahlen so aus, dass immer die gleiche Stelle übereinander ausgegeben wird.
Das Konto ist limitiert auf 8 stellige Beträge.

```shell
Date       ||   Amount ||  Balance
16.06.2021 ||     -250 ||     1275
15.06.2021 ||     1500 ||     1525
12.06.2021 ||      -75 ||       25
01.01.2021 ||      100 ||      100
```

MovieRental Teil 2: Integrationstests
--------

Jetzt geht es darum, für die MovieRental Funktionalität einen Integrationstest zu schreiben.
Die erste Frage, die man sich stellen muss: Was möchte ich im Integrationstest testen, was im Unittest?
In dieser Übung ist die Frage schon für dich beantwortet, wir diskutieren am Ende noch zusammen,
ob die Aufteilung sinnvoll war. Ignoriere hier mal die Spring Magic, sondern copy paste die Verwendung
von WebTestClient und passie sie wenn nötig an.

### Implementieren der Tests

Implementiere die momentan leeren Tests in `SpringCustomerControllerIT`.
Es kann sein, dass dann ein Test fehlschlägt, weil Daten von einem vorherigen Test noch in der Datenbank sind.
Stelle sicher, dass vor oder nach jedem Test ein definierter Stand in der Datenbank ist.

### Zusatzaufgabe Aufteilung Integrationstest/Unittest

Überlege dir, welchen Teil der Funktionalität du mit einem Integrationstest, welchen du mit einem Unittest getestet
hättest. Bist du mit der Lösung einverstanden?

### Zusatzaufgabe Ungültiger Request

Schreibe einen Test, bei dem du ein ungültiges JSON an einen POST Endpoint sendest.
Welchen HTTP Statuscode erwartest du, welcher kommt zurück?
Finde mit einer Suchmaschine heraus, wie du den erwarteten HTTP Status Code zurückgeben kannst.
Frameworks und Libraries sind super, man muss sie aber auch richtig verwenden. Und manchmal verhalten sie sich
in gewissen Edge Cases anders, als du dir das wünschst. Deshalb sind Integrationstests wichtig, bei denen man die
Library/das Framework auch testet.
