package ch.scs.jumpstart.pattern.examples.receipts;

@SuppressWarnings("unused")
public interface ReceiptFactory {
  Receipt createEftReceipt();

  Receipt createRefundReceipt();

  Receipt createContactlessReceipt();
}
