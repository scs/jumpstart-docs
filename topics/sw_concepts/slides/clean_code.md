Clean Code
==========


Clean Code
-------

* **S**ingle Responsibility
* **O**pen Closed Principle
* **L**iskov's Substitution Principle
* **I**nterface Segregation
* **D**ependency Inversion

Grundlagen für Clean Code Beispiele
-------

* Was ist ein Interface in **O**bjekt**o**rientierter **P**rogrammierung?
* Was ist **U**niform **M**odelling **L**anguage?

Was ist ein Interface?
-------

* Definiert minimales Set an Fähigkeiten(Methoden), die ein Objekt haben muss.
* Ziel ist es, das **Was** der Fähigkeit vom **Wie** zu trennen.
* Beispiel: Wenn ich eine Person einstelle, um meine Wäsche zu waschen, dann brauche ich
  folgendes Interface:

```java
interface Washer {
    void washClothes();
}
```

Dabei ist es mir egal, wie die Person die Wäsche wäscht:

\colBegin{0.5}

```java
class MashineWasher
    implements ClothesWasher {

    void washClothes(){}
}
```

\colNext{0.5}

```java
class HandWasher
    implements ClothesWasher {

    void washClothes(){}
}
```

\colEnd

Interface mit mehreren Methoden
-------

Wenn ich jetzt zustäzlich mein Geschirr waschen lassen möchte,
dann hat das Interface eine weitere Methode:

```java
interface Washer {
    void washClothes();

    void washDishes();
}
```

Und die Implementationen sehen dann so aus:

\colBegin{0.5}

```java
class MashineWasher
    implements ClothesWasher {

    void washClothes(){}

    void washDishes(){}
}
```

\colNext{0.5}

```java
class HandWasher
    implements ClothesWasher {

    void washClothes(){}

    void washDishes(){}
}
```

\colEnd

Kurze Übung Interface
-------

Implementiert das Interface `Washer` für die Klasse `MashineWasher` und `HandWasher` in Python.
(Verwendet simple print statements für die Implementierung der Methoden.)

Das Interface in Python könnte so aussehen (siehe auch [exercise/02-03-interfaces.py](code/exercise/02-03-interfaces.py)):

```python
from abc import ABC, abstractmethod

class Washer(ABC):
    @abstractmethod
    def wash_clothes(self, item: str):
        pass

    @abstractmethod
    def wash_dishes(self, item: str):
        pass
```


UML
-------

**U**nified **M**odeling **L**anguage

* Ein standardisierter Weg um beispielsweise Teile einer Software zu visualisieren.
* Einlesen lohnt sich.
* Auf den nächsten Folien finden Sie eine kurze Übersicht über einen Teil des Klassendiagramms.

