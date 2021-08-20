Praxis Block 2
==============


Selbständig ausprobieren
------------------------

* Aufsetzten eigenes lokalen Repos (`git init --bare --shared`)
* `clone` lokales Repo
* `amend` und `git fix` einsetzten
* Feature-Branches rebasen anstatt mergen
* Merge Konflikte lösen
  * mittels `merge` oder `rebase`
  * was sind die Unterschiede?
* History aufräumen mit interaktivem Rebase:
  * umsortieren
  * `squash`
  * `edit`
  * `reword`


Selbständig ausprobieren
------------------------

* Aliases
* Bisect
* Patches erstellen und einspielen
* Einsetzten von vorbereiteten `.gitignore`: [gitignore]
* [git_katas]
* eigene Probleme ausdenken und lösen


`rebase` anstatt `merge`
------------------------

`setup.sh` erstellt ein Beispiel Repo mit Feature-Branch

~~~ {.bash}
cd git/code
./rebase_merge_example.sh
~~~

Vergleiche die Merge Konflikte bei:

* `git merge master`
* `git rebase master`

Am Schluss Ordner einfach wieder löschen: `rm -rf rebase_merge`


`rebase` anstatt `merge`
------------------------

\colBegin{0.5}

*Merge*:

\scriptsize

~~~
<<<<<<< HEAD
and the 3. will be sad because of them
=======
and the third will ignore both of the others
>>>>>>> master











// correct:
and the 3. will be sad because of the others
~~~


\colNext{0.5}

*Rebase*:

\scriptsize

~~~
<<<<<<< HEAD
and the third will ignore both of the others
=======
and the 3. will ignore both of them
>>>>>>> * change words to numbers

// correct:
and the 3. will ignore both of the others


<<<<<<< HEAD
and the 3. will ignore both of the others
=======
and the 3. will be sad because of them
>>>>>>> * change behavior of third line

// correct:
and the 3. will be sad because of the others
~~~

\colEnd
