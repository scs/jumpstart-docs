Aufgaben lösen - Ein Beispiel
=============================


Fragestellung
-------------

Wie implementiere ich das Observer-Pattern?

* Problem ist *bekannt* $\to$ hat sogar einen *Namen*
* Gibt es eine Implementation oder Helper in der *Standard-Library*?
* *Performance*-Anforderungen?
* *Beispiel* anhand C++


Smart-Pointer und Funktionsobjekte
----------------------------------

~~~ {.cpp .numberLines}
// Callback-Funktion
using Callback = std::function<void(int new_value)>;

// Callback-Handler
std::weak_ptr<Callback> subscriber_;
~~~

* Maximale *Freiheit* für `subscriber_`
* sehr *Implizit* beim Implementieren
* `std::weak_ptr` damit *Zerstörung* des Observers nicht verhindert wird


Libraries suchen
----------------

* Konzept abklären: [wiki_signal_slot]
* Observer-Pattern hat unterschiedliche Namen:
  * Signal-Slot (Qt)
  * Callback
* Google/Stack-Overflow
* $\to$ [nano_signal_slot]

~~~ {.cpp .numberLines}
struct CallbackInterface : public Nano::Observer {
  virtual ~CallbackInterface() = default;
  virtual void Notify(int new_value) = 0;
}
~~~

* automatisches *Abmeldung* bei Zerstörung: `Nano::Observer`
* *mehrere* Slots pro Signal


Implementation mit Nano-Signal-Slot
-----------------------------------

~~~ {.cpp .numberLines}
struct ExampleManager {
  void Subscribe(std::weak_ptr<CallbackInterface> subscriber) {
    if (const auto subs = subscriber.lock()) {
      Subscribe(subs.get());
    }
  }
  void Subscribe(CallbackInterface* subscriber) {
    signal_.connect<CallbackInterface, &CallbackInterface::Notify>(subscriber);
  }

  void OnChange(int new_value) {
    signal_.emit(new_value);
  }

 private:
  Nano::Signal<void(int)> signal_;
}
~~~


OpenSource
----------

z.B: *GitHub*

* Wenn nicht im *Standard-Framework/Library* der Sprache
* spezifische *kleinere* Libraries
  * [nano_signal_slot]
  * [protocol_buffers]
  * [flat_buffers]
* diverse *grössere* etablierte Libraries
  * Moderne C++ Features für C++11: [abseil]
  * Stark Template-isiert: [boost]
  * Einfach gehalten. Orientiert sich an Java: [poco]
* Libraries/Treiber der Hardware


Verwendung von Libraries aus dem Internet
-----------------------------------------

*Libraries:*

* Wie gut ist die Dokumentation?
* Gibt es Beispiel-Code oder Unit-Tests?
* Lizenz(en) kompatibel?
* Community noch aktiv?
* Wie viele Maintainer?
* Gibt es Issues und werden diese bearbeitet?
* Gibt es bessere Forks? [active_forks]


Verwendung von Code aus dem Internet
------------------------------------

*Stack-Overflow, ChatGPT, Github Copilot, etc.:*

* Code muss grundsätzlich als *gefährlich* betrachtet werden
* Wenn einsetzten, dann:
  * komplett verstehen
  * An Code-Style anpassen
  * Refactoren
  * Unit-Tests schreiben
  * Lizenz- und Rechte-Themen abklären

\vspace{0.5cm}
\huge
***Nicht einfach Copy-Paste!!!***
\normalsize
\vspace{0.5cm}

### Hintergrund

Studie über die Gefahr von Code auf Code-Plattformen: [vulnerabilities_in_code_examples]
oder Programmierhilfe von AI: [quality_chatgpt_as_code_help]


AI Coding Assistants
--------------------

Die meisten IDEs unterstützen mittlerweile AI Coding Assistants, die den Programmierprozess unterstützen können.
In der SCS verwenden wir z.B. GitHub Copilot (siehe [scs-copilot-infos] für weitere Infos).

* Copilot über die SCS beziehen: [scs-copilot-infos]
* Immer in Absprache mit dem PL und Team, ob und wie AI-Tools eingesetzt werden dürfen.
* Wie immer mit AI-Tools: *Kritisch hinterfragen, was vorgeschlagen wird und nicht blind übernehmen!*
* Das Tool kann durchaus nützlich sein — man sollte etwas Erfahrung sammeln, wie es am besten eingesetzt werden kann.
