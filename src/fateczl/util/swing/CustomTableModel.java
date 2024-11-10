package fateczl.util.swing;

import javax.swing.table.AbstractTableModel;

/**
 * CustomTableModel is a custom implementation of AbstractTableModel that allows
 * for specifying which columns are editable.
 * 
 * @see AbstractTableModel
 */
public class CustomTableModel extends AbstractTableModel {
  private final transient Object[][] data;
  private final String[] columnNames;
  private final int[] editableColumns;

  /**
   * Constructs a CustomTableModel with the specified data, column names, and editable columns.
   *
   * @param data            A 2D array of Objects representing the data of the table.
   * @param columnNames     An array of Strings representing the names of the columns.
   * @param editableColumns An array of integers representing the indices of the columns that are editable.
   */
  public CustomTableModel(Object[][] data, String[] columnNames, int[] editableColumns) {
    this.data = data;
    this.columnNames = columnNames;
    this.editableColumns = editableColumns;
  }

  @Override
  public int getRowCount() {
    return data.length;
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return data[rowIndex][columnIndex];
  }

  @Override
  public String getColumnName(int column) {
    return columnNames[column];
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    if (editableColumns == null) {
      return false;
    }

    for (int editableColumn : editableColumns) {
      if (column == editableColumn) {
        return true;
      }
    }

    return false;
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    data[rowIndex][columnIndex] = aValue;
    fireTableCellUpdated(rowIndex, columnIndex);
  }
}
