Praxis Block 2
==============


Kapseln einer Toolchain
-----------------------

1. Erstellen eines Containers,
   der die Applikation [hostcontrold](https://github.com/langchr86/hostcontrold) bauen kann.
   Siehe: [installation](https://github.com/langchr86/hostcontrold#installation)
1. Erweitern, dass auch die Unittests durchgeführt werden mittels: `make && make test`.
   Siehe: [unit-testing](https://github.com/langchr86/hostcontrold#unit-testing)
1. Zweites Image, welches mittels Multistage Build nur noch die Abhängigkeiten und die Applikation enthält.

Am einfachsten direkt das Github Repo öffnen und dort ein "Default"
[Codespace starten](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=351816701&skip_quickstart=true)

Oder auf einem *Linux-Host* mit *Docker* klonen.


Wichtige Bemerkungen
--------------------

* Nicht in der existierenden Docker-Infrastruktur im Repo nachschauen
* Ubuntu 20.04 als Basis-Image verwenden
* Damit `apt` kein User-Input abfragt, folgende Environment-Variable setzten: `DEBIAN_FRONTEND=noninteractive`
* `software-properties-common` wird benötigt, falls Zertifikat-Fehler auftreten
* `make install` sagt genau, was wo installiert wird
* Die Applikation soll beim ersten Start sofort wieder enden
  und ein Default-Config-File unter `/etc/hostcontrold.conf` anlegen
