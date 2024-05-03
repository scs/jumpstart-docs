package ch.scs.jumpstart.pattern.examples.receipts;

// details omitted
public class Customer1ReceiptFactory implements ReceiptFactory {
  @Override
  public Receipt createEftReceipt() {
    // details omitted
    return new Receipt();
  }

  @Override
  public Receipt createRefundReceipt() {
    throw new UnsupportedOperationException(
        "%s does not support refund".formatted(getClass().getSimpleName()));
  }

  @Override
  public Receipt createContactlessReceipt() {
    // details omitted
    return new Receipt();
  }
}
