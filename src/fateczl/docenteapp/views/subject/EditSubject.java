package fateczl.docenteapp.views.subject;

import fateczl.csvdb.CsvContextFactory;
import fateczl.csvdb.CsvMapperFactory;
import fateczl.docenteapp.controllers.CourseController;
import fateczl.docenteapp.controllers.SubjectController;
import fateczl.docenteapp.model.Course;
import fateczl.docenteapp.views.dtos.CourseDto;
import fateczl.docenteapp.views.dtos.SubjectDto;
import fateczl.util.Queue;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditSubject extends JPanel {
	private final transient SubjectController subjectController;
	private final transient GetSubjects getSubjects;
	private transient SubjectDto subjectDto = new SubjectDto();
	private Integer subjectId;
	private JTextField codeTextField;
	private JTextField nameTextField;
	private JComboBox coursesTextField;
	private JTextField startTimeField;
	private JComboBox dayTextField;
	private JTextField hourPerDayField;
	private CourseController courseController;

	public EditSubject(SubjectController subjectController, GetSubjects getSubjects) throws IOException {
		this.subjectController = subjectController;
		this.getSubjects = getSubjects;
		
		var courseMapper = CsvMapperFactory.create(Course.class);
	    var courseContext = CsvContextFactory.create("courses", courseMapper, Course.class);
	    this.courseController = new CourseController(courseContext);

		setLayout(null);

		var windowTitle = new JLabel("Editar Disciplina");
		windowTitle.setBounds(250, 10, 200, 20);
		add(windowTitle);

		var codeLabel = new JLabel("Código da Disciplina:");
		codeLabel.setBounds(52, 39, 118, 20);

		codeTextField = new JTextField();
		codeTextField.setBounds(170, 40, 400, 20);
		codeTextField.setPreferredSize(new Dimension(400, 28));

		var nameLabel = new JLabel("Nome da disciplina:");
		nameLabel.setBounds(66, 70, 105, 20);

		nameTextField = new JTextField();
		nameTextField.setBounds(170, 70, 400, 20);
		nameTextField.setPreferredSize(new Dimension(400, 28));

		var courseLabel = new JLabel("Curso:");
		courseLabel.setBounds(124, 99, 47, 20);

		JLabel hourPerDayLabel = new JLabel("Horas por dia");
		hourPerDayLabel.setBounds(66, 142, 89, 13);

		JLabel dayLabel = new JLabel("Dia da semana");
		dayLabel.setBounds(81, 176, 89, 13);

		JLabel startTimeLabel = new JLabel("Horário inicial");
		startTimeLabel.setBounds(41, 206, 95, 13);

		startTimeField = new JTextField();
		startTimeField.setColumns(10);
		startTimeField.setBounds(170, 203, 47, 19);

		hourPerDayField = new JTextField();
		hourPerDayField.setBounds(170, 139, 32, 19);
		hourPerDayField.setColumns(10);

		dayTextField = new JComboBox();
		dayTextField.setModel(new DefaultComboBoxModel(new String[] { "Segunda-Feira", "Terça-Feira", "Quarta-Feira", "Quinta-Feira", "Sexta-Feira", "Sabádo", "Domingo" }));
		dayTextField.setBounds(170, 172, 80, 21);

		coursesTextField = new JComboBox();
		coursesTextField.setModel(new DefaultComboBoxModel(subjectController.getCourses()));
		coursesTextField.setBounds(170, 100, 80, 21);

		var backButton = new JButton("voltar");
		backButton.setBounds(189, 245, 80, 20);

		var saveButton = new JButton("salvar");
		saveButton.setBounds(321, 245, 80, 20);

		add(codeLabel);
		add(codeTextField);
		add(nameLabel);
		add(nameTextField);
		add(courseLabel);
		add(coursesTextField);
		add(hourPerDayLabel);
		add(hourPerDayField);
		add(dayLabel);
		add(dayTextField);
		add(startTimeLabel);
		add(startTimeField);
		add(backButton);
		add(saveButton);

		backButton.addActionListener(e -> {
			var parent = (JPanel) getParent();
			var cardLayout = (CardLayout) parent.getLayout();
			cardLayout.show(parent, "Menu Panel");
		});

		saveButton.addActionListener(e -> {
			subjectDto.setCode(codeTextField.getText());
			subjectDto.setName(nameTextField.getText());
			subjectDto.setCourseId(subjectController.searchCourse(coursesTextField.getSelectedItem().toString()));
			subjectDto.setDay(dayTextField.getSelectedItem().toString());
			subjectDto.setHoursPerDay(hourPerDayField.getText());
			subjectDto.setStartTime(startTimeField.getText());

			this.subjectController.edit(subjectId.toString(), subjectDto);

			nameTextField.setText("");
			codeTextField.setText("");

			this.getSubjects.refreshListPanel();

			var parent = (JPanel) getParent();

			var cardLayout = (CardLayout) parent.getLayout();
			cardLayout.show(parent, "Menu Panel");
		});
	}

	public void loadData() {
		System.out.println("");
		codeTextField.setText(subjectDto.getCode());
		nameTextField.setText(subjectDto.getName());
		coursesTextField.setSelectedItem(subjectController.getCourseName(subjectId));
		dayTextField.setSelectedItem(subjectDto.getDay());
		hourPerDayField.setText(subjectDto.getHoursPerDay());
		startTimeField.setText(subjectDto.getStartTime());
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public void setSubjectDto(SubjectDto subjectDto) {
		this.subjectDto = subjectDto;
	}
}
