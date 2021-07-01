<#include meta/exercise.md>

---
author: Lucius Bachmann
title: "Arbeitsblatt: Linux"
---

Linux
===================================

Bash Befehle in Linux
--------

Nutze die Kommandozeile, um folgende Aufgaben auszuführen:

1. Erstelle das Directory `exercise`
1. Erstelle im Directory `exercise` die Directories `basic`, `tools` und `test`
1. Erstelle im Directory `exercise/basic` das Directory `hello`
1. Schreibe in das File `exercise/basic/user` deinen Username (Verwende Redirection)
1. Schreibe in das File `exercise/basic/system` die Informationen über das Betriebsystem
1. Schreibe die Liste aller Files/Directories in einem Subfolder von exercise in `exercise/basic/dirstructure`
1. Kopiere das Directory `exercise/basic` zu `exercise/anotherbasic`
1. Kopiere die Datei `exercise/anotherbasic/user` in `exercise/tools/user`
1. Verschiebe das Directory `tools/` in `exercise/anotherbasic`
1. Optional: kannst du die ganze Directory Struktur (ohne Files) mit einem Befehl anlegen?

Hello World Service
--------

### Update Script  
Schreibe ein Script, das das hello-world Script updated.  
Nutze nano, um das Skript zu schreiben.  
Testen kannst du das Skript, indem du die updated timestamps mit touch änderst.  
Dazu soll das Script folgende Schritte ausführen:

1. Finde das File das zuletzt geändert wurde in code/hello_world_service/  
   (ls -t, head und pipe (`|`) werden helfen)
1. Kopiere dieses File nach /usr/local/bin/hello-world
1. Schau, dass /usr/local/bin/hello-world execute Berechtigung hat.  

\newpage

### Systemd Service  
Jetzt möchten wir, dass das hello-world Skript beim Aufstarten automatisch gestartet wird,
und die Meldungen ins journald schreibt.  
Nutze für diese Übung vim.

1. Erstelle deine Service Konfiguration in /etc/systemd/system/hello-world.service
1. Starte den Service nach systemd-user-sessions.service
1. Vergiss nicht den Service zu enablen
1. Nach jeder Änderung an der config musst du  
   `systemctl reload hello-world.service`  
   aufrufen, damit die Änderung einen Effekt hat.
1. Passe das update Script so an, dass es den Service vor dem Update des Skripts stoppt, und danach wieder startet.
1. Teste mit journald, was der hello-world service ausgibt.
1. Stoppe den service. Werden keine neuen Meldungen mehr ins journald geschrieben?
1. Starte die VM neu, startet der Service auch neu?
1. Disable den Service nach dem Ende der Übung, du willst dein Log nicht füllen.
