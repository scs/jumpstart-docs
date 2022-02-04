FROM gitpod/workspace-full:latest

ENV DEBIAN_FRONTEND=noninteractive DEBCONF_NONINTERACTIVE_SEEN=true

RUN sudo apt-get update \
    && sudo apt-get install -y --no-install-recommends \
        #basic tools
        ca-certificates \
        cmake \
        g++ \
        git \
        make \
        unzip \
        wget \
        # needed for ansible
        python3-pip \
        # needed for drawio-desktop
        desktop-file-utils \
        xvfb \
    && sudo rm -rf /var/lib/apt/lists/* \
    && sudo pip --no-cache-dir install ansible

COPY --from=ghcr.io/langchr86/docmake:latest /tmp/ansible-role-docmake /tmp/ansible-role-docmake
RUN sudo /tmp/ansible-role-docmake/run_local.sh

# copy user local latex parts into global folder
RUN sudo cp -r /root/texmf/tex/latex/ /usr/share/texlive/texmf-dist/tex/ \
    && sudo texhash

ENV DISPLAY=":99"
