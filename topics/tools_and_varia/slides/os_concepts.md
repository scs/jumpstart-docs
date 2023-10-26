Betriebssystem-Konzepte
=======================


Unterschiede: Tasks, Threads und Prozesse
-----------------------------------------

* Prozesse und Threads sind *OS-Features*
* Tasks werden durch das *SW-Framework* definiert
* Threads und Prozesse bedeuten *Overhead*: Stack, Context-Switch, Synchronisierung

\centering
![process_threads](images/process_threads.pdf)


Zugriffsrechte Arbeitsspeicher
------------------------------

* Alle Threads können auf gesamten *Prozess-Speicher* zugreifen
* Prozesse können untereinander *nicht auf Speicher* zugreifen.


Was ist ein Real-Time-OS (RTOS)?
--------------------------------

\colBegin{0.5}

Time-Sharing OS
\vspace{0.2cm}

* *Mehrere Prozesse* mit virtuellem Memory (MMU)
* *Präemptives Scheduling* für Prozesse und Threads
* *Dynamisches* zeitliches Verhalten des Schedulers

\colNext{0.5}

Bare Metal
\vspace{0.2cm}

* Nur *ein Prozess*
* Meist nur *kooperatives Scheduling* für Tasks (Event-Loop)
* *Direkte Verwendung* von Timer-Interrupts für die Einhaltung von Echtzeitverhalten

\colEnd

\vspace{0.6cm}
\colBegin{0.25}
\colNext{0.5}

Real-Time OS
\vspace{0.2cm}

* Nur *ein Prozess* aber mit Threads
* *Präemptives Scheduling* für Threads/Tasks
* *Scheduler* kümmert sich um die Einhaltung von Echtzeitverhalten

\colNext{0.25}
\colEnd

