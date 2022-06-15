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
   <#ifdef solution>`mkdir exercise`<#endif>
1. Erstelle im Directory `exercise` die Directories `basic`, `tools` und `test`  
   <#ifdef solution>

   ```
   cd exercise
   mkdir basic tools test
   ```

   <#endif>
1. Erstelle im Directory `exercise/basic` das Directory `hello`  
   <#ifdef solution>

   ```
   cd basic
   mkdir hello
   ```

   <#endif>
1. Schreibe in das File `exercise/basic/user` deinen Username (Verwende Redirection)  
   <#ifdef solution>

   ```
   whoami > user
   ```

   <#endif>
1. Schreibe in das File `exercise/basic/system` die Informationen über das Betriebsystem  
   <#ifdef solution>

   ```
   cat /etc/*-release > system
   ```

   <#endif>
1. Schreibe die Liste aller Files/Directories in einem Subfolder von exercise in `exercise/basic/dirstructure`  
   <#ifdef solution>

   ```
   cd ..
   ls -R > basic/dirstructure
   ```

   <#endif>
1. Kopiere das Directory `exercise/basic` zu `exercise/anotherbasic`  
   <#ifdef solution>

   ```
   cp -a basic anotherbasic
   ```

   <#endif>
1. Kopiere die Datei `exercise/anotherbasic/user` in `exercise/tools/user`  
   <#ifdef solution>

   ```
   cp anotherbasic/user tools/user
   ```

   <#endif>
1. Verschiebe das Directory `exercise/tools/` in `exercise/anotherbasic`  
   <#ifdef solution>

   ```
   mv tools anotherbasic
   ```

   <#endif>
1. Optional: kannst du die ganze Directory Struktur (ohne Files) mit einem Befehl anlegen?  
   <#ifdef solution>

   ```
   mkdir -p basic/hello tools test
   ```

   <#endif>

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
1. Stelle sicher, dass /usr/local/bin/hello-world execute Berechtigung hat.  

<#ifdef solution>

[code/solution/update-hello-world-script.sh](update-hello-world-script.sh)
\lstinputlisting{code/solution/update-hello-world-script.sh}

<#endif>

\newpage

### Systemd Service  
Jetzt möchten wir, dass das hello-world Skript beim Aufstarten automatisch gestartet wird,
und die Meldungen ins journald schreibt.  
Nutze für diese Übung vim.

1. Erstelle deine Service Konfiguration in /etc/systemd/system/hello-world.service
1. Starte den Service nach systemd-user-sessions.service
1. Vergiss nicht den Service zu enablen
1. Nach jeder Änderung an der config musst du  
   `sudo systemctl daemon-reload`  
   aufrufen, damit die Änderung einen Effekt hat.
1. Passe das update Script so an, dass es den Service vor dem Update des Skripts stoppt, und danach wieder startet.
1. Teste mit journald, was der hello-world service ausgibt.
1. Stoppe den service. Werden keine neuen Meldungen mehr ins journald geschrieben?
1. Starte die VM neu, startet der Service auch neu?
1. Disable den Service nach dem Ende der Übung, du willst dein Log nicht füllen.

<#ifdef solution>

[code/solution/update-hello-world-service.sh](update-hello-world-service.sh)
\lstinputlisting{code/solution/update-hello-world-service.sh}

<#endif>
