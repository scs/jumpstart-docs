Markup Languages
================


Eigenschaften
-------------

* *Lesbarkeit* (für Menschen)
* Kompakte oder redundante *Syntax*
* *Parsing*
* Verfügbare *Datentypen*
* Primäre *Nutzung*: Daten oder Text


Beispiele
---------

*Daten*-Verarbeitung:

* *SGML*: Standard Generalized Markup Language
* *XML*: Extensible Markup Language
* *HTML*: HyperText Markup Language
* *JSON*: JavaScript Object Notation
* *YAML*: YAML Ain’t Markup Language

*Text*-Verarbeitung:

* *ReST*: reStructuredText
* *LaTeX*
* *Markdown*


JSON
----

\colBegin{0.45}

* Config-Files
* Serialisierte Daten
* Syntax: [json_tutorial]
* `camelCase`
* Achtung: *Kommas*!

~~~{.json}
{
  "string": "my string",
  "boolean": true,
  "integerNumber": -123,
  "floatNumber": 3.1415,
  "integerArray": [ 1, 2, 3, 4 ],
  "stringArray": [ "A", "B", "C" ]
}
~~~

\colNext{0.55}

~~~{.json}
{
  "object": {
    "myNumber": 123
  },
  "map": [
    { "key": "value" },
    { "another": "whatever" }
  ],
  "objectList": [
    {
      "key1": 1,
      "key2": "bla",
      "key3": { "subKey1": true, "subKey2": -1.2 }
    },
    { "smallObject": 2 }
  ]
}
~~~

\colEnd


YAML
----

* Config-Files
* Beschreibende Programmierung
* Superset von JSON
* JSON kann in YAML konvertiert werden
* Syntax: [yaml_syntax]
* Achtung: *Spaces*!

\colBegin{0.45}

~~~{.yaml}
string: my string
anotherString: "with quotes"
boolean: true
integerNumber: -123
floatNumber: 3.1415
list:
  - 1
  - 2
~~~

\colNext{0.55}

~~~{.yaml}
dictionary:
  key: value
  another: "dictionaries are objects"

objectList:
  - key1: 1
    key2: "bla"
    key3: { subKey1: true, subKey2: -1.2 }
  - otherStruct: 2
    inlineList: [ 1, 2, 3, 4 ]
~~~

\colEnd


Markdown
--------

* Lesbarer Rohtext welcher formatiert werden kann
* `README.md`
* Syntax: [markdown_guide]

\colBegin{0.3}

~~~{.markdown}
Header 1
========

Header 2
--------

# Header 1
## Header 2
### Header 3 etc

Table     Header
-----     ------
Content   Number
~~~

\colNext{0.3}

~~~~{.markdown}
*Italic*
**Fett**
***Fett & Italic***

> Zitat

`Code`

~~~
Code
Block
~~~

~~~~

\colNext{0.5}

~~~{.markdown}
* Unordered List A
  * Sublist
* Unordered List B

1. Ordered List A
1. Ordered List B

![MeinBild](bild.jpeg)

[MeinLink](link.html)

[reference_link]: reference_link.html
[reference_link]
[Reference Link][reference_link]
~~~

\colEnd
