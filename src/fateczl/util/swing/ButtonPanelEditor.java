package fateczl.util.swing;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 * ButtonPanelEditor is a custom cell editor for a JTable that provides
 * a panel with two buttons: "Editar" and "Deletar". This editor allows
 * users to perform edit and delete actions directly from the table cell.
 */
public class ButtonPanelEditor extends DefaultCellEditor {
  private final JPanel panel;
  private final JButton editButton;
  private final JButton deleteButton;

  public JButton getEditButton() {
    return editButton;
  }

  public JButton getDeleteButton() {
    return deleteButton;
  }

  public ButtonPanelEditor(JCheckBox checkBox) {
    super(checkBox);
    panel = new JPanel();
    panel.setLayout(new GridLayout(1, 2));
    editButton = new JButton("Editar");
    deleteButton = new JButton("Deletar");
    panel.add(editButton);
    panel.add(deleteButton);
  }

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    return panel;
  }
}
