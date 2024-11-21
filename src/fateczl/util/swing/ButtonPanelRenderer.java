package fateczl.util.swing;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * ButtonPanelRenderer is a custom TableCellRenderer that renders a panel with
 * two buttons ("Editar" and "Deletar") in a table cell.
 * 
 * <p>This class extends JPanel and implements TableCellRenderer to provide
 * custom rendering of table cells. It creates a panel with a GridLayout
 * containing two buttons: one for editing and one for deleting.
 * 
 * <p>Usage example:
 * <pre>
 * {@code
 * JTable table = new JTable(data, columnNames);
 * table.getColumnModel().getColumn(columnIndex).setCellRenderer(new ButtonPanelRenderer());
 * }
 * </pre>
 * 
 * @see javax.swing.JPanel
 * @see javax.swing.table.TableCellRenderer
 */
public class ButtonPanelRenderer extends JPanel implements TableCellRenderer {
  public ButtonPanelRenderer(String button1Text, String button2Text) {
    setLayout(new GridLayout(1, 2));
    var button1 = new JButton(button1Text);
    var button2 = new JButton(button2Text);
    add(button1);
    add(button2);
  }

  public ButtonPanelRenderer(String button1Text) {
    setLayout(new GridLayout(1, 1));
    var button1 = new JButton(button1Text);
    add(button1);
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
      int row, int column) {
    return this;
  }
}
