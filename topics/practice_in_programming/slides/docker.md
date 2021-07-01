Docker
======


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

![virtualization_vs_containers](images/virtualization_vs_containers.png)

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

![docker_architecture](images/docker_architecture.png)

[docker_get_started]


Runtime: containerd
-------------------

*OS*-Abstraktion:

* Windows
* Linux

Standardisierte Schnittstellen $\to$ *Open Container Initiative (OCI)*

* `image-spec`
* `runtime-spec`

Featuring:

* CLI client: *`runc`*
* *Lifecycle* management
* Teil der *Cloud Native Computing Foundation (CNCF)*


Runtime: containerd
-------------------

\centering
![containerd_architecture](images/containerd_architecture.png){width=90%}

[containerd.io/img/architecture.png](https://containerd.io/img/architecture.png)


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
FROM ubuntu:15.04
COPY . /app
RUN make /app
CMD python /app/app.py
~~~

Beispiel von hier:
[docker_storage_driver]

\colNext{0.6}

![container-layers](images/container-layers.jpg)

\colEnd


Das Dockerfile
--------------

* besteht aus Docker *Kommandos*: [dockerfile_reference]
* beschreibt das *Bauen* eines Images
* wird *sequentiell* abgearbeitet $\to$ jeder Schritt ein Layer
* `RUN` führt normale *Shell-Befehle* aus

\colBegin{0.45}

~~~ {.dockerfile}
FROM ubuntu:18.04

# install all needed packages
RUN apt-get update \
    && apt-get install -y \
        git-core rsync

# create user
ARG user=sdkuser
ARG group=sdkuser
RUN groupadd -o -g ${gid} ${group}
~~~

\colNext{0.55}

~~~ {.dockerfile}
ARG uid=1000
ARG gid=1000
RUN useradd --no-log-init -d "/home/${user}" \
    -u ${uid} -g ${gid} -m -s /bin/bash ${user}

# change to normal user
USER ${user}
WORKDIR /home/${user}

# prepare custom install steps
COPY my_install_script.sh /home/${user}/
RUN /home/${user}/my_install_script.sh
~~~

\colEnd


Build Context
-------------

* wird an Docker Deamon *gesendet* (kopiert)
* Dockerfile kann *nur auf Dateien im Kontext* zugreifen
* was kopiert wird, kann mittels *`.dockerignore`* eingeschränkt werden

~~~ {.bash}
# hier wird "Dockerfile" im Kontext "." gesucht
$ docker build .

# hier wird das Dockerfile explizit angegeben (kann ausserhalb des Kontext liegen)
$ docker build -f /path/to/a/docker_file .
~~~


Management
----------

\colBegin{0.5}

*Images erzeugen*

~~~ {.bash}
docker build .
docker build --tag <image_tag> .
~~~

*Images verwalten*

~~~ {.bash}
docker images
docker rmi <image_tag/hash>
docker image prune
~~~

*Interaktiv*

~~~ {.bash}
docker run -it <image_tag>
docker run -it --rm <image_tag>

docker exec -it <container_name/hash> \
    /bin/bash
docker logs -f -t <container_name/hash>
~~~

\colNext{0.5}

*Container erzeugen/starten*

~~~ {.bash}
docker create <image_tag>
docker create --name <container_name> \
    <image_tag>

# create and run
docker run --name <container_name> <image_tag>
docker run -d --rm --privileged <image_tag>
docker run -it --rm <image_tag> /bin/bash
~~~

*Container verwalten*

~~~ {.bash}
docker ps -a
docker rm <container_name/hash>

docker start <container_name/hash>
docker stop <container_name/hash>

docker inspect <container_name/hash>
~~~

\colEnd


Persistenz
----------

* Container soll *Wegwerf-Ware* sein
* Persistenz wird *ausserhalb* gelöst (nicht im Container-Layer)

\centering
![docker_mounts](images/docker_mounts.png){width=60%}

### Varianten

* Volumes: [docker_volumes]
* Bind-Mounts: [docker_bind_mounts]
* tmpfs-Mounts: [docker_tmpfs_mounts]


Volumes
-------

* Volumes werden von `docker` *verwaltet*
* nicht existierende Volumes werden automatisch *erstellt*
* vorhandene Daten am Mount-Point werden *kopiert*
* Volumes können zwischen Container *geteilt* werden

*Management*

~~~ {.bash}
docker volume create <vol_name>
docker volume ls
docker volume rm <vol_name>
docker volume prune
~~~

*Verwendung*

~~~ {.bash}
# --mount (more explicit)
docker run --mount source=<vol_name>,target=/app alpine
# -v (shorter)
docker run -v <vol_name>:/app alpine
~~~


Bind-Mounts
-----------

* vorhandene Daten am Mount-Point werden *überdeckt*
* einfacher Weg um Daten *rein oder raus* zu bekommen
* langsam auf Windows & Mac

*Verwendung*

~~~ {.bash}
# --mount (more explicit)
docker run --mount type=bind,source=/my/local/app,target=/app,readonly alpine

# -v (shorter)
docker run -v /my/local/app:/app:ro alpine
~~~


tmpfs-Mounts
------------

* für nicht persistente, *temporäre* Daten
* nur auf Linux
* kann nicht zwischen Container geteilt werden

*Verwendung*

~~~ {.bash}
# --mount (more explicit)
docker run --mount type=tmpfs,target=/app alpine

# --tmpfs (no options)
docker run --tmpfs /app alpine
~~~


Netzwerk
--------

Verschiedene *Driver* verfügbar:

* `bridge`: Default-Netz. Isoliert Container von Host
* `host`: Keinerlei Isolation des Netzwerks
* `none`: Totale Isolation $\to$ kein Netzwerk
* `overlay`: Erlaubt die Verbindung mehrere Docker Daemons
* `macvlan`: Container hat MAC-Adresse und erscheint als "echtes" Gerät im Netzwerk



Bridge-Netzwerk
---------------

* Alle Container im Default-Netz sind *verbunden*
  * können aber nur mit IP adressiert werden
* Container in einem *user-defined* Netzwerk sind mit *Container-Namen* adressierbar

*Verwendung*

~~~ {.bash}
docker network create --driver bridge <network_name>
docker network ls
docker run --network <network_name> alpine
docker network inspect <network_name>
docker network connect <network_name> <container_name/hash>
~~~

[docker_network_bridge]


Netzwerk-Ports
--------------

*Dokumentation* relevanter Ports im `Dockerfile`

~~~ {.dockerfile}
EXPOSE 22
EXPOSE 23/tcp
EXPOSE 24/udp
~~~

Müssen trotzdem explizit beim Starten *geöffnet* werden.

* im gleichen Netzwerk sind alle Ports direkt verfügbar
* aber z.B: bei `bridge` nicht für den Host geöffnet

~~~ {.bash}
docker run -p 2222:22 -p 2323:23/tcp ...
~~~

Es können auch Ports geöffnet werden die *nicht* `EXPOSE` sind.


Tipps
-----

\colBegin{0.6}

Als "VM" verwenden.

~~~ {.dockerfile}
...

# create user
...

# make sudo not use a password
RUN echo "${user} ALL=(ALL) NOPASSWD: ALL" \
    >> /etc/sudoers

# run SSH server
RUN mkdir /var/run/sshd
EXPOSE 22
CMD ["/usr/sbin/sshd", "-D"]
~~~

\colNext{0.4}

Mit *SSH* anstatt `exec` verbinden:

~~~ {.bash}
docker build --tag dev_docker .
docker create \
  --publish 2201:22 \
  --name dev_docker \
  dev_docker

docker start dev_docker

ssh sdkuser@localhost -A -p 2201
~~~

\colEnd


Tipps
-----

* SSH *authentication agent* im Docker mit `docker run`:

  ~~~ {.bash}
  docker run -it --rm \
    --volume $SSH_AUTH_SOCK:/tmp/ssh_auth_sock \
    --env SSH_AUTH_SOCK=/tmp/ssh_auth_sock \
    <container_name/hash>
  ~~~

* Dateien im gleichen `RUN` Kommando *aufräumen*. So bleibt der Layer *klein*.

  ~~~ {.dockerfile}
  # install all needed packages
  RUN apt-get update \
      && apt-get install -y --no-install-recommends \
          git-core \
          rsync \
      && rm -rf /var/lib/apt/lists/*
  ~~~


Multistage Build
----------------

* Verwende ein (oder mehrere) Container für Installation/Builds mit *Nebenprodukten*
* Kopiere *Endresultat* in End-Image

\colBegin{0.5}

~~~ {.dockerfile}
#---------------
FROM debian:buster AS base

# install runtime dependencies
...

#---------------
FROM base AS build

# install build time dependencies
...

COPY app/ /build
RUN cd /build && make
~~~

\colNext{0.5}

~~~ {.dockerfile}
#---------------
# final stage
FROM base

COPY --from=build /build/my_app /bin/my_app
~~~

[dockerfile_best_practice]

\colEnd


Image-Registry
--------------

* Image bei jedem Deployment bauen ist unnötiger *Overhead*
* Images einmal bauen und *zentral* zur Verfügung stellen
* Versionierung mittels *Tags*
* Images für unterschiedliche *Architekturen*: arm, x86, etc.

*Registries*

* Docker Hub
* Artifactory
* GitHub Packages
* private

~~~ {.bash}
# upload image to registry
docker push <your_username>/<image_repo>

# is automatically done by using in Dockerfile or create/run command
docker pull <your_username>/<image_repo>
~~~



Orchestrieren
-------------

Container verbinden und koordinieren mit *docker-compose*: `docker-compose.yml`

\colBegin{0.5}

~~~ {.yaml}
version: '3'
services:
  homeassistant:
    container_name: home-assistant
    image: homeassistant/home-assistant:stable
    depends_on:
      - mqtt
    volumes:
      - /etc/homeassistant:/config
    environment:
      - TZ=Europe/Zurich
    restart: always
    network_mode: host
~~~

\colNext{0.5}

~~~ {.yaml}
  mqtt:
    container_name: mosquitto
    image: eclipse-mosquitto:1.6.14
    ports:
      - "1883:1883"
      - "9001:9001"
    volumes:
      - /etc/mosquitto/config:/mqtt/config:ro
      - /etc/mosquitto/data/:/mqtt/data
      - /var/log/mosquitto/log:/mqtt/log
    restart: always
~~~

\colEnd


Orchestrieren
-------------

~~~ {.bash}
docker-compose up
docker-compose down
docker-compose logs
~~~

*Andere Tools*:

* Kubernetes (K8s) (Google)
* OpenShift (RedHat)
* Minikube
* etc.
* Docker Swarm (deprecated)


Container als Service
---------------------

*Restart always*:

~~~ {.bash}
docker run -d --restart always <image_tag>
~~~

Oder besser mit *`docker-compose`*:

~~~ {.yaml}
version: '3'
services:
  <my_container>:
    image: <image_tag>
    restart: always
~~~

[docker_restart_policies]

oder als Systemd-Unit: [docker_compose_systemd_unit]
