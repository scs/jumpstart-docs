Konzepte
========


Was sind Container?
-------------------

* *Kapselung* einer Anwendung und dessen Laufzeitumgebung (Libraries, etc.)
* Container-Runtime hat fest *definierte Schnittstelle*
* Container kann einfach *verteilt* und gestartet werden.
* Prozess(e) und andere Ressourcen im Container werden vom OS und anderen Container *separiert*


Konzepte
--------

*Ressourcen*:

* Hauptspeicher
* Dateisystem
* Netzwerk
* CPU-Zeit

*Separierung* der Daten von der Anwendung:

* Container repräsentiert die Anwendung
* Bind-Mounts/Volumes separieren die Daten
* Lifecycle des Containers ist unabhängig von den Daten
* Stop, Pull, Start des Containers $=$ Update der Anwendung


Container vs. Virtual Machine
-----------------------------

![virtualization_vs_containers](images/virtualization_vs_containers.pdf)

* [containers_vs_vms]
* [introduction_to_windows_container]


Was ist Docker?
---------------

* *Container Engine*
* *Komposition* aller Teile für einfaches Container-Handling
  * Ressource-Abstraktionen (z.B: Dateisystem)
  * Kernel-Features (z.B: CPU-Limitierung)
  * (lokale) Image-Verwaltung
  * Registry (dezentrale Image-Verwaltung)
  * Container-Verwaltung (Image bauen und laufen lassen)
* *Hauptbestandteile*:
  * `dockerd` Daemon (bietet Docker API an)
  * `docker` CLI (Docker API Client)

*Typische Abstraktionsschichten*

`docker` $\to$ `dockerd` $\to$ `containerd` $\to$ `runc`

*Alternativen*:

* [podman]


Standartisierte Schnittstellen
------------------------------

* Schnittstelle zu Container Engine $\to$ *Container Runtime interface (CRI)*
  * Implementiert durch:
    * `containerd`
    * `CRI-O`
  * Verwendet durch:
    * Kubernetes
    * (Docker)
* Schnittstelle zu Container $\to$ *Open Container Initiative (OCI)*
  * Definieren:
    * `image-spec`
    * `runtime-spec`
  * Implementiert durch:
    * `runc`
    * alternative `runc` implementation: [sysbox]
* Diese Definitionen sind Teil der *Cloud Native Computing Foundation (CNCF)*

Siehe: [container_wording]


Runtime: containerd
-------------------

\centering
![containerd_architecture](images/containerd_architecture.png){width=90%}

\tiny
[architecture.png](https://containerd.io/img/architecture.png)
by [The Linux Foundation](https://www.linuxfoundation.org)
licensed under [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/)
