FROM ubuntu:20.04 AS base

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update \
  && apt-get install -y --no-install-recommends \
    libtool \
    pkg-config \
  && rm -rf /var/lib/apt/lists/*


#------------------
FROM base AS build

RUN apt-get update \
  && apt-get install -y --no-install-recommends \
    cmake \
    g++ \
    git \
    make \
    software-properties-common \
  && rm -rf /var/lib/apt/lists/*

# install googletest
RUN cd /tmp \
  && git clone https://github.com/google/googletest.git \
  && cd googletest \
  && git checkout release-1.10.0 \
  && mkdir build \
  && cd build \
  && cmake -DBUILD_SHARED_LIBS=OFF .. \
  && make -j4 install \
  && ldconfig

# build, run tests and install application
RUN cd /tmp \
  && git clone https://github.com/langchr86/hostcontrold.git \
  && cd hostcontrold \
  && mkdir build \
  && cd build \
  && cmake .. \
  && make -j4 \
  && make test \
  && make install


#------------------
FROM base AS hostcontrold

COPY --from=build /usr/sbin/hostcontrold /usr/sbin/
COPY --from=build /etc/systemd/system/hostcontrold.service /etc/systemd/system/

ENTRYPOINT ["/usr/sbin/hostcontrold"]
