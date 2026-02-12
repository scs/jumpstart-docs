from datetime import datetime


class Transaction:
    def __init__(self, transaction_id: str, amount: float, timestamp: datetime) -> None:
        self.transaction_id = transaction_id
        self.amount = amount
        self.timestamp = timestamp

    def __repr__(self) -> str:
        return f"Transaction({self.transaction_id}, ${self.amount:.2f}, {self.timestamp})"


class DBTransactionRepository:
    def __init__(self) -> None:
        self._transactions: dict[str, Transaction] = {}

    def save(self, transaction: Transaction) -> None:
        print(f"[DB] Saving transaction to database: {transaction.transaction_id}")
        self._transactions[transaction.transaction_id] = transaction

    def find_by_id(self, transaction_id: str) -> Transaction | None:
        print(f"[DB] Querying database for transaction: {transaction_id}")
        return self._transactions.get(transaction_id)


class TransactionService:
    def __init__(self) -> None:
        self.repository = DBTransactionRepository()

    def create_transaction(self, transaction_id: str, amount: float) -> Transaction:
        """Create and save a new transaction"""
        transaction = Transaction(transaction_id, amount, datetime.now())
        self.repository.save(transaction)
        return transaction

    def get_transaction(self, transaction_id: str) -> Transaction | None:
        """Retrieve a transaction by ID"""
        return self.repository.find_by_id(transaction_id)


if __name__ == "__main__":
    service = TransactionService()

    print("Creating transaction:")
    txn = service.create_transaction("TXN-001", 99.99)
    print(f"Created: {txn}")

    print("\nRetrieving transaction:")
    retrieved = service.get_transaction("TXN-001")
    print(f"Retrieved: {retrieved}")
