FROM registry.fedoraproject.org/fedora:41

RUN dnf install -y \
        systemd \
        bash \
        vim \
    && dnf clean all

CMD [ "/usr/sbin/init" ]
