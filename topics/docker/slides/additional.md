Weiterführendes
===============

Devcontainer
------------

[devcontainer]

* Bieten eine vorkonfigurierte, reproduzierbare Entwicklungsumgebung mit Docker-Containern.
* Entwicklungsumgebung funktioniert konsistent auf Windows, macOS und Linux.
* `devcontainer.json` definiert das Container-Image sowie Erweiterungen und Tools.
* Nahtlose Integration mit VS Code, GitHub Codespaces und Intellij IDEs.
* Ermöglicht die genaue Festlegung von Toolchains, Dependencies und Runtimes, ohne das lokale System zu beeinflussen.


Devcontainer
------------

`devcontainer.json`:

~~~ {.json}
{
  "name": "Python DevContainer",
  "image": "mcr.microsoft.com/devcontainers/python:3.11",
  "features": {
    "ghcr.io/devcontainers/features/docker-in-docker:2": {}
  },
  "customizations": {
    "vscode": {
      "extensions": ["ms-python.python"]
    }
  },
  "postCreateCommand": "pip install --upgrade pip",
  "remoteUser": "vscode"
}
~~~


Devcontainer
------------

`devcontainer.json` mit lokalem Dockerfile:

~~~ {.json}
{
  "build": { "dockerfile": "Dockerfile" },

  "customizations": {
    "vscode": {
      "extensions": ["dbaeumer.vscode-eslint"]
    }
  },

  "forwardPorts": [3000]
}
~~~


Container als Service
---------------------

*Restart always*:

~~~ {.bash}
docker run -d --restart always <image_tag>
~~~

Oder besser mit *`docker compose`*:

~~~ {.yaml}
version: '3'
services:
  <my_container>:
    image: <image_tag>
    restart: always
~~~

[docker_restart_policies]

oder als Systemd-Unit: [docker_compose_systemd_unit]


Docker in Docker
----------------

* *Docker daemon* in Container laufen lassen
* Dieser Docker-in-Docker (dind) Container benötigt *privileged* Rechte
  * oder man verwendet [sysbox]
* Alle anderen Container kennen diesen *nested daemon*
* Die CLI Tools kommunizieren über *TCP* (port: 2376) mit dem nested daemon
* So gibt es einen *zentralen* Punkt wo alle nested Containers leben

![dind](images/dind.pdf){width=80%}

Alternative: dockerd Socket mounten: `/var/run.docker.sock`


Docker in Docker
----------------

\colBegin{0.6}

~~~ {.yaml}
version: '3.3'
volumes:
  certs:
  docker:

services:
  machine:
    container_name: my-machine
    build:
      context: .
      dockerfile: docker/Dockerfile
    networks: [net]
    volumes:
      - certs:/certs/client:ro
    environment:
      - DOCKER_HOST=tcp://docker:2376
      - DOCKER_CERT_PATH=/certs/client
      - DOCKER_TLS_VERIFY=1
~~~

\colNext{0.5}

~~~ {.yaml}
  dind:
    container_name: my-dind
    image: docker:dind
    privileged: true
    networks:
      net:
        aliases: [docker]
    volumes:
      - certs:/certs/client
      - docker:/var/lib/docker
    environment:
      - DOCKER_TLS_CERTDIR=/certs
    ports:
      - "2376:2376"

networks:
  net:
    driver: bridge
~~~

\colEnd


Alternativen zu Docker
----------------------

*[podman]*

* OpenSource Implementation des Docker Interfaces
* Bessere *Separierung* der Ressourcen
* Durchdachteres *Rechte-Management*: Rootless Container
* Benötigt *keinen Daemon*
* *1-zu-1 Ersatz* für Docker: `alias docker=podman`
* Nutzt [quay.io](https://quay.io/) als *Default-Registry* (anstatt [hub.docker.com](https://hub.docker.com/)): `/etc/containers/registries.conf`


Alternativen zu Docker
----------------------

*[sysbox]*

* alternative `runc` implementation
* Rootless Container
* bessere Isolierung
* kann systemd, docker, etc. in Container laufen lassen

*systemd-nspawn*

* eher als Entwickler-Tool für Linux-Entwickler gedacht
* kann komplettes OS (inkl. systemd und vollem rootfs) laufen lassen

*[rkt]*

* Ausgesprochen: Rocket
* Projekt beendet
