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

[containers_vs_vms]


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
  * `docker` und `docker-compose` CLI (Docker API Clients)

*Typische Abstraktionsschichten*

`docker` $\to$ `dockerd` $\to$ `containerd` $\to$ `runc`

*Alternativen*:

* [podman]


Docker Architektur
------------------

![docker_architecture](images/docker_architecture.pdf)

[docker_get_started]


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


Docker Images
-------------

\colBegin{0.3}

*Image* $\neq$ *Container*
$\break\to$ ähnlich wie in Code:

\colNext{0.3}

----      ----
Code      Dockerfile
Binary    Image
Process   Container
----      ----

\colNext{0.4}
\colEnd

* Jedes Kommando im Dockerfile erzeugt neuen *read-only* Layer
* Jeder Layer ist nur die *Differenz* zum vorherigen
* Später entfernte Dateien sind zwar nicht mehr sichtbar,
  aber benötigen in vorherigem Layer trotzdem *Speicherplatz*
* *CoW*: wiederverwendete Layer werden nur referenziert und nicht kopiert


Docker Images
-------------

\colBegin{0.4}

~~~ {.dockerfile}
# syntax=docker/dockerfile:1

FROM ubuntu:20.04

COPY . /app

RUN make /app

CMD python /app/app.py
~~~

Beispiel von hier:
[docker_storage_driver]

\colNext{0.6}

Container based on ubuntu:20.04 image

\small

~~~
------------------------------
d3a3b2737d            200.0 MB  \
                                |
c86d237daa            190.0 KB  |
                                > Image Layers
d002db893a            1.203 KB  | (Read only)
                                |
91ef45d2ab                0 B  /
------------------------------
Thin Read/Write Layer        <-- Container Layer
------------------------------
~~~

\colEnd
