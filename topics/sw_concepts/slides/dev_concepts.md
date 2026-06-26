Entwicklungs-Prinzipien
=======================


DRY
-------

* **D**on't **R**epeat **Y**ourself
* Für **Verhalten** aber auch für **Information**
* Idee: Wenn man Verhalten oder Information ändern muss,
  dann kann man das an einer Stelle tun.\
  Sonst müssen alle Kopien angepasst werden.
* Der Name der extrahierten Methode (z.b. für Verhalten)
  oder Konstante (z.b. für Information) sollte den Inhalt beschreiben.

DRY Beispiel für Konstanten 1
------

```java
assertEquals(new DateTime(2013, 6, 5, 12, 54).toDate(), view.getServiceStartDate());
assertEquals(new Long(4), view.getServiceSerialId());
assertEquals(new Long(2), view.getServicePeriodSerialId());
assertEquals("RGS_EMPTYING_TO_RETURNTRAY", view.getServiceType());
```

DRY Beispiel für Konstanten 2
------

```java
assertEquals(SERVICE_START_DATE, view.getServiceStartDate());
assertEquals(SERVICE_SERIAL_ID, view.getServiceSerialId());
assertEquals(SERVICE_PERIOD_SERIAL_ID, view.getServicePeriodSerialId());
assertEquals(ServiceType.RGS_EMPTYING_TO_RETURNTRAY, view.getServiceType());
```

KISS
-------

* **K**eep **i**t **S**hort and **S**imple\
  (Keep it simple stupid)

```python
# Good example (KISS)
result = sum(numbers) / len(numbers)

# Bad example (unnecessarily complex)
result = 0
for num in numbers:
  result += num
result = result / len(numbers)
```

YAGNI
-------

* **Y**ou **a**ren't **g**onna **n**eed **i**t
* Beispiele:
    * Performanceoptimierung, bevor Performance zu einem Problem wird.
    * Design für Anforderungen, die möglicherweise in der Zukunft auftreten könnten.

"We should forget about small efficiencies, say about 97% of the time: premature
optimization is the root of all evil.
Yet we should not pass up our opportunities in that critical 3%."

[Knuth, Donald (December 1974). "Structured Programming with go to Statements"](https://pic.plover.com/knuth-GOTO.pdf)

NIH
-------

* **N**ot **i**nvented **h**ere
* Dinge nicht selber implementieren, die schon in der Sprache oder einer Library enthalten sind.

NIH Beispiel 1
-------

```python
def custom_sort(numbers):
    for i in range(len(numbers)):
        for j in range(i + 1, len(numbers)):
            if numbers[i] > numbers[j]:
                numbers[i], numbers[j] = numbers[j], numbers[i]
    return numbers

sorted_list = custom_sort([3, 1, 4, 1, 5])

# Besser: built-in Funktion verwenden
sorted_list = sorted([3, 1, 4, 1, 5])
```
