Concurrency vs. Parallelism
===========================


Concurrency vs. Parallelism
---------------------------

Concurrency                                 Parallelism
-----------                                 -----------
*gefühlte* Parallelität                     benötigt *mehrere CPU-Cores*
$\to$ benötigt nur *1 CPU-Core*

### Verwendung

* Concurrency: *zeitlich unabhängige Tasks*
* Parallelism: *Problem schneller lösen*


Implementation von Concurrency
------------------------------

*Architektur* einfach halten:

* 1 Thread pro Prozess
* Probleme in Prozesse aufteilen
* Interprocess-Communication (*IPC*)

Anstatt mehrere Threads für Concurrency: $\to$ *Event-Loop*

* Definition: [wiki_event_loop]
* *kooperatives Scheduling* (userspace)
* baut meist auf *Betriebssystem*-Funktionen auf
* diverse Libraries
  * [win_main]
  * [glib_main_event_loop]
  * [sd_event]
  * [boost_asio]


Beispiel: sd-event
------------------

~~~ {.cpp .numberLines}
#include <cassert>

#include <sys/socket.h>

#include <systemd/sd-event.h>
#include <systemd/sd-daemon.h>

// create event loop
sd_event* event_loop = nullptr;
int result = sd_event_default(&event_loop);
assert(result >= 0);

// enable watchdog handling
result = sd_event_set_watchdog(event_loop, true);
assert(result >= 0);
~~~


Beispiel: sd-event
------------------

~~~ {.cpp .numberLines}
static int io_handler(sd_event_source* es,
    int fd, uint32_t revents, void* userdata) {
  // ...
  return 0;
}

// open UDP socket with asynchronous operation mode
int fd = socket(AF_INET, SOCK_DGRAM|SOCK_CLOEXEC|SOCK_NONBLOCK, 0);
assert(fd >= 0);
// ...

// register I/O event for incoming messages on fd
sd_event_source* io_event = nullptr;
result = sd_event_add_io(event_loop, &io_event, fd, EPOLLIN, io_handler, nullptr);
assert(result >= 0);
~~~


Beispiel: sd-event
------------------

~~~ {.cpp .numberLines}
static int timer_handler(sd_event_source* s,
    uint64_t usec, void* userdata) {
  // ...
  return 0;
}

// register timer event with interval 1ms (accuracy 100us)
sd_event_source* timer_event = nullptr;
result = sd_event_add_time(event_loop, &timer_event, CLOCK_MONOTONIC,
                           1000, 100, timer_handler, nullptr);
assert(result >= 0);

// notify system manager about finished startup
sd_notify(false, "READY=1\nSTATUS=Startup completed.");

// loop forever and process events
result = sd_event_loop(event_loop);
assert(result >= 0);
~~~
