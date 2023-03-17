Basic Tipps & Tricks
====================


Grundlagen
----------

Git Grundlagen werden *vorausgesetzt*.
Im Internet gibt es genügend *Tutorials* etc. um sich selber einzuarbeiten.
Ein Teil wird im *Praxis Block 1* behandelt.

Als Ergänzung können auch *Cheat Sheets* helfen:

* [cheatsheet_gitlab]
* [cheatsheet_github]
* [cheatsheet_atlassian]
* [cheatsheet_hofmannsven]


Stash / WIP
-----------

Um *unfertige Änderungen* zu speichern:

* temporärer Branch
* Stash

### Stash

Zuerst alle neuen Files stagen (dann werden diese auch gestashed)

~~~ {.bash .numberLines}
git stash
...
git stash pop
~~~


Reflog
------

* `reflog` zeigt *alle* Commits an
  * z.B. alle Commits auf dem `main` vor einer Woche:

    ~~~ {.bash}
    git reflog main@{one.week.ago}
    ~~~

* zeigt auch *nicht mehr referenzierte Commits* an
  * kann verwendet werden, um einen verlorenen Stand *wiederherzustellen*

* wird mit zunehmender Erfahrung unnötig
  $\break\to$ bei Unsicherheit *vorher* einen "Sicherheits"-Branch erstellen


Separierung von commits / hunks
-------------------------------

Nachdem alle nötigen Änderungen gemacht wurden,
können diese trotzdem in einzelne Commits aufgeteilt werden:

* *interactive staging*
* *line/hunk staging*

Am einfachsten mit *`git gui`* o.Ä.

![line_stagging](images/line_stagging.png)

oder mit `git commit -p`


Caret & Tilde
-------------

Caret und Tilde machen nicht dasselbe: [caret_and_tilde]

~~~ {.bash}
git checkout HEAD~1    # vertical (historical) index
git checkout HEAD^1    # horizontal (multi-parent) index

      B
     /|\          D = B^1 = B~1 = B^ = B~
    / | \         E = B^2
   /  |  \        F = B^3
  D   E   F
  |
  |
  G               G = B~2 = B~~
~~~


Bisect
------

* Unterstützung bei der Suche nach dem *Commit des Bugs*
* Nutzt *binäre Suche* in der Commit-History

~~~ {.bash .numberLines}
git bisect start
git bisect bad
git checkout <good_hash>
git bisect good
# git selects next commit to test
git bisect bad
# git selects next commit to test
git bisect skip
# git selects next commit to test
...
# when found mark with branch/tag and stop bisecting
git bisect reset
~~~


[Automated Bisect](https://git-scm.com/docs/git-bisect#_bisect_run)
------------------

Evaluation ob *gut* oder *schlecht* kann mittels Skript automatisiert werden.

~~~ {.bash}
git bisect run <my_script> <arguments>
~~~

das Skript muss folgende Punkte handhaben:

* anwenden von temporärer Patches wärend der Vorbereitung
* kompilieren und testen
* entscheiden ob: `good`/`bad`/`skip`
* temporäre Änderungen verwerfen
* mit entsprechendem *Return Wert* beenden:

\colBegin{0.5}

----------------            ------
`0`                         `good`
\[`1`, `124`\]+`126`+`127`  `bad`
`125`                       `skip`
\[`128`, `255`\]            abort
----------------------------------

\colNext{0.5}
\colEnd
