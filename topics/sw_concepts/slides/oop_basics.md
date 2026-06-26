OOP Basics
==========


Grundlagen für Code Beispiele
-----------------------------

* Was ist ein Interface in **O**bjekt**o**rientierter **P**rogrammierung?
* Was ist **U**niform **M**odelling **L**anguage?

Klasse vs. Instanz/Objekt

Funktion vs. Methode vs. Klassenfunktion

public private

Vererbung (Inheritance): Nicht zu stark nutzten, nicht zu viele Hierarchie-Level

Abstrakte Methode
* keine Implementation
* kann default haben
* use Case:INterface. Dann aber nie ein default mitgeben

Abstrakte Klasse
kann nicht instanziiert werden.

Polymorphismus

Überall Best Practices angeben.
Alles mit Java, Beispiel wie man die Konzepte in Python lösen kann.

Danach konkrete Beispiele wie:
Interfaces
Abstrakte Base Class
Anti-Patterns: Generelle abstrakte Klassen, Interface mit defaults

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
class MachineWasher implements Washer {
    void washClothes(){}
}
```

\colNext{0.5}

```java
class HandWasher implements Washer {
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
class MachineWasher implements Washer {
    void washClothes() {}
    void washDishes() {}
}
```

\colNext{0.5}

```java
class HandWasher implements Washer {
    void washClothes() {}
    void washDishes() {}
}
```

\colEnd


Übung: Interface
----------------

Implementiere das Interface `Washer` für die Klasse `MachineWasher` und `HandWasher` in Python:

* Führe Vererbung zwischen den Klassen ein.
* Implementiere die abstrakten Methoden.
* Verwende einfach `print()`-Logs für die Implementierung der Methoden.
* Nutzte den `@override`-Decorator um dem Typsystem die Implementierung zu markieren.

Das Interface in Python könnte folgendermassen aussehen (siehe
auch [exercise/02-03-interfaces.py](code/exercise/02-03-interfaces.py)):

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
---

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
