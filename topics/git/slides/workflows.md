Workflows
=========


Ultimatives Ziel
----------------

\Large

1. hohe Code-Qualität
1. gute Verbreitung des Know-Hows
1. effiziente und wertvolle Code-Reviews
1. saubere und klare Git-History
1. für alle Teammitglieder klarer Git-Workflow


[OneFlow][one_flow]
---------

\large

Vereinfacht die History zu einem linearen Verlauf durch Nutzen von `rebase` anstatt `merge`.

\vspace{1cm}

![Rebase vs. Merge](images/git_rebase_vs_merge.png)



---

\Large

> *Man kann auch einen anderen Workflow verwenden. Aber das Team muss sich bewusst sein welchen!*


\vspace{1cm}
\normalsize

Atlassian Artikel über Workflows: [comparing_workflows]

Git workflows:

* [one_flow]
* [git_flow]
* [trunk_based_development]
* [git_grundlagen_workflows]


`rebase` anstatt `merge` für Feature-Branches
---------------------------------------------

\colBegin{0.5}

*`merge`*

~~~ {.bash .numberLines}
// prepare history
git checkout -b feature
git commit
git commit
git checkout main
git commit
git commit

// merge commit
git merge feature
~~~

\colNext{0.5}

*`rebase`*

~~~ {.bash .numberLines}
// revert merge
git reset --hard HEAD~1

// rebase
git checkout feature
git rebase main

// update main
git checkout main
git merge feature
~~~

\colEnd


Feature-Branches
----------------

* gehören dem:der *Ersteller:in*
* nur der:die Ersteller:in darf ihn *push*en
* der:die Ersteller:in darf die History des Branches *beliebig neu schreiben*
* dazu wird ein *force* push benötigt: `git push --force-with-lease`

\vspace{0.4cm}

### `~/.gitconfig`

~~~
[alias]
  pushfwl = push --force-with-lease
~~~


voneinander abhängige Feature-Branches
--------------------------------------

\colBegin{0.5}

~~~ {.bash .numberLines}
// consecutive feature branches
git checkout -b feature1
git commit
git checkout -b feature2
git commit

// feature1 progresses
git checkout feature1
git commit
git commit

// main progresses
git checkout main
git commit
git commit
~~~

\colNext{0.5}

~~~ {.bash .numberLines}
// feature1 rebased to main
git checkout feature1
git rebase main

// normal rebase would create
// merge conflict here
git checkout feature2
git rebase feature1   // conflict

git reset --hard C3

// rebase onto allows to explicitly
// select all to pick commits
// (does not work in DEMO)
git rebase --onto feature1 HEAD~1
~~~

\colEnd
