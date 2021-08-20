Tipps und Tricks
================


Container als "VM" verwenden
----------------------------

\colBegin{0.6}

~~~ {.dockerfile}
FROM ...

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


SSH Agent im Container
----------------------

SSH *authentication agent* im Docker mit `docker run`:

~~~ {.bash}
docker run -it --rm \
  --volume $SSH_AUTH_SOCK:/tmp/ssh_auth_sock \
  --env SSH_AUTH_SOCK=/tmp/ssh_auth_sock \
  <container_name/hash>
~~~


Layer klein halten
------------------

Dateien im gleichen `RUN` Kommando *aufräumen*. So bleibt der Layer *klein*.

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


