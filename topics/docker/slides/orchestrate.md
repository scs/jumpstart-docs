Orchestrieren
=============


docker compose
--------------

* Container verbinden und koordinieren mit *docker compose*
* *Beschreibung* mehrerer Container, Netzwerke, Volumes, etc. in: `compose.yaml`
* Starten und stoppen von *lokalen* Container-Infrastrukturen:

~~~ {.bash}
docker compose up
docker compose up --build
docker compose up --detach
docker compose down
docker compose logs
~~~

### Compose V2

* Ist neu ein *Docker Plugin*: [docker_compose_v2]
* File Format V2 & V3 wurden *gemergt*.
* Neu ist das `version` Feld *deprecated*.


compose.yaml
------------

\colBegin{0.5}

~~~ {.yaml}
services:
  homeassistant:
    container_name: home-assistant
    image: homeassistant/home-assistant
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


Kubernetes
----------

\colBegin{0.5}

*High-level Management*:

* Clustering über mehrere physische Hosts
* High Availability
* Load Balancing
* Self Healing
* Rollout Strategien

*Zusätzliche Organisationsstrukturen*:

* Node
* Cluster
* Pod
* Replica Set
* ...

\colNext{0.5}

![module_03_nodes](images/module_03_nodes.png)

\tiny
[module_03_nodes.svg](https://d33wubrfki0l68.cloudfront.net/5cb72d407cbe2755e581b6de757e0d81760d5b86/a9df9/docs/tutorials/kubernetes-basics/public/images/module_03_nodes.svg)
by [The Kubernetes Authors](https://kubernetes.io/de/docs/tutorials/kubernetes-basics/explore/explore-intro/)
licensed under [CC BY 4.0](https://github.com/kubernetes/website/blob/main/LICENSE)

\colEnd


Weitere Begriffe
----------------

* Kubernetes (*K8s*) wurde von Google entwickelt
* Es gibt viele grosse Kubernetes Provider:
  * Amazon’s Elastic Kubernetes Service (EKS)
  * Microsoft’s Azure Kubernetes Service (AKS)
  * Google’s Kubernetes Engine (GKE)
* OpenShift (RedHat) ist eine Erweiterung des Kubernetes OpenSource Projektes
* Für die Entwicklung gibt es Tools für lokale Clusters
  * Minikube (basiert auf VMs)
  * Kind (nutzt Docker-in-Docker)
  * K3s (Leichtgewichtiger Ersatz für K8s für echten Betrieb)
* Docker Swarm (deprecated) war Dockers eigene Variante
* CLI um Kubernetes o.Ä. zu verwalten: *`kubectl`*
