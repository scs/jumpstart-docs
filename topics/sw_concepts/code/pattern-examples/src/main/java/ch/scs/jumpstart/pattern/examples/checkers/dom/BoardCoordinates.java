package ch.scs.jumpstart.pattern.examples.checkers.dom;

public record BoardCoordinates(Row row, Column column) {

  public enum Row {
    ROW_1,
    ROW_2,
    ROW_3,
    ROW_4,
    ROW_5,
    ROW_6,
    ROW_7,
    ROW_8;

    public boolean isLastRow() {
      return this == ROW_8;
    }

    public boolean isFirstRow() {
      return this == ROW_1;
    }

    public int diffRow(Row row) {
      return this.ordinal() - row.ordinal();
    }

    public Row getRowBetween(Row row) {
      int indexBetween = (this.ordinal() + row.ordinal()) / 2;
      return Row.values()[indexBetween];
    }
  }

  public enum Column {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H;

    public int diffCol(Column column) {
      return this.ordinal() - column.ordinal();
    }

    public Column getColBetween(Column column) {
      int indexBetween = (this.ordinal() + column.ordinal()) / 2;
      return Column.values()[indexBetween];
    }
  }
}
