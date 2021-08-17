Orchestrieren
=============


*docker-compose*
----------------

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
