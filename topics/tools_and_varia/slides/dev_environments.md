Entwicklungs-Umgebungen
=======================


Physisch oder virtuell?
-----------------------

*Varianten*:

* physischer PC
* WSL2
* lokale VM
* VM im Rechenzentrum
* Container (auf physischem PC, in VM oder in Cloud)
* In der Cloud: [gitpod] oder [github_codespaces]

*Verbindung*:

* Spezifisches Frontend, z.B: virtualbox GUI
* SSH mit X-Forwarding
* WSL2 mit Wayland ([WLSg](https://github.com/microsoft/wslg))


Betriebssystem
--------------

Welches für welchen Einsatz?

*Windows:*

* Büro-Arbeiten
* Entwicklung von Windows Apps (UWP)
* Entwicklung mit C#
* Bei spezieller Hardware (guter HW Support)

*Linux:*

* Server (-Infrastruktur)
* Web-Entwicklung
* Embedded Devices
* Gratis / OpenSource

*MacOS:*

* Entwicklung von iOS Apps


Infrastruktur
-------------

*Handhabung* genau wie bei *Code*:

* reproduzierbar
* automatisiert
* dokumentiert
* versioniert


*Tools*:

\colBegin{0.5}

Configuration Management

* Ansible
* Chef
* Puppet

\colNext{0.5}

Infrastructure Management

* OpenTofu (Cloud: AWS, Azure, etc.)
* Vagrant (nur für lokale Entwickler-VMs)

Container

* Kubernetes
* Docker Compose

\colEnd


Vagrant
-------

* Automatisierung von *VMs* (beste Unterstützung für virtualbox)
* Sehr gut geeignet für die *Entwicklung*, z.b. für Ansible
* *Provisioning* (z.b. Ansible) wird automatisch gestartet
* Konfiguration in *`Vagrantfile`* (ähnlich `Dockerfile` + `compose.yaml`)
* kann auch *mehrere* VMs inkl. Netzwerk verwalten

~~~
vagrant up
vagrant provision
vagrant destroy -f
~~~


Vagrant Beispiel
----------------

~~~ {.ruby}
Vagrant.configure("2") do |config|
  config.vm.box = "geerlingguy/ubuntu2004"
  config.vm.box_version = "1.0.3"
  config.vm.provider "virtualbox" do |vb|
    vb.memory = "4096"
    vb.cpus = 4
  end
  config.vm.define "main" do |main|
    main.vm.hostname = "main"
    main.vm.network "private_network", ip: "192.168.10.8"
    main.vm.disk :disk, size: "8GB", name: "share_1"
    main.vm.provision "ansible_local" do |ansible|
      ansible.playbook = "/vagrant/ansible/playbook.yml"
    end
  end
  config.vm.define "backup" do |backup|
    # ...
  end
end
~~~


Chocolatey
----------

* *Packet Manager* für Windows
* Verwaltet durch die *Community*
* Wenn ein Programm nicht existiert, reiche ein *eigenes Packet* ein: [choco_create_packages]

~~~
choco list --local-only
choco install -y <package>
choco uninstall <package>
choco upgrade <package>
choco upgrade -y all
choco pin add -n git
choco pin add -n git --version 1.2.3
choco info <package>
choco search --id-starts-with <pattern>
~~~


Ansible
-------

\centering
![ansible_overview](images/ansible_overview.pdf){width=80%}

[ansible_quick_guide]


Ansible
-------

* Nutzt *idempotente* Funktionen
  $\break\rightarrow$ Auch mehrmalige Ausführung führt immer zum *selben Endzustand*
  $\break\rightarrow$ Vorteil gegenüber Skripts
* Nur Ansible-Host braucht *Ansible-Installation*
  * zu verwaltende Clients brauchen nur *SSH* und *Python*
* Konfiguration in *`YAML`*


Ansible
-------

*Strukturierung* in:

Ansible                                               vergleichbar mit
---------                                             -----------------
Playbook (Aufruf von mehreren Rolen und Tasks)        Main-Funktion
Roles (Sammlung von mehreren Tasks und Files, etc.)   Klassen
Tasks (Verwendung von Modules)                        Funktionen
Modules (Grundfunktionalität von Ansible)             API

* Grosser *Katalog* an Modules
* *eigene* Modules möglich (in Python geschrieben)
* *Vorgefertigte* Roles via: [ansible_galaxy]
