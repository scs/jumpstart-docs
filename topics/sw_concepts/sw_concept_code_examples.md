<#include meta/slides.md>

---
title: "SW Konzepte Code Beispiele"
date: \today
---

Code Beispiele
-------

Überlege für die folgenden Code Beispiele folgendes:

1. Hat das Beispiel etwas mit DRY, KISS, YAGNI oder NIH zu tun?
2. Hat es mit den SOLID Principles zu tun?
3. Welche Design Pattern siehst du?
4. Was könnte sonst noch verbessert werden?

OrganisationSupplier
-------

```python
class OrganisationSupplier:
    def get_current_organisation(self):
        pass

class ConfigurationOrganisationSupplier(OrganisationSupplier):
    def __init__(self, device_location_dao, konfiguration_dao, default_organisation):
        self.konfiguration_dao = konfiguration_dao
        self.default_organisation = default_organisation

    def get_current_organisation(self):
        return self.konfiguration_dao.get_konfigurationKonfigurationDAO.ORGANISATION_ID, self.default_organisation)

class DeviceLocationOrganisationSupplier(OrganisationSupplier):
    def __init__(self, konfiguration_dao, device_location_dao):
        self.konfiguration_dao = konfiguration_dao
        self.device_location_dao = device_location_dao

    def get_current_organisation(self):
        return Optional.ofNullable(self.konfiguration_dao.get_konfiguration(KonfigurationDAO.STANDORT_KENNUNG, None)) \
            .map(int) \
            .map(self.device_location_dao.get_device_location_by_id) \
            .map(lambda device_location_entity: device_location_entity.get_organisation())
```

OrganisationSupplier Auswertung
-------

1. DRY...: Property Keys werden geteilt (e.g. STANDORT_KENNUNG)
2. SOLID: nicht speziell
3. Pattern: Strategy
4. Verbesserung: -

AtmelObservableFactory
-------

```python
class AtmelObservableFactory:
    @staticmethod
    def create_power_state(host, port, scheduler):
        return AtmelObservableFactory.create_power_state_with_interval(host, port, INTERVAL, scheduler)

    @staticmethod
    def create_power_state_with_interval(host, port, interval, scheduler):
        simple_request = SimpleRequest("inputGetIgnitionState")
        ignition_state_request_executor = AtmelRequestExecutor(host, port, simple_request, IgnitionStateResult)
        return PollingObservable.create(scheduler, lambda: ..., interval, scheduler)

    @staticmethod
    def create_system_state(host, port, scheduler):
        return AtmelObservableFactory.create_system_state_with_interval(host, port, INTERVAL, scheduler)

    @staticmethod
    def create_system_state_with_interval(host, port, interval, scheduler):
        simple_request = SimpleRequest("registerObject", ["InfovisionSystemState"])
        return RetryUntilSuccess.create(AtmelRequestExecutor(host, port, simple_request, SystemStateResult), lambda: interval, scheduler)
```

AtmelObservableFactory Auswertung
-------

1. DRY...: YAGNI: muss das Interval konfigurierbar sein?
2. SOLID: Single Responsibility: muss beides hier erstellt werden?
3. Pattern: AbstractFactory, Factory method
4. Verbesserung: -

BrightnessParserTest
-------

```python
def test_parses_brightness_correctly(self):
        windows_brightness_output = """
    Power Setting GUID: 3c0bc021-c8a8-4e07-a973-6b14cbcb2b7e  (Turn off display after)
      Minimum Possible Setting: 0x00000000
      Maximum Possible Setting: 0xffffffff
      Possible Settings increment: 0x00000001
    Current AC Power Setting Index: 0x00000000
    Current DC Power Setting Index: 0x00003840

    Power Setting GUID: aded5e82-b909-4619-9949-f5d71dac0bcb  (Display brightness)
      Minimum Possible Setting: 0x00000000
      Maximum Possible Setting: 0x00000064
      Possible Settings increment: 0x00000001
      Possible Settings units: %
    Current AC Power Setting Index: 0x0000005a
    Current DC Power Setting Index: 0x0000005a
"""
        self.assertEqual(parse(windows_brightness_output), "0000005a")
```

