Basics
======


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

# create group
ARG group=sdkuser
ARG gid=1000
RUN groupadd -o -g ${gid} ${group}
~~~

\colNext{0.55}

~~~ {.dockerfile}
# create user
ARG user=sdkuser
ARG uid=1000
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


Docker Architektur
------------------

![docker_architecture](images/docker_architecture.pdf)

[docker_get_started]


Build Context
-------------

* wird an Docker Deamon *gesendet* (kopiert)
* Dockerfile kann *nur auf Dateien im Kontext* zugreifen
* was kopiert wird, kann mittels *`.dockerignore`* eingeschränkt werden

~~~ {.bash}
# hier wird "Dockerfile" im Kontext "." gesucht
docker build .

# hier wird das Dockerfile explizit angegeben (kann ausserhalb des Kontext liegen)
docker build -f /path/to/a/docker_file .
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
