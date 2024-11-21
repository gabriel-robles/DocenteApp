package fateczl.docenteapp.views.subject;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import fateczl.docenteapp.controllers.CourseController;
import fateczl.docenteapp.controllers.SubjectController;
import fateczl.docenteapp.controllers.dtos.SubjectDto;
import fateczl.util.swing.ButtonPanelEditor;
import fateczl.util.swing.ButtonPanelRenderer;
import fateczl.util.swing.CustomTableModel;
import fateczl.util.swing.FixedTableColumnModel;

public class GetSubjects extends JPanel {
	private final transient SubjectController subjectController;
	private final transient CourseController courseController;
	private JButton createButton;
	private JPanel cardPanel;
	private JPanel menuPanel;
	private CardLayout cardLayout;
	private JSplitPane splitPane;
	private JScrollPane listPanel;
	private EditSubject editPanel;
	private CreateSubject createPanel;

	public GetSubjects(SubjectController subjectController, CourseController courseController) throws IOException {
		this.subjectController = subjectController;
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
		createPanel = new CreateSubject(subjectController, courseController, this);
		cardPanel.add(createPanel, "Create Panel");
		editPanel = new EditSubject(subjectController, courseController, this);
		cardPanel.add(editPanel, "Edit Panel");

		createButton.addActionListener(e -> {
			createPanel.loadData();
			cardLayout.show(cardPanel, "Create Panel");
		});
	}

	public void refreshListPanel() {
		splitPane.setBottomComponent(createListPanel());
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(75);
		splitPane.setDividerSize(0);
		createPanel.loadData();
		editPanel.loadData();
		revalidate();
		repaint();
	}

	private JScrollPane createListPanel() {
		var subjects = this.subjectController.getAll();

		Object[][] data = new Object[subjects.size()][9];

		int i = 0;

		while (!subjects.isEmpty()) {
			var subject = subjects.dequeue();
			data[i][0] = subject.getId();
			data[i][1] = subject.getProcess();
			data[i][2] = subject.getCode();
			data[i][3] = subject.getName();
			data[i][4] = subject.getCourseCode();
			data[i][5] = subject.getDay();
			data[i][6] = subject.getHoursPerDay();
			data[i][7] = subject.getStartTime();
			data[i][8] = "Ações";
			i++;
		}

		var columnNames = new String[] { "Id", "Número do Processo", "Código", "Nome", "Código do Curso", "Dia da Semana", "Duração",
				"Horário Inicial", "Ações" };

		var tableModel = new CustomTableModel(data, columnNames, new int[] { 8 });
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
			var process = table.getValueAt(row, 1);
			var code = table.getValueAt(row, 2);
			var name = table.getValueAt(row, 3);
			var courseCode = table.getValueAt(row, 4).toString();
			var day = table.getValueAt(row, 5);
			var hoursPerDay = table.getValueAt(row, 6);
			var startTime = table.getValueAt(row, 7);

			var subjectDto = new SubjectDto();
			subjectDto.setProcess((String) process);
			subjectDto.setCode((String) code);
			subjectDto.setName((String) name);
			subjectDto.setCourseCode(courseCode);
			subjectDto.setDay((String) day);
			subjectDto.setHoursPerDay((String) hoursPerDay);
			subjectDto.setStartTime((String) startTime);

			editPanel.setSubjectId((Integer) id);
			editPanel.setSubjectDto(subjectDto);
			editPanel.loadData();

			cardLayout.show(cardPanel, "Edit Panel");
		});

		deleteButton.addActionListener(e -> {
			var row = table.getSelectedRow();
			var id = table.getValueAt(row, 0);
			var process = table.getValueAt(row, 1);
			subjectController.delete((Integer) id, (String) process);

			refreshListPanel();
		});

		table.getColumn("Ações").setCellEditor(buttonPanelEditor);

		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(1).setMinWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(0);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(100);
		table.getColumnModel().getColumn(7).setPreferredWidth(100);
		table.getColumnModel().getColumn(8).setPreferredWidth(160);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		var scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		return scrollPane;
	}

	private JPanel createMenuPanel() {
		menuPanel = new JPanel();
		menuPanel.setLayout(null);

		var lblTitle = new JLabel("Disciplinas");
    lblTitle.setBounds(300, 26, 100, 14);

		createButton = new JButton("cadastrar");
		createButton.setBounds(550, 26, 100, 23);

		menuPanel.add(lblTitle);
		menuPanel.add(createButton);

		return menuPanel;
	}

	public String[] getCourses() {
		var courses = courseController.getAll();
		var courseNames = new String[courses.size()];

		int i = 0;

		while (!courses.isEmpty()) {
			var course = courses.dequeue();
			courseNames[i] = course.getName();
			i++;
		}

		return courseNames;
	}
}
