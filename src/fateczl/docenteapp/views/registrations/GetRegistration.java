package fateczl.docenteapp.views.registrations;

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

import fateczl.docenteapp.controllers.CourseController;
import fateczl.docenteapp.controllers.RegistrationController;
import fateczl.docenteapp.controllers.SubjectController;
import fateczl.docenteapp.controllers.TeacherController;
import fateczl.util.swing.ButtonPanelEditor;
import fateczl.util.swing.ButtonPanelRenderer;
import fateczl.util.swing.CustomTableModel;
import fateczl.util.swing.FixedTableColumnModel;

public class GetRegistration extends JPanel{
	private final transient CourseController courseController;
	private JPanel cardPanel;
	private JPanel menuPanel;
	private CardLayout cardLayout;
	private JSplitPane splitPane;
	private JScrollPane listPanel;
	private CreateRegistration createPanel;
	private RegistrationDetail detailPanel;

	public GetRegistration(CourseController courseController, RegistrationController registrationController,
		SubjectController subjectController, TeacherController teacherController) throws IOException {

		this.courseController = courseController;

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
		createPanel = new CreateRegistration(registrationController, this, subjectController, teacherController);
	  cardPanel.add(createPanel, "Create Panel");
		detailPanel = new RegistrationDetail(registrationController, teacherController);
		cardPanel.add(detailPanel, "Detail Panel");
	}

	public void refreshListPanel() {
	  splitPane.setBottomComponent(createListPanel());
	  splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
	  splitPane.setDividerLocation(75);
	  splitPane.setDividerSize(0);
		createPanel.loadData();
	  revalidate();
	  repaint();
	}

	private JScrollPane createListPanel() {
		var processes = courseController.getProcesses();

		Object[][] data = new Object[processes.size()][8];

	  int i = 0;

		for (var entry : processes.toArray()) {
			var process = entry.getValue();
			data[i][0] = process.getCourseName();
			data[i][1] = process.getProcess();
			data[i][2] = process.getSubjectName();
			data[i][3] = process.getKnowledgeArea();
			data[i][4] = process.getDay();
			data[i][5] = process.getStartTime();
			data[i][6] = process.getHoursPerDay();
			data[i][7] = "Ações";
			i++;
		}

	  var columnNames = new String[] { 
			"Curso", "Processo", "Disciplina", "Área do Conhecimento", "Dia da Semana", "Horário de Inicio", "Horas", "Ações" };

	  var tableModel = new CustomTableModel(data, columnNames, new int[] { 7 });
	  var columnModel = new FixedTableColumnModel();

	  var table = new JTable(tableModel, columnModel);
	  table.createDefaultColumnsFromModel();
	  table.getColumn("Ações").setCellRenderer(new ButtonPanelRenderer("Inscrever", "Detalhes"));

		var registerButton = new JButton("Inscrever");
		var detailsButton = new JButton("Detalhes");

	  var buttonPanelEditor = new ButtonPanelEditor(new JCheckBox(), registerButton, detailsButton);

	  registerButton.addActionListener(e -> {
			createPanel.setProcessCode(table.getValueAt(table.getSelectedRow(), 1).toString());
	    createPanel.loadData();

	    cardLayout.show(cardPanel, "Create Panel");
	  });

	  detailsButton.addActionListener(e -> {
			var process = processes.get(table.getValueAt(table.getSelectedRow(), 1).toString());
			detailPanel.setProcess(process);
			detailPanel.update();

	    cardLayout.show(cardPanel, "Detail Panel");
	  });

	  table.getColumn("Ações").setCellEditor(buttonPanelEditor);

		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(120);
		table.getColumnModel().getColumn(6).setPreferredWidth(80);
	  table.getColumnModel().getColumn(7).setPreferredWidth(180);

	  table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	  var scrollPane = new JScrollPane(table);
	  scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	  return scrollPane;
	}

	private JPanel createMenuPanel() {
	  menuPanel = new JPanel();
	  menuPanel.setLayout(null);

	  var lblTitle = new JLabel("Processos em Aberto");
    lblTitle.setBounds(300, 26, 160, 14);

		menuPanel.add(lblTitle);

	  return menuPanel;
	}
}
