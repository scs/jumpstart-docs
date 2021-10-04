CI/CD
=====


Warum?
------

* *Automatisiertes* Durchlaufen des gesamten Produktes
  * Style-Checks
  * Statische Code Analyse
  * Kompilation
  * Unit-Tests
  * Deployment
  * System-Tests
* Feature-Branches werden erst *gemergt*, wenn alles "Grün" ist
* *Regression* im Produktiv/Release-Branch zu erkennen

### Kein Ersatz für lokale Tools

* *Alle Schritte* auf der CI/CD-Pipeline müssen einfach auch lokal durchgeführt werden können
* Oder sogar *forciert* werden (z.B: Style-Checks während Kompilation und nicht erst auf CI)


Unterschied: [ci_vs_cd]
-----------------------

* Continuous Integration
  * Automatisiertes kompilieren und Testen
* Continuous Delivery
  * Automatisiertes Testen in Entwicklungs-System
  * *Manuelles* Ausliefern
* Continuous Deployment
  * Automatisiertes Ausliefern in Produktiv-System

\centering
![ci_vs_cd](images/ci_vs_cd.pdf){height=50%}


Was, Wann, Wo?
--------------

Faktoren:

* Wie *gross* ist das Produkt?
* Wie *viele* Mitarbeitende?
* Gewünschter *Release-Zyklus*?

CI: $\to$ Sollte immer aufgesetzt werden

* Hilft den Aufwand für *lokales Ausführen* der Tests zu reduzieren.
* Erleichtert die *Skalierung*, wenn Team grösser wird $\to$ Reviews.
* Bildet *absolute Wahrheit* des Build-/Test-Ablaufs ab.

CD: $\to$ Je nach Bedarf/Projekt

* Verkürzt den *Feedback-Loop* mit der Kundschaft.
* Automatisierte *Release-Builds*


Tools
-----

*Integriert in Tool-Environment*

* Atlassian: Bamboo
* GitLab: GitLab CI/CD
* GitHub: GitHub Actions: [github_awesome_actions]

Siehe: [future_of_ci_cd], [awesome_ciandcd]

*Selbständige Tools*

* Jenkins: [awesome_jenkins]
* BuildBot
* Tekton
* Jenkins X
* Circle CI
* etc.

Siehe: [scs_techstudie_ci_cd]


Pipelines organisieren
----------------------

* Pipeline Definition sollte als File *im Repo* liegen
  * Anpassungen *direkt* über Commit möglich
  * Feature-Branches können neue Pipelines *ausprobieren*
* Fehler soll die *ganze* Pipeline stoppen
* Fehler im master-Branch soll *E-Mail* (oder andere Notification) versenden
* Build-Environment als *Container*-Image definieren
  * CI/CD-Tool muss Container *unterstützen*
  * `Dockerfile` o.Ä. auch im *Code-Repo*
