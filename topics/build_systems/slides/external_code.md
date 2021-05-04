Externer Code
=============


Libraries
---------

* nicht *alles selber* implementieren!
  * viele Probleme sind bekannt/gelöst
  * Problem-Namen herausfinden kann helfen
* Was bringt die Sprache selber für ein *Environment*?
  * System-Libraries
* Wo findet man *weitere* Libraries etc.?
  * OpenSource: [github.com](https://github.com), [gitlab.com](https://gitlab.com)
  * Diskussionen: [stackoverflow.com](https://stackoverflow.com)


Libraries einbetten
-------------------

Wie *ziehe* ich die Libraries *an*?

* Java: Maven (`pom.xml`)
* .Net: nuget (`nuget.config`)
* Python: pip (`requirements.txt`)
* Rust: Cargo (`config.toml`)
* C/C++: bitbake, apt/yum, conan, cmake, header-only

### Design

* je nach Level des Projektes
* nicht manuell


Libraries nutzen
----------------

Wie *verwendet* meine Applikation die Library?

* statisch oder dynamisch linken?
* mit der Applikation paketiert?
* in der Runtime (OS, Container, etc.) mitliefern?
