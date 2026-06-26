Software Design Patterns
========================


Was sind Design-Patterns?
-------

* Lösungsmuster für Probleme, die immer wieder in der Softwareentwicklung auftreten.
* In Design-Pattern-Katalogen kann man für sein Problem eine mögliche Lösung nachschlagen.
* Design Patterns haben auch Wiedererkennungswert.
  Ein Design Pattern im Code weist auf das gelöste Problem hin.
* Es müssen nicht alle Probleme mit Design Patterns gelöst werden.
  Design Patterns können auch eine zu komplexe Lösung für ein einfaches Problem sein. (KISS)

Factory
-------

* Alias: Factory Method, Simple Factory
* Use Case:
    * Logik für die Erstellung eines Objekts kapseln und vereinheitlichen.
    * Wissen um konkrete Implementationen eines Interfaces zentralisieren.

von [Java Design Patterns/Factory](https://github.com/iluwatar/java-design-patterns/tree/07663ce2bdd46ca4697307068b9eb0d4c8888ead/factory)

Factory Beispiel: vorher
------

![Factory Beispiel: vorher](images/factory/factory-bad-case.png){width=78%}

[factory-bad-case.py](code/slides/factory/factory-bad-case.py)

Factory Beispiel: nachher
------

![Factory Beispiel: nachher](images/factory/factory-good-case.png)

[factory-good-case.py](code/slides/factory/factory-good-case.py)

Factory Übung
------

Erweitert die Übung [exercise/02-03-interfaces.py](code/exercise/02-03-interfaces.py) um eine Factory,
die `Washer`-Klasse erstellt basierend darauf, ob die Items empfindlich (fragile) sind oder nicht.
Die Factory könnte so aussehen (muss nicht zwangsläufig eine klasse sein):

```python
def washer_factory(can_wash_fragile_things: bool) -> Washer:
  pass
```

AbstractFactory
-------

* Use Case:
    * Es gibt 2 oder mehr Gruppen von Komponenten. Es sollen immer nur Komponenten aus einer der Gruppen erstellt werden.
        * Beispiel Light/Dark Theme: es soll entweder Light oder Dark Theme sein, aber nie gemischt.
    * Die gleichen If-Statements tauchen wiederholt an unterschiedlichen Stellen im Code auf. Man möchte diese Code-Duplizierung
      verhindern, indem man die If-Statements an einem zentralen Ort platziert.

von [Java Design Patterns/AbstractFactory](https://github.com/iluwatar/java-design-patterns/tree/07663ce2bdd46ca4697307068b9eb0d4c8888ead/abstract-factory/README.md)

AbstractFactory Beispiel: vorher
------
\colBegin{0.8}
![AbstractFactory Beispiel: vorher](images/abstract-factory/abstract-factory-bad-case.png){width=100%}
\colNext{0.2}
\colEnd

[abstract-factory-bad-case.py](code/slides/abstract-factory/abstract-factory-bad-case.py)


AbstractFactory Beispiel: nachher
------

\colBegin{0.8}
![AbstractFactory Beispiel: nachher](images/abstract-factory/abstract-factory-good-case.png){width=100%}
\colNext{0.2}
\small

* [abstract-factory-good-case.py](code/slides/abstract-factory/abstract-factory-good-case.py)
* Die Anzahl "if darkmode" statements ist reduziert.
* Es ist jetzt viel einfacher, ein "HighContrast"-Theme einzubauen.

\colEnd

AbstractFactory Beispiel 2
------

![footer_m](images/abstract-factory/footer_m.png)
![footer_c](images/abstract-factory/footer_c.png)

AbstractFactory Übung
------

Wir haben die vorherige Übung etwas erweitert (Interface Segregation).
Nutzt [04-abstract-factory.py](code/exercise/04-abstract-factory.py) als Ausgangspunkt um eine Abstract Factory zu implementieren.

```python
class WasherFactory(ABC):
  @abstractmethod
  def create_clothes_washer(self) -> ClothesWasher:
    pass

  @abstractmethod
  def create_dish_washer(self) -> DishWasher:
    pass

```

Strategy
-------

* Use Case:
    * Man möchte von einer Funktionalität das Was vom Wie trennen.
    * Wenn man das Was vom Wie trennt, kann man eventuell Funktionalität teilen
      (DRY) und separat testen.
    * Beispiele:
        * Unterschiedliche Algorithmen für ein Ziel (z.b. Suchalgorithmen, Sortieralgorithmen)
        * Daten können von verschiedenen Quellen kommen (RemoteConfigSourceStrategy, FileConfigSourceStrategy)


Strategy Beispiel: vorher
-------

![Strategy bad case](images/strategy/strategy-bad-case.png)

[strategy-bad-case.py](code/slides/strategy/strategy-bad-case.py)

Strategy Beispiel: nachher
-------

![Strategy good case](images/strategy/strategy-good-case.png)

[strategy-good-case.py](code/slides/strategy/strategy-good-case.py)

Observer
-------

* Use Case:
    * Wenn eine Änderung Auswirkungen auf viele andere Objekte haben soll.
    * Man möchte den Auslöser von Änderungen entkoppeln von den Objekten,
      die sich für die Änderung interessieren.

Observer Beispiel: vorher
-------

![Observer bad case](images/observer/observer-bad-case.png)

[05-observer-bad-case.py](code/exercises/05-observer-bad-case.py)

Observer Beispiel: nachher
-------

![Observer good case](images/observer/observer-good-case.png){height=90%}

Observer Übung
-------

Implementiert das Observer Pattern für die folgende Situation.
Nehmt dafür
[05-observer-bad-case.py](code/exercises/05-observer-bad-case.py) als Ausgangspunkt.

![Observer good case](images/observer/observer-good-case.png){height=75%}

Observer Lösung
-------

Lösung: [05-observer-good-case.py](code/solutions/05-observer-good-case.py)

Command
-------

* Use Case:
    * Man möchte von einer Funktionalität das Wie vom Wann trennen.
    * Man möchte die Klasse, die ein Kommando auslöst, von der Klasse, an der das Kommando ausgeführt wird, trennen.

Command Beispiel: vorher
-------

![Command bad case](images/command/command-bad-case.png)

[command-bad-case.py](code/slides/command/command-bad-case.py)

Command Beispiel: nachher
-------

![Command good case](images/command/command-good-case.png){height=85%}

[command-good-case.py](code/slides/command/command-good-case.py)

Zusammenfassung Abstraktion
------

* Mit Abstraktionen
    * \+
        * Strukturieren wir den Code
        * Geben Konzepten (Abläufen, Konstanten) einen Namen
        * Verstecken Details vor höheren Abstraktionsebenen
        * Machen den Code DRY
    * \-
        * Erhöhen wir die Komplexität
        * Verstecken Funktionalität
        * Koppeln die User der Abstraktion
