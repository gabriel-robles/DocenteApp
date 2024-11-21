package fateczl.docenteapp.views.registrations;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JCheckBox;

import fateczl.docenteapp.controllers.dtos.ProcessDto;
import fateczl.docenteapp.controllers.RegistrationController;
import fateczl.docenteapp.controllers.TeacherController;
import fateczl.util.swing.CustomTableModel;
import fateczl.util.swing.FixedTableColumnModel;
import fateczl.util.swing.ButtonPanelRenderer;
import fateczl.util.swing.ButtonPanelEditor;
import fateczl.util.sort.QuickSort;
import fateczl.util.sort.PivotType;

public class RegistrationDetail extends JPanel {
  private final transient RegistrationController registrationController;
  private final transient TeacherController teacherController;
  private ProcessDto process;
  private JSplitPane splitPane;
  private JLabel windowTitle;
  private JLabel lblCourseName;
  private JLabel lblSubjectName;
  private JLabel lblKnowledgeArea;
  private JLabel lblDay;
  private JLabel lblStartTime;
  private JLabel lblHoursPerDay;
  private JButton backButton;

  public RegistrationDetail(RegistrationController registrationController, TeacherController teacherController) {

    this.registrationController = registrationController;
    this.teacherController = teacherController;

    splitPane = new JSplitPane();
    splitPane.setPreferredSize(new Dimension(700, 420));

    setPreferredSize(new Dimension(700, 420));

    splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    splitPane.setDividerLocation(130);
    splitPane.setDividerSize(0);
    splitPane.setTopComponent(createInfoPanel());
    splitPane.setBottomComponent(createListPanel());

    add(splitPane);

    backButton.addActionListener(e -> {
	    var parent = (JPanel) getParent();
	    var cardLayout = (CardLayout) parent.getLayout();
	    cardLayout.show(parent, "Menu Panel");
	  });
  }

  public void setProcess(ProcessDto process) {
    this.process = process;
  }

  public void update() {
    windowTitle.setText("Detalhes do Processo: " + process.getProcess());
    lblCourseName.setText("Nome do Curso: " + process.getCourseName());
    lblSubjectName.setText("Nome da Disciplina: " + process.getSubjectName());
    lblKnowledgeArea.setText("Área de Conhecimento: " + process.getKnowledgeArea());
    lblDay.setText("Dia da Semana: " + process.getDay());
    lblStartTime.setText("Horário de Início: " + process.getStartTime());
    lblHoursPerDay.setText("Horas por Dia: " + process.getHoursPerDay());

    splitPane.setBottomComponent(createListPanel());
    splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    splitPane.setDividerLocation(130);
    splitPane.setDividerSize(0);
    revalidate();
    repaint();
  }

  private JScrollPane createListPanel() {
    if (process == null) {
      return new JScrollPane();
    }

    var registrations = registrationController.findMany(r -> r.getProcessCode().equals(process.getProcess()));

    var data = new Object[registrations.size()][8];
    
    int i = 0;

    while (!registrations.isEmpty()) {
      var registration = registrations.dequeue();
      var teacher = teacherController.find(t -> t.getCpf().equals(registration.getCpf()));
      
      data[i][0] = registration.getId();
      data[i][1] = registration.getProcessCode();
      data[i][2] = registration.getSubjectCode();
      data[i][3] = teacher.getName();
      data[i][4] = teacher.getCpf();
      data[i][5] = teacher.getArea();
      data[i][6] = teacher.getScore();
      data[i][7] = "Ações";
      i++;
    }

    QuickSort.sort(data, 6, PivotType.RANDOM);

    var columnNames = new String[] { 
      "Id", "Código do Processo", "Código da Disciplina", "Nome do Docente", "CPF do Docente", "Área do Docente", "Pontuação do Docente", "Ações" };

    var tableModel = new CustomTableModel(data, columnNames, new int[] { 7 });
    var columnModel = new FixedTableColumnModel();

    var table = new JTable(tableModel, columnModel);
    table.createDefaultColumnsFromModel();
    table.getColumn("Ações").setCellRenderer(new ButtonPanelRenderer("Cancelar Inscrição"));

    var cancelButton = new JButton("Cancelar Inscrição");

    var buttonPanelEditor = new ButtonPanelEditor(new JCheckBox(), cancelButton);

    cancelButton.addActionListener(e -> {
      var row = table.getSelectedRow();
      var id = (int) table.getValueAt(row, 0);
      registrationController.delete(id);
      update();
    });

    table.getColumn("Ações").setCellEditor(buttonPanelEditor);

    table.getColumnModel().getColumn(0).setMinWidth(0);
    table.getColumnModel().getColumn(0).setMaxWidth(0);
    table.getColumnModel().getColumn(0).setWidth(0);
    table.getColumnModel().getColumn(0).setPreferredWidth(0);
    table.getColumnModel().getColumn(7).setMinWidth(160);

    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    var scrollPane = new JScrollPane(table);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    return scrollPane;
  }

  private JPanel createInfoPanel() {
    var infoPanel = new JPanel();

    windowTitle = new JLabel("");
	  windowTitle.setBounds(250, 10, 250, 20);
    
    lblCourseName = new JLabel("");
    lblCourseName.setBounds(10, 40, 350, 20);
    
    lblSubjectName = new JLabel("");
    lblSubjectName.setBounds(370, 40, 300, 20);

    lblKnowledgeArea = new JLabel("");
    lblKnowledgeArea.setBounds(10, 70, 350, 20);

    lblDay = new JLabel("");
    lblDay.setBounds(370, 70, 300, 20);

    lblStartTime = new JLabel("");
    lblStartTime.setBounds(10, 100, 350, 20);

    lblHoursPerDay = new JLabel("");
    lblHoursPerDay.setBounds(370, 100, 300, 20);
    
    backButton = new JButton("voltar");
    backButton.setBounds(10, 10, 80, 20);

    infoPanel.setLayout(null);
    infoPanel.add(windowTitle);
    infoPanel.add(lblCourseName);
    infoPanel.add(lblSubjectName);
    infoPanel.add(lblKnowledgeArea);
    infoPanel.add(lblDay);
    infoPanel.add(lblStartTime);
    infoPanel.add(lblHoursPerDay);
    infoPanel.add(backButton);

    return infoPanel;
  }
}
