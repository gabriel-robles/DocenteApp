package fateczl.docenteapp.views.teacher;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import fateczl.csvdb.CsvContextFactory;
import fateczl.csvdb.CsvMapperFactory;
import fateczl.docenteapp.controllers.TeacherController;
import fateczl.docenteapp.model.Teacher;
import fateczl.docenteapp.views.dtos.TeacherDto;
import fateczl.util.swing.ButtonPanelRenderer;
import fateczl.util.swing.CustomTableModel;
import fateczl.util.swing.FixedTableColumnModel;
import fateczl.util.swing.ButtonPanelEditor;

public class GetTeacher extends JPanel
{
	 private final transient TeacherController teacherController;
	  private JButton createButton;
	  private JButton searchButton;
	  private JComboBox<String> filterByComboBox;
	  private JTextField searchTextField;
	  private JPanel cardPanel;
	  private JPanel menuPanel;
	  private CardLayout cardLayout;
	  private JSplitPane splitPane;
	  private JScrollPane listPanel;
	  private EditTeacher editPanel;

	  public GetTeacher() throws IOException{
	    var teacherMapper = CsvMapperFactory.create(Teacher.class);
	    var teacherContext = CsvContextFactory.create("teacher", teacherMapper, Teacher.class);
	    this.teacherController = new TeacherController(teacherContext);

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
	      data[i][1] = teachers.getNomeTeacher();
	      data[i][2] = teachers.getCPFTeacher();
	      data[i][3] = teachers.getAreaTeacher();
	      data[i][4] = teachers.getPontuacaoTeacher();
	 	  data[i][5] ="Ações";
	      i++;
	    }

	    var columnNames = new String[] { "Id", "Nome", "CPF", "Área de interesse","Pontuação", "Ações" };

	    var tableModel = new CustomTableModel(data, columnNames, new int[] { 5 });
	    var columnModel = new FixedTableColumnModel(0);

	    var table = new JTable(tableModel, columnModel);
	    table.createDefaultColumnsFromModel();
	    table.getColumn("Ações").setCellRenderer(new ButtonPanelRenderer());

	    var buttonPanelEditor = new ButtonPanelEditor(new JCheckBox());
	    
	    buttonPanelEditor.getEditButton().addActionListener(e -> {
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

	    buttonPanelEditor.getDeleteButton().addActionListener(e -> {
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
	    table.getColumnModel().getColumn(4).setPreferredWidth(160);

	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	    var scrollPane = new JScrollPane(table);
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	    return scrollPane;
	  }

	  private JPanel createMenuPanel() {
	    menuPanel = new JPanel();
	    menuPanel.setLayout(null);

	    var filterByLabel = new JLabel("Filtrar por");
	    filterByLabel.setBounds(10, 5, 200, 25);

	    filterByComboBox = new JComboBox<>(new String[] { "Nome", "CPF", "Área de Interesse","Pontuação" });
	    filterByComboBox.setBounds(10, 26, 160, 23);

	    searchTextField = new JTextField();
	    searchTextField.setBounds(172, 26, 200, 25);

	    searchButton = new JButton("buscar");
	    searchButton.setBounds(374, 26, 100, 23);

	    createButton = new JButton("cadastrar");
	    createButton.setBounds(550, 26, 100, 23);

	    menuPanel.add(filterByLabel);
	    menuPanel.add(filterByComboBox);
	    menuPanel.add(searchTextField);
	    menuPanel.add(searchButton);
	    menuPanel.add(createButton);

	    return menuPanel;
	  }
	  

}
