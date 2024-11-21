package fateczl.util.swing;

import javax.swing.table.AbstractTableModel;

public class GroupedTableModel extends AbstractTableModel {
  private final String[] columnNames;
  private final Object[][] data;

  public GroupedTableModel(String[] columnNames, Object[][] data, int groupColumnIndex) {
    this.columnNames = columnNames;
    this.data = data;
    groupData(data, groupColumnIndex);
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

  public static void groupData(Object[][] rows, int groupColumnIndex) {
    String prevGroup = "";
    for (Object[] row : rows) {
      if (row[groupColumnIndex].equals(prevGroup)) {
          row[groupColumnIndex] = " ";
      } else {
          prevGroup = row[groupColumnIndex].toString();
      }
    }
  }
}
