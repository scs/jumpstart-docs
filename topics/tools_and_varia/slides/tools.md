Tools
=====


Regex
-----

* Kurz für: *Regular Expressions*
* Eine Art um *Pattern Matching* zu spezifizieren.
* Ursprung in der *theoretischen Informatik*.
* Sandbox im Browser: [regex101] oder [regexr]

\vspace{0.5cm}
Beispiel:

~~~{.cpp}
#include <gtest/gtest.h>
#include "gmock/gmock.h"
~~~

Suchen mit: *`#include "(.+)"`*  
Ersetzen mit: *`#include <$1>`*

Regex Basics
------------

Symbol    Explanation
------    -----------
`\`       Escape
`?`       Repetition of 0 or 1
`*`       Repetition of >= 0
`+`       Repetition of >= 1
`.`       Any character
`(?s:.)`  Any character including new line
`\\s`     Whitespace character
`\\S`     Non whitespace character
`[0-2]`   Character 0, 1 or 2
`[A-Z]`   Character big A to big Z
`[^,.]`   Characters except: , and .
`^abc`    abc at the start of the string
`abc$`    abc at the end of the string


Putty
-----

* *SSH Client* für Windows
* Bringt eigenes *Key-Management*: `pageant`
  * kann als `ssh-agent` fungieren inkl. *Agent-Forwarding*
  * kann von anderen Tools verwendet werden:
    * git bash (git for Windows)
    * Total Commander

*Style/Health*

* [pretty_putty]

*Alternativen:*

* [kitty]
* [solar_putty]
* [mobaxterm]