BrightnessParserTest Auswertung
-------

1. DRY...: "0000005a" könnte geteilt und benannt werden
2. SOLID: -
3. Pattern: -
4. Verbesserung: -

DoorState
-------

```java
private synchronized void setDoorState(DoorState newDoorState,
                                       DoorComponent doorComponent) {
  doorContext.setDoorState(newDoorState, doorComponent);
  if (doorContext.isInconsistent()) {
   observers.forEach(DoorObserver::inconsistentSensors);
  } else if (DoorState.OPEN.equals(newDoorState)) {
   observers.forEach(DoorObserver::switchOpened);
  } else if (DoorState.CLOSED.equals(newDoorState) && doorContext.isDoorCompletelyClosed()) {
   observers.forEach(DoorObserver::doorClosed);
  }
 }
```

DoorState Auswertung
-------

1. DRY...: -
2. SOLID: Open/Closed: Für alle neuen Events muss das if statement und das DoorObserver Interface geändert werden.
3. Pattern: Observer
4. Verbesserung: -

TransactionRepository
-------

```java
public interface TransactionRepository {
 Transaction addTransactions(Collection<Transaction> transactions);

 void updateTransactions(Collection<Transaction> transactions);

 Optional<Transaction> findTransaction(UUID uuid);
}

HibernateTransactionRepository implements TransactionRepository {}

public class TransactionObserverTest {
 private TransactionObserver transactionObserver;
 private TransactionRepository transactionRepository;
 @Before
 public void givenTransactionObserver() {
  transactionRepository = mock(TransactionRepository.class);
  transactionObserver = new TransactionObserver(transactionRepository, createTerminalInfoExporter(), createLocationInfoExporter());
 }
```

TransactionRepository Auswertung
-------

1. DRY...: KISS: Ein Mock kann auch einfach mit einer Klasse umgesetzt werden
2. SOLID: Dependency Inversion
3. Pattern: -
4. Verbesserung: -

CreditCardAppIdMapperProvider
-------

```java
public class CreditCardAppIdMapperProvider implements Provider<CreditCardAppIdMapper> {
 private final CreditCardAppIdMapper creditCardAppIdMapper;

 public CreditCardAppIdMapperProvider(CreditCardAppIdMappingConfiguration configuration,
           CreditCardAppIdCategoryMappingParser creditCardAppIdCategoryMappingParser) {
  if (configuration.isEnabled()) {
   List<MappingEntry> mappingEntries = creditCardAppIdCategoryMappingParser.parse(new File(configuration.getMappingFilePath()));
   creditCardAppIdMapper = new CreditCardAppIdMapper(createMap(mappingEntries));
  } else {
   creditCardAppIdMapper = new CreditCardAppIdMapper(new HashMap<>());
  }
 }

 public CreditCardAppIdMapper get() {
  return creditCardAppIdMapper;
 }
 //...
}
```

CreditCardAppIdMapperProvider Auswertung
-------

