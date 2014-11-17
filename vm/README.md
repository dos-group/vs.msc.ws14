# HOWTO

## [Packer](https://www.packer.io)
Mit diesem Tool kann man aus einer Konfiguration VM Images für verschiedene Platformen bauen (z.B. VirtualBox/VMware für lokale Tests und OpenStack für die Cloud).

### Dateien
* _preseed.cfg_ Ubuntu Konfiguration (für die Installation).
* _ubuntu-node.json_ Packer-Template.
* _ubuntu-node.sh_ Shell/Bash Script der nach der OS-Installation auf der VM ausgeführt wird (hier können wir den Worker oder SDN-Controller installieren, konfigurieren und hochfahren).

### VirtualBox Image für Ubuntu Nodes erstellen.

1. Packer installieren.
2. Ausführen _packer build ubuntu-node.json_.
3. Erstelle Image starten/deployen.

