package fateczl.util.swing;

import javax.swing.table.DefaultTableColumnModel;

/**
 * A custom table column model that prevents a specified column from being moved.
 * This class extends DefaultTableColumnModel and overrides the moveColumn method
 * to ensure that the column at the fixed index cannot be moved.
 */
public class FixedTableColumnModel extends DefaultTableColumnModel {
    private final int fixedColumnIndex;

    public FixedTableColumnModel(int fixedColumnIndex) {
        this.fixedColumnIndex = fixedColumnIndex;
    }

    @Override
    public void moveColumn(int columnIndex, int newIndex) {
        if (columnIndex != fixedColumnIndex && newIndex != fixedColumnIndex) {
            super.moveColumn(columnIndex, newIndex);
        }
    }
}
