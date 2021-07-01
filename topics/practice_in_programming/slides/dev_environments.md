Entwicklungs-Umgebungen
=======================


Physisch oder virtuel?
----------------------

*Varianten*:

* physischer PC
* lokale VM
* VM im Rechenzentrum
* Container (auf physischem PC, in VM oder in Cloud)

*Verbindung*:

* Spezifisches Frontend, z.B: virtualbox GUI
* SSH mit X-Forwarding

*Einsatzzwecke*:

* isolierte Entwicklungsumgebung
* Test-Systeme
* Produktive Server-Infrastruktur
* Kapselung von Microservices/Tools


Infrastruktur
-------------

*Handhabung* genau wie bei *Code*:

* reproduzierbar
* automatisiert
* dokumentiert
* versioniert


*Tools*:

* Ansible
* Chef
* Puppet
* Terraform
* Vagrant
* Kubernetes
* Docker Compose


Ansible
-------

\centering
![ansible_overview](images/ansible_overview.jpg){width=80%}

[ansible_quick_guide]


Ansible
-------

* Nur Ansible-Host braucht *Ansible-Installation*
  * zu verwaltende Clients brauchen nur *SSH* und *Python*
* Konfiguration in *`YAML`*
* *Strukturierung* in:
  * Modules
  * Tasks
  * Roles
* Grosser *Katalog* an Modules
* *eigene* Modules möglich
* *Vorgefertigte* Roles via: [ansible_galaxy]
