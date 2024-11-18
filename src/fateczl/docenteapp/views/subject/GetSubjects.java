package fateczl.docenteapp.views.subject;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import fateczl.csvdb.CsvContextFactory;
import fateczl.csvdb.CsvMapperFactory;
import fateczl.docenteapp.controllers.CourseController;
import fateczl.docenteapp.controllers.SubjectController;
import fateczl.docenteapp.model.Course;
import fateczl.docenteapp.model.Subject;
import fateczl.docenteapp.views.course.EditCourse;
import fateczl.docenteapp.views.dtos.CourseDto;
import fateczl.docenteapp.views.dtos.SubjectDto;
import fateczl.util.Queue;
import fateczl.util.swing.ButtonPanelEditor;
import fateczl.util.swing.ButtonPanelRenderer;
import fateczl.util.swing.CustomTableModel;
import fateczl.util.swing.FixedTableColumnModel;
import javax.swing.DefaultComboBoxModel;

public class GetSubjects extends JPanel {

	private final transient SubjectController subjectController;
	private final transient CourseController courseController;
	private JButton createButton;
	private JButton searchButton;
	private JComboBox<String> filterByComboBox;
	private JTextField searchTextField;
	private JPanel cardPanel;
	private JPanel menuPanel;
	private CardLayout cardLayout;
	private JSplitPane splitPane;
	private JScrollPane listPanel;
	private EditSubject editPanel;

	public GetSubjects() throws IOException {
		var subjectMapper = CsvMapperFactory.create(Subject.class);
		var subjectContext = CsvContextFactory.create("subjects", subjectMapper, Subject.class);
		this.subjectController = new SubjectController(subjectContext);

		var courseMapper = CsvMapperFactory.create(Course.class);
		var courseContext = CsvContextFactory.create("courses", courseMapper, Course.class);
		this.courseController = new CourseController(courseContext);

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
		cardPanel.add(new CreateSubject(subjectController, this, courseController), "Create Panel");
		editPanel = new EditSubject(subjectController, this);
		cardPanel.add(editPanel, "Edit Panel");
		
		System.out.println("NFJNF");

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
		var subjects = this.subjectController.getAll();

		Object[][] data = new Object[subjects.size()][8];

		int i = 0;
		
		System.out.println(subjects.isEmpty());

		while (!subjects.isEmpty()) {
			var subject = subjects.dequeue();
			data[i][0] = subject.getId();
			data[i][1] = subject.getName();
			data[i][2] = subject.getCode();
			data[i][3] = subjectController.getCourseName(subject.getCourseId());
			data[i][4] = subject.getDay();
			data[i][5] = subject.getHoursPerDay();
			data[i][6] = subject.getStartTime();
			data[i][7] = "Ações";
			i++;
		}

		var columnNames = new String[] { "Id", "Nome", "Código", "Curso", "Dia da Semana", "Duração", "Horário Inicial",
				"Ações" };

		var tableModel = new CustomTableModel(data, columnNames, new int[] { 7 });
		var columnModel = new FixedTableColumnModel(0);

		var table = new JTable(tableModel, columnModel);
		table.createDefaultColumnsFromModel();
		table.getColumn("Ações").setCellRenderer(new ButtonPanelRenderer());

		var buttonPanelEditor = new ButtonPanelEditor(new JCheckBox());

		buttonPanelEditor.getEditButton().addActionListener(e -> {
			var row = table.getSelectedRow();
			var id = table.getValueAt(row, 0);
			var code = table.getValueAt(row, 2);
			var name = table.getValueAt(row, 1);
			var course = subjectController.searchCourse(table.getValueAt(row, 3).toString());
			var day = table.getValueAt(row, 4);
			var hoursPerDay = table.getValueAt(row, 5);
			var startTime = table.getValueAt(row, 6);

			var subjectDto = new SubjectDto();
			subjectDto.setCode((String) code);
			subjectDto.setName((String) name);
			subjectDto.setCourseId(course);
			subjectDto.setDay((String) day);
			subjectDto.setHoursPerDay((String) hoursPerDay);
			subjectDto.setStartTime((String) startTime);

			editPanel.setSubjectId((Integer) id);
			editPanel.setSubjectDto(subjectDto);
			editPanel.loadData();

			cardLayout.show(cardPanel, "Edit Panel");
		});

		buttonPanelEditor.getDeleteButton().addActionListener(e -> {
			var row = table.getSelectedRow();
			var id = table.getValueAt(row, 0);
			subjectController.delete((Integer) id);

			refreshListPanel();
		});

		table.getColumn("Ações").setCellEditor(buttonPanelEditor);

		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(7).setPreferredWidth(160);

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

		filterByComboBox = new JComboBox<>(
				new String[] { "Nome", "Código", "Curso", "Dia da Semana", "Duração", "Horário Inicial" });
		filterByComboBox.setModel(new DefaultComboBoxModel(
				new String[] { "Nome", "Código", "Curso", "Dia da Semana", "Duração", "Horário Inicial" }));
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