von [UML: ISO/IEC 19501](https://www.omg.org/spec/UML/ISO/19501/PDF)

UML Klassendiagramm
-------

\colBegin{0.8}
![UML Klassendiagramm: verwendete Komponenten](images/uml/class-diagram-parts.png){width=90%}
\colNext{0.2}
\small

[class-diagram-parts.py](code/slides/uml/class-diagram-parts.py)

\colEnd

Single Responsibility
-------

* Eine Funktion, Klasse, Modul sollte nur eine "Responsibility" haben.
* Grenze ist nicht ganz klar.
* Indikator: Wenn bei der Bezeichnung ein "and" vorkommt, dann sollte man aufteilen.
* Andere Sichtweise: Eine Funktion, Klasse, Modul sollte nur einen Grund haben, sich zu ändern.

Beispiel Single Responsibility verletzt
------

```java
public enum DirectionAndRazziaValue {
    DIRECTION_1,
    DIRECTION_2,
    RAZZIA
}

public SessionFactoryBuilder withUserAndPassword(String username, String password) {
    this.username = username;
    this.password = password;
    return this;
}

```

Beispiel Single Responsibility verletzt mit Kommentar
------

```java
// Werte in unabhängige Felder packen
// Hier sind zwei eher unabhängige Zustände (Richtung 1/2, Razzia)
// in einem Feld abgebildet.
public enum DirectionAndRazziaValue {
    DIRECTION_1,
    DIRECTION_2,
    RAZZIA
}

// in zwei Methoden aufteilen: es ist ja schon ein Builder
public SessionFactoryBuilder withUserAndPassword(String username, String password) {
    this.username = username;
    this.password = password;
    return this;
}
```

Open Closed
-------

* "Open for extension, closed for modification"
* Für neue Funktionalität
    * Bestehenden Code nicht anpassen
    * Möglichst nur neuen Code hinzufügen

Open Closed Beispiel: vorher
-------
\colBegin{0.8}
![Open Closed Beispiel: vorher](images/abstract-factory/abstract-factory-bad-case.png){width=100%}
\colNext{0.2}
\small

* [abstract-factory-bad-case.py](code/slides/abstract-factory/abstract-factory-bad-case.py)
* Das Modul `views` ist `closed for extension`.
* Für GreyLabel und GreyButton müssen alle if statements geändert werden.

\colEnd

Open Closed Beispiel: nachher
------

\colBegin{0.8}
![Open Closed Beispiel: nachher](images/abstract-factory/abstract-factory-good-case.png){width=100%}
\colNext{0.2}
\small

* [abstract-factory-good-case.py](code/slides/abstract-factory/abstract-factory-good-case.py)
* Das Modul `views` ist jetzt `open` für weitere Implementationen von ComponentFactory.

\colEnd

Liskov substitution principle
-------

* Implementationen eines Interfaces müssen ausgetauscht werden können.
* Sie nehmen mindestens die gleichen Inputwerte an (dürfen auch mehr).
* Sie geben maximal die gleichen Rückgabewerte zurück.
* Werfen maximal die gleichen Exceptions (oder Subtypen der Exceptions).

Beispiel einer Verletzung des Liskov substitution principle
-------

![Liskov substitution principle bad case](images/interface-segregation/interface-segregation-bad-case.png)


Das Liskov Substitution Principle ist hier verletzt, da die `DishWashingMachine::washClothes`
Methode sich nicht so verhält, wie das Interface verlangt.\
`DishWashingMachine` kann nicht überall dort eingesetzt werden, so das Interface `Washer` verlangt wird.

Interface Segregation
-------

* Um die Kopplung zu reduzieren, kann man auch Interfaces aufteilen.
* Eine Klasse kann mehrere Interfaces implementieren
* Man will in einer Klasse nicht benötigte Methoden nicht implementieren
* Man möchte vermeiden, dass jemand unerwartet eine Methode verwendet,
  die eigentlich nicht zur Verfügung gestellt werden sollte.

Interface Segregation Beispiel vorher
------

![Interface Segregation bad case](images/interface-segregation/interface-segregation-bad-case.png)

[interface-segregation-bad-case.py](code/slides/interface-segregation/interface-segregation-bad-case.py)


Interface Segregation Beispiel nachher
------

![Interface Segregation good case](images/interface-segregation/interface-segregation-good-case.png)

[interface-segregation-bad-case.py](code/slides/interface-segregation/interface-segregation-bad-case.py)

Hier lösen wir die Verletzung des Liskov Substitution Principles mit Interface Segregation.

Dependency Inversion
------

* Um zwei Klassen zu entkoppeln, kann ein Interface verwendet werden.
* Das verhindert, dass eine High-Level-Komponente von einer Low-Level-Komponente abhängt.
* Es verhindert auch Abhängigkeiten von der Domäne auf die Infrastruktur Code, wie z.b. die Datenbank.
* Teilweise können so zyklische Abhängigkeiten aufgelöst werden.

Dependency Inversion Beispiel 1
-----

![No dependency inversion](images/dependency-inversion/no-dependency-inversion.png){width=65%}

[no-dependency-inversion.py](code/slides/dependency-inversion/no-dependency-inversion.py)

Dependency Inversion Beispiel 2
-----

![Dependency inversion](images/dependency-inversion/with-dependency-inversion.png){height=80%}

[with-dependency-inversion.py](code/slides/dependency-inversion/with-dependency-inversion.py)
