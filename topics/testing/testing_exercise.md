<#include meta/exercise.md>

---
author: Lucius Bachmann
title: "Arbeitsblatt: Testing"
---

Testing
===================================

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

\newpage

Mocking
--------

In dieser kurzen Übung sollst du `Mockito.mock`, `Mockito.when` und `Mockito.verify` verwenden, damit
du dies in den weiteren Übungen (und auch sonst) verwenden kannst.
Dazu verwenden wir die Klasse `MoviePrinter` und entwickeln deren Funktionalität test driven mit
`MoviePrinterTest`. Wie du siehst hat die Klasse die 2 Dependencies `MovieRepository` und `Printer`.
Mit `MovieRepository` holt man sich die movies, die man danach mit `Printer` ausgibt oder druckt.
Das Interface der Klassen darf nicht verändert werden. Überlege kurz, welches Verhalten die Klasse
`MoviePrinter` aufweisen soll. Implementiere danach die Klasse `MovieRepository` test driven.


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
