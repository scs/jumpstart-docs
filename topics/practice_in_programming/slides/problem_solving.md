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
