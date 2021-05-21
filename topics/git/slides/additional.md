Weiterführendes
===============


Aliases
-------

Erlaubt die Definition von Shortcuts für *git commands* in *git* config: `~/.gitconfig`

  ~~~ {.bash}
  [alias]
  st = status
  ~~~

Sogar beliebige *Shell Befehle* können verwendet werden:

  ~~~ {.bash}
  [alias]
  st = status
  upstream-merge = !"git fetch origin -v && git fetch upstream -v && ..."
  ~~~

*Ausführlichere Skripts* können im `PATH` als *`git-<my_command>`* abgelegt werden.
$\break\to$ Diese können dann folgendermassen ausgeführt werden: *`git <my_command>`*


Aliases
-------

In `~/.gitconfig`

~~~ {.numberLines}
[alias]
  co = checkout
  cob = checkout -b
  st = status --short
  rpo = remote prune origin
  spu = stash push
  spo = stash pop
  ra = rebase --abort
  rc = rebase --continue
  rs = rebase --skip
  pushfwl = push --force-with-lease
  pusho = !git push -u origin $(git rev-parse --abbrev-ref HEAD)
  pushdel = push origin --delete
  l = log --oneline
~~~

Siehe auch: [git_tools]


`git fix` Alias
---------------

*`amend`*, nicht nur für den letzten Commit.

~~~
fix = "!fix() { git commit --fixup $1 && GIT_SEQUENCE_EDITOR=: \
      git rebase --interactive --autosquash --autostash $1~1; }; fix"
~~~

~~~ {.numberLines}
// stage your changes e.g. with git gui
git gui

// check which commit to fix e.g. with git log
git log --oneline

// now append the staged changes to the selected commit
git fix <commit-hash>

// this results in rebasing and therefore changing the history upon the selected commit
~~~

Siehe auch: [git_tools]


Git Commit Beschreibungen
-------------------------

Beispiel-Struktur:

~~~ {.numberLines}
category/component: Commit message summary

Commit message body explaining what was changed and why.
May consist of multiple lines or even multiple paragraphs.

Issue: WINGDECSSW- 9999
~~~

Evtl. Linter verwenden: [gitlint]


Wie schreibt man gute Commit Beschreibungen?
--------------------------------------------

Beim Schreiben der Commit Message sollten folgende Regeln beachtet werden:

\small

* Die erste Zeile enthält eine Zusammenfassung, ist nicht länger als 70 Zeichen und endet nicht mit einem Punkt.
* Die erste Zeile verwendet den Imperativ bzw. Präsens
  (Add support for X" anstatt "Added support for X" oder "Adds support for X").
  Die restliche Commit Message verwendet den Präsens um zu beschreiben, was der Patch ändert.
  Die Vergangenheitsform kann verwendet werden, um den Code vor dem Patch zu beschreiben.
* Wenn sinnvoll, kann in der ersten Zeile ein Prefix mit Doppelpunkt verwendet werden,
  um den Codebereich zu beschreiben, der geändert wird (z.B. "ssh: decrease ServerAliveInterval to 60 seconds").
* Nach der ersten Zeile folgt eine Leerzeile und eine ausführlichere Beschreibung.
  Wenn die Zusammenfassung bereits ausreicht, kann auf die Beschreibung verzichtet werden.
* Die maximale Zeilenlänge in der Commit Message ist 72 Zeichen.
* Siehe auch:
  * [good_git_comments]
  * [practical_guide_git_comments]
  * [uboot_commit_message_conventions]


Beschreibungs-Präfix
--------------------

Kann helfen, um schnell eine bestimmte *Art* von Commits zu finden.

Präfix  Bedeutung
------  ---------
`+`     Erweiterung von Funktionalität (rückwärts kompatibel))
`*`     Veränderung von Funktionalität (nicht mehr rückwärts kompatibel)
`-`     Entfernen von Funktionalität
`!`     Fehlerbehebung
`c`     Kommentare und Kosmetik
`~`     Refactoring

Zum Beispiel:

~~~
* replace IPC API Create() function by CreateFor()
~~~


Integration von anderen Git Repos
---------------------------------

* es existieren zwei Mechanismen um Sub-Projekte in Git zu haben

\colBegin{0.5}

[git_submodule]

* gut um eigenes Repo als Sub-Projekt zu verwalten
* gut um zum Sub-Projekt zu *push*en
* schwierig zu *pull*en $\to$ manuelles `git submodule update` o.Ä. nötig

\colNext{0.5}

[git_subtree]

* gut um andere Repos als Sub-Projekt einzupflegen
* gut um zum Sub-Projekt zu *pull*en
* good to use *another* repo as sub-project $\to$ keine manuellen Aktionen nötig
* schwierig zu *push*en

\colEnd

Es existieren aber auch third-party Lösungen wie z.B.: [git_subrepo]


Tips and Tricks
---------------

* use *`-`* as *placeholder* for the previous commit hash or branch name

  ~~~ {.bash}
  git checkout feature/whatever
  git checkout -    # go back to wherever you was before
  ~~~

* do use *automatic rebasing* if pull is no fast-forward

  ~~~ {.bash}
  git pull --rebase
  ~~~

