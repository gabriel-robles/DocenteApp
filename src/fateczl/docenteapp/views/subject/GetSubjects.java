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
import fateczl.docenteapp.views.course.CreateCourse;
import fateczl.docenteapp.views.course.EditCourse;
import fateczl.docenteapp.views.dtos.CourseDto;
import fateczl.util.swing.ButtonPanelEditor;
import fateczl.util.swing.ButtonPanelRenderer;
import fateczl.util.swing.CustomTableModel;
import fateczl.util.swing.FixedTableColumnModel;
import javax.swing.DefaultComboBoxModel;

public class GetSubjects extends JPanel {

	private final transient SubjectController subjectController;
	private JButton createButton;
	private JButton searchButton;
	private JComboBox<String> filterByComboBox;
	private JTextField searchTextField;
	private JPanel cardPanel;
	private JPanel menuPanel;
	private CardLayout cardLayout;
	private JSplitPane splitPane;
	private JScrollPane listPanel;
	private EditCourse editPanel;

	public GetSubjects() throws IOException{
	    var subjectMapper = CsvMapperFactory.create(Subject.class);
	    var subjectContext = CsvContextFactory.create("subjects", subjectMapper, Subject.class);
	    this.subjectController = new SubjectController(subjectContext);

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
	    //listPanel = createListPanel();
	    splitPane.setBottomComponent(listPanel);

	    cardPanel.add(splitPane, "Menu Panel");
	    cardPanel.add(new CreateSubject(subjectController, this), "Create Panel");
	    //cardPanel.add(editPanel, "Edit Panel");

	    createButton.addActionListener(e -> {
	      cardLayout.show(cardPanel, "Create Panel");
	    });
	  }

//	public void refreshListPanel() {
//		splitPane.setBottomComponent(createListPanel());
//		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
//		splitPane.setDividerLocation(75);
//		splitPane.setDividerSize(0);
//		revalidate();
//		repaint();
//	}

//	private JScrollPane createListPanel() {
//		var courses = this.courseController.getAll();
//
//		Object[][] data = new Object[courses.size()][5];
//
//		int i = 0;
//
//		while (i < 5) {
//			var course = courses.dequeue();
//			data[i][0] = course.getId();
//			data[i][1] = course.getCode();
//			data[i][2] = course.getName();
//			data[i][3] = course.getKnowledgeArea();
//			data[i][4] = "Ações";
//			i++;
//		}
//
//		var columnNames = new String[] { "Id", "Código", "Nome", "Curso", "Ações" };
//
//		var tableModel = new CustomTableModel(data, columnNames, new int[] { 4 });
//		var columnModel = new FixedTableColumnModel(0);
//
//		var table = new JTable(tableModel, columnModel);
//		table.createDefaultColumnsFromModel();
//		table.getColumn("Ações").setCellRenderer(new ButtonPanelRenderer());
//
//		var buttonPanelEditor = new ButtonPanelEditor(new JCheckBox());
//
//		table.getColumn("Ações").setCellEditor(buttonPanelEditor);
//
//		table.getColumnModel().getColumn(0).setMinWidth(0);
//		table.getColumnModel().getColumn(0).setMaxWidth(0);
//		table.getColumnModel().getColumn(0).setWidth(0);
//		table.getColumnModel().getColumn(0).setPreferredWidth(0);
//		table.getColumnModel().getColumn(4).setPreferredWidth(160);
//
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//
//		var scrollPane = new JScrollPane(table);
//		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//		return scrollPane;
//	}

	private JPanel createMenuPanel() {
		menuPanel = new JPanel();
		menuPanel.setLayout(null);

		var filterByLabel = new JLabel("Filtrar por");
		filterByLabel.setBounds(10, 5, 200, 25);

		filterByComboBox = new JComboBox<>(new String[] { "Nome", "Código", "Área do Conhecimento" });
		filterByComboBox
				.setModel(new DefaultComboBoxModel(new String[] { "Nome", "Código", "Curso", "Dia da Semana" }));
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