1. DRY...: -
2. SOLID: -
3. Pattern: Factory, [Null Object](https://github.com/iluwatar/java-design-patterns/tree/master/null-object)
4. Verbesserung: Das parsen des Files (Input/Output oder I/O) im Konstruktor
kann das aufstarten Verzögern. Das könnte man wenn möglich asynchron machen.

Board
-------

```java
public class Board {
  private final List<BoardObserver> boardObservers = new ArrayList<>();

  public void executeMove(Move move) {
    Command command = createCommand(move);
    command.execute();
    boardObservers.forEach(boardObserver -> boardObserver.boardChanged(this));
  }
  
  public void registerObserver(BoardObserver boardObserver) {
    boardObservers.add(boardObserver);
  }
}
```

Board Auswertung
-------

1. DRY...: -
2. SOLID: -
3. Pattern: Observer
4. Verbesserung: Keine Möglichkeit um Observer zu entfernen $\rightarrow$ Memory Leak

Board2
-------

\colBegin{0.5}

```python
class SimpleMove(Command):
    def execute(self):
        remove_piece(self.start)
        add_piece(self.end)

    def undo(self):
        add_piece(self.start)
        remove_piece(self.end)
```

\colNext{0.5}

```python
class Board:
    def __init__(self):
        self.executed_commands = []

    def execute_move(self, move):
        command = self.create_command(move)
        command.execute()
        self.executed_commands.append(command)

    def undo_last_turn(self):
        if len(self.executed_commands) == 0:
            raise NoPreviousMovesException()

        last_command = get_executed_commands(-1)
        last_command.undo()
        self.executed_commands.pop()
```

\colEnd

Board2 Auswertung
-------

1. DRY...: -
2. SOLID: -
3. Pattern: Command
4. Verbesserung: -

DoorEventHandler
-------

```java
public class DoorEventHandler {
 private static final Logger LOGGER = Logger.getLogger(DoorEventHandler.class);

 private final DoorContext doorContext;

 private final Set<DoorObserver> observers = new HashSet<>();

 public DoorEventHandler(DoorContext doorContext) {
  this.doorContext = doorContext;
 }

 public void addObserver(DoorObserver observer) {
 }

 @Handler
 public void handle(DoorEvent event) {
 }
}
```

DoorEventHandler Auswertung
-------

1. DRY...: -
2. SOLID: -
3. Pattern: Observer
4. Verbesserung:
   1. Liste der Observer muss je nach Bedarf Threadsafe sein
   2. Keine Möglichkeit Observer zu entfernen -> Memory Leak

ButtonFactory
-------

```python
class ButtonFactory:
    def get_bwd_button(self, name, dim, text):
        return BwdButton(name, dim, text)

    def get_circle_button(self, name, color, text):
        return CircleButton(name, color, text)

    def get_fwd_button(self, name, dim, text):
        return FwdButton(name, dim, text)

    def get_info_button(self, name, info_text):
        return InfoButton(name, info_text)

class ButtonFactoryC(ButtonFactory):
    pass
```

ButtonFactory Auswertung
-------

1. DRY...: -
2. SOLID: -
3. Pattern: AbstractFactory
4. Verbesserung: Inheritance hat in diesem Fall zu Problemen geführt.
Da man nicht Voraussagen kann, wo welche Buttons gebraucht werden,
bietet sich hier Composition + Interface besser an.

ReceiptFactory
-------

```python
class ReceiptFactory:
    def create_eft_receipt(self, receipt_lines):
        pass

    def create_refund_receipt(self, kunden_session_nr, fehl_betrag, beleg_nr, mfk):
        pass

class Customer1ReceiptFactory(ReceiptFactory):
    def __init__(self, printer_config):
        self.printer_config = printer_config

    def create_eft_receipt(self, receipt_lines):
        pass

    def create_refund_receipt(self, kunden_session_nr, fehl_betrag, beleg_nr, mfk):
        print("No refund receipt implemented")
        return None
```

ReceiptFactory Auswertung
-------

1. DRY...: -
2. SOLID: create_refund_receipt verletzt Liskov's Substitution Principle
3. Pattern: AbstractFactory
4. Verbesserung: -

OperatingPoint
-----

```python
class OperatingPointNumberResolveStrategy:
    """
    Returns the vending location (Verkaufspunkt) assigned to the operating point number (Betriebspunkt)
    """
    def get_net_point(self, operating_point_number):
        pass

class DidokStrategy(OperatingPointNumberResolveStrategy):
    def get_net_point(self, operating_point_number):
        return self.assortment_provider.get_current_netpoint_by_didok_number(
                                            operating_point_number
                                        )

class SubstopNetpointIdStrategy(OperatingPointNumberResolveStrategy):
    def get_net_point(self, operating_point_number):
        return self.assortment_provider.get_current_netpoint_by_netpoint_id(
                                            operating_point_number,
                                            "SUBSTOP"
                                        )
```

OperatingPoint Auswertung
-------

1. DRY...: KISS: Hätte auch ein IF Statement gereicht?
2. SOLID: -
3. Pattern: Strategy
4. Verbesserung: -