* do use `reset --hard` to *change references* to other commits

  ~~~ {.bash}
  git rebase feature/whatever main
  git checkout main
  git reset --hard feature/whatever
  ~~~

* clean-up *deleted remote branches*

  ~~~ {.bash}
  git remote prune origin
  ~~~


Cherry-Picking
--------------

* "copy" *one or multiple commits* to other branch

  ~~~ {.bash}
  git cherry-pick <single_commit>
  git cherry-pick <first_commit> <second_commit> <third_commit>
  ~~~

* consider [*consequence of cherry-picking for future merge*](https://stackoverflow.com/questions/881092/how-to-merge-a-specific-commit-in-git/881112#881112)

* cherry-pick [*ranges*](https://stackoverflow.com/questions/9853681/git-create-branch-from-range-of-previous-commits/9853814#9853814)

  ~~~ {.bash}
  git cherry-pick <oldest_not_included_commit>..<newest_included_commit>

  git checkout short_branch
  git cherry-pick E..H    # or: git cherry-pick F~..H

        F'-G'-H'  <-- short_branch
       /
    A-B  <-- main
       \
        C-D-E-F-G-H  <-- long_branch
  ~~~


Rebasing
--------

* do *use* `rebase` -- do not think it is too dangerous

* *update/rebase* your feature branch to new root (e.g. main)

  ~~~ {.bash}
  git checkout feature/whatever
  git rebase main
  ~~~

* use only the *last 3 commits* to rebase to new root (e.g. main)

  ~~~ {.bash}
  git checkout feature/whatever
  git rebase --onto main HEAD~3
  ~~~

* *manipulate history* of your branch until a certain root (e.g. main)

  ~~~ {.bash}
  git checkout feature/whatever
  git rebase -i main
  ~~~

* this opens the *editor* and allows to select changes:


Rebasing (Editor prompt)
--------

\BeforeBeginEnvironment{Shaded}{\scriptsize}

~~~ {.bash}
pick 2496330 * replace drawio-batch by drawio-desktop
pick fc00ba2 ! replace sudo calls by become:yes
pick e3691b3 ! fix docker group addition with become:yes

# Rebase e0e9946..e3691b3 onto 86330e6 (5 commands)
#
# Commands:
# p, pick <commit> = use commit
# r, reword <commit> = use commit, but edit the commit message
# e, edit <commit> = use commit, but stop for amending
# s, squash <commit> = use commit, but meld into previous commit
# f, fixup <commit> = like "squash", but discard this commit's log message
# x, exec <command> = run command (the rest of the line) using shell
# b, break = stop here (continue rebase later with 'git rebase --continue')
# d, drop <commit> = remove commit
# l, label <label> = label current HEAD with a name
# t, reset <label> = reset HEAD to a label
# m, merge [-C <commit> | -c <commit>] <label> [# <oneline>]
# .       create a merge commit using the original merge commit's
# .       message (or the oneline, if no original merge commit was
# .       specified). Use -c <commit> to reword the commit message.
~~~


Conflicts
---------

* *Conflicts occur* at merge/rebase/pull/etc. operations

* prefer resolution at *rebase operation* instead of merge
  * in a merge operation you can only fix the conflicts *at the end* of the commit history
  * in a rebase operation you can fix conflicts each *per commit* and then continue

* resolve conflicts in a *tool* $\to$ simplest one: `git gui`

* *participant* code parts in conflicts are named:


-------   ------   ------   --------
main      local    ours     upstream
feature   remote   theirs
------------------------------------

\colBegin{0.5}

* if using *merge*

  ~~~ {.bash}
  git checkout main
  git merge feature
  ~~~

\colNext{0.5}

* if using *rebase*

  ~~~ {.bash}
  git checkout feature
  git rebase main
  ~~~

\colEnd


Rerere
------

* allows git to "know" how to solve *already occured conflicts*

* enable in *config*:

  ~~~ {.bash}
  git config --global rerere.enabled true

  # do not directly commit but only stage conflict resolution [optional]
  git config --global rerere.autoupdate true
  ~~~

* works by *recording* conflict patterns and their solution (provided by you)


Patch files
-----------

* distribute one or multiple *commits per file*

* Creation:

  ~~~ {.bash}
  git format-patch <root_hash> --stdout > <my_commits>.patch
  ~~~

* Apply:

  ~~~ {.bash}
  git am < <my_commits>.patch
  ~~~


SVN
---

* do not use `svn` clients but *`git-svn`*
* "normal" local git repo with *`svn` remote*
* allows *lightweight branches* (at least locally)
* needs `git pull --rebase` *always*


Others
------

* Import CVS repos: [git-cvsimport](https://git-scm.com/docs/git-cvsimport)

* Allow to diff binary files like PDF/xlsx/etc. [Generating diff text](https://git-scm.com/docs/gitattributes#_generating_diff_text)

* Checkout only parts of repo: [git sparse checkout](https://briancoyner.github.io/2013/06/05/git-sparse-checkout.html)

* Multiple worktrees with common object store: [git_worktree]

* Collection of `.gitignore` parts: [gitignore]

* Grep which respects `.gitignore`: `git grep`
