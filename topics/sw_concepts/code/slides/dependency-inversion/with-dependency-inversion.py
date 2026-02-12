from abc import ABC, abstractmethod
from datetime import datetime


class Transaction:
    def __init__(self, transaction_id: str, amount: float, timestamp: datetime) -> None:
        self.transaction_id = transaction_id
        self.amount = amount
        self.timestamp = timestamp

    def __repr__(self) -> str:
        return f"Transaction({self.transaction_id}, ${self.amount:.2f}, {self.timestamp})"


class TransactionRepository(ABC):
    @abstractmethod
    def save(self, transaction: Transaction) -> None:
        pass

    @abstractmethod
    def find_by_id(self, transaction_id: str) -> Transaction | None:
        pass


class TransactionService:
    def __init__(self, repository: TransactionRepository) -> None:
        self.repository = repository

    def create_transaction(self, transaction_id: str, amount: float) -> Transaction:
        transaction = Transaction(transaction_id, amount, datetime.now())
        self.repository.save(transaction)
        return transaction

    def get_transaction(self, transaction_id: str) -> Transaction | None:
        return self.repository.find_by_id(transaction_id)


class DBTransactionRepository(TransactionRepository):
    def __init__(self) -> None:
        self._transactions: dict[str, Transaction] = {}

    def save(self, transaction: Transaction) -> None:
        print(f"[DB] Saving transaction to database: {transaction.transaction_id}")
        self._transactions[transaction.transaction_id] = transaction

    def find_by_id(self, transaction_id: str) -> Transaction | None:
        print(f"[DB] Querying database for transaction: {transaction_id}")
        return self._transactions.get(transaction_id)


class InMemoryTransactionRepository(TransactionRepository):
    def __init__(self) -> None:
        self._transactions: dict[str, Transaction] = {}

    def save(self, transaction: Transaction) -> None:
        print(f"[Memory] Saving transaction to memory: {transaction.transaction_id}")
        self._transactions[transaction.transaction_id] = transaction

    def find_by_id(self, transaction_id: str) -> Transaction | None:
        print(f"[Memory] Retrieving transaction from memory: {transaction_id}")
        return self._transactions.get(transaction_id)


if __name__ == "__main__":
    print("Using DB Repository:")
    db_repo = DBTransactionRepository()
    service = TransactionService(db_repo)

    txn = service.create_transaction("TXN-001", 99.99)
    print(f"Created: {txn}")

    retrieved = service.get_transaction("TXN-001")
    print(f"Retrieved: {retrieved}")

    print("\nUsing In-Memory Repository:")
    memory_repo = InMemoryTransactionRepository()
    service2 = TransactionService(memory_repo)

    txn2 = service2.create_transaction("TXN-002", 49.99)
    print(f"Created: {txn2}")
