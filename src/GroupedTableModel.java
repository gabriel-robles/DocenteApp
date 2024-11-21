import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GroupedTableModel extends AbstractTableModel {
    private final String[] columnNames;
    private final List<Object[]> data;

    public GroupedTableModel(String[] columnNames, List<Object[]> data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public static List<Object[]> groupData(List<Object[]> rawData, int groupColumnIndex) {
        Map<Object, List<Object[]>> groupedData = new TreeMap<>();
        for (Object[] row : rawData) {
            Object key = row[groupColumnIndex];
            groupedData.computeIfAbsent(key, k -> new ArrayList<>()).add(row);
        }

        List<Object[]> result = new ArrayList<>();
        for (Map.Entry<Object, List<Object[]>> entry : groupedData.entrySet()) {
            List<Object[]> group = entry.getValue();
            for (int i = 0; i < group.size(); i++) {
                if (i == 0) {
                    result.add(group.get(i));
                } else {
                    Object[] combinedRow = new Object[group.get(i).length];
                    combinedRow[groupColumnIndex] = entry.getKey();
                    for (int j = 0; j < group.get(i).length; j++) {
                        if (j != groupColumnIndex) {
                            combinedRow[j] = group.get(i)[j];
                        }
                    }
                    result.add(combinedRow);
                }
            }
        }
        return result;
    }
}