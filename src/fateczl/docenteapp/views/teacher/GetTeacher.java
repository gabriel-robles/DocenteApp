package fateczl.docenteapp.views.teacher;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import fateczl.docenteapp.controllers.TeacherController;
import fateczl.docenteapp.controllers.dtos.TeacherDto;
import fateczl.util.swing.ButtonPanelRenderer;
import fateczl.util.swing.CustomTableModel;
import fateczl.util.swing.FixedTableColumnModel;
import fateczl.util.swing.ButtonPanelEditor;

public class GetTeacher extends JPanel
{
	 private final transient TeacherController teacherController;
	  private JButton createButton;
	  private JPanel cardPanel;
	  private JPanel menuPanel;
	  private CardLayout cardLayout;
	  private JSplitPane splitPane;
	  private JScrollPane listPanel;
	  private EditTeacher editPanel;

	  public GetTeacher(TeacherController teacherController) throws IOException{
	    this.teacherController = teacherController;

	    splitPane = new JSplitPane();
	    splitPane.setPreferredSize(new Dimension(700, 425));

	    cardLayout = new CardLayout();
	    cardPanel = new JPanel(cardLayout);

	    setPreferredSize(new Dimension(700, 425));
	    add(cardPanel);

	    splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
	    splitPane.setDividerLocation(75);
	    splitPane.setDividerSize(0);
	    menuPanel = createMenuPanel();
	    splitPane.setTopComponent(menuPanel);
	    listPanel = createListPanel();
	    splitPane.setBottomComponent(listPanel);

	    cardPanel.add(splitPane, "Menu Panel");
	    cardPanel.add(new CreateTeacher(teacherController, this), "Create Panel");
	    editPanel = new EditTeacher(teacherController, this);
	    cardPanel.add(editPanel, "Edit Panel");

	    createButton.addActionListener(e -> {
	      cardLayout.show(cardPanel, "Create Panel");
	    });
	  }

	  public void refreshListPanel() {
	    splitPane.setBottomComponent(createListPanel());
	    splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
	    splitPane.setDividerLocation(75);
	    splitPane.setDividerSize(0);
	    revalidate();
	    repaint();
	  }

	  private JScrollPane createListPanel() {
	    var teacher = this.teacherController.getAll();

	    Object[][] data = new Object[teacher.size()][6];

	    int i = 0;

	    while (!teacher.isEmpty()) {
	      var teachers = teacher.dequeue();
	      data[i][0] = teachers.getId();
	      data[i][1] = teachers.getName();
	      data[i][2] = teachers.getCpf();
	      data[i][3] = teachers.getArea();
	      data[i][4] = teachers.getScore();
	 	  data[i][5] ="Ações";
	      i++;
	    }

	    var columnNames = new String[] { "Id", "Nome", "CPF", "Área de interesse","Pontuação", "Ações" };

	    var tableModel = new CustomTableModel(data, columnNames, new int[] { 5 });
	    var columnModel = new FixedTableColumnModel();

	    var table = new JTable(tableModel, columnModel);
	    table.createDefaultColumnsFromModel();
	    table.getColumn("Ações").setCellRenderer(new ButtonPanelRenderer("Editar", "Deletar"));

			var editButton = new JButton("Editar");
			var deleteButton = new JButton("Deletar");

	    var buttonPanelEditor = new ButtonPanelEditor(new JCheckBox(), editButton, deleteButton);
	    
	    editButton.addActionListener(e -> {
	      var row = table.getSelectedRow();
	      var id = table.getValueAt(row, 0);
	      var nomeTeacher = table.getValueAt(row, 1);
	      var nCPFTeacher = table.getValueAt(row, 2);
	      var areaTeacher = table.getValueAt(row, 3);
	      var pontuacao = table.getValueAt(row, 4);

	      var teacherDto = new TeacherDto();
	      teacherDto.setNomeTeacher((String) nomeTeacher);
	      teacherDto.setCPFTeacher((String) nCPFTeacher);
	      teacherDto.setAreaTeacher((String) areaTeacher);
	      teacherDto.setPontuacaoTeacher((Integer) pontuacao);

	      editPanel.setTeacherId((Integer) id);
	      editPanel.setTeacherDto(teacherDto);
	      editPanel.loadData();

	      cardLayout.show(cardPanel, "Edit Panel");
	    });

	    deleteButton.addActionListener(e -> {
	      var row = table.getSelectedRow();
	      var id = table.getValueAt(row, 0);
	      teacherController.delete((Integer) id);

	      refreshListPanel();
	    });

	    table.getColumn("Ações").setCellEditor(buttonPanelEditor);

	    table.getColumnModel().getColumn(0).setMinWidth(0);
	    table.getColumnModel().getColumn(0).setMaxWidth(0);
	    table.getColumnModel().getColumn(0).setWidth(0);
	    table.getColumnModel().getColumn(0).setPreferredWidth(0);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
			table.getColumnModel().getColumn(3).setPreferredWidth(200);
	    table.getColumnModel().getColumn(4).setPreferredWidth(80);
			table.getColumnModel().getColumn(5).setPreferredWidth(160);

	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	    var scrollPane = new JScrollPane(table);
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	    return scrollPane;
	  }

	  private JPanel createMenuPanel() {
	    menuPanel = new JPanel();
	    menuPanel.setLayout(null);

	    var lblTitle = new JLabel("Professores");
    	lblTitle.setBounds(300, 26, 100, 14);

	    createButton = new JButton("cadastrar");
	    createButton.setBounds(550, 26, 100, 23);

			menuPanel.add(lblTitle);
	    menuPanel.add(createButton);

	    return menuPanel;
	  }
	  

}
