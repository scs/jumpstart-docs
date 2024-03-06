FROM mcr.microsoft.com/devcontainers/base:ubuntu-22.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
            nano \
            tmux \
            vim \
    && yes | unminimize \
    && rm -rf /var/lib/apt/lists/*

CMD ["tail", "-f", "/dev/null"]
