package fateczl.docenteapp.views.subject;

import fateczl.docenteapp.controllers.CourseController;
import fateczl.docenteapp.controllers.SubjectController;
import fateczl.docenteapp.controllers.dtos.SubjectDto;

import java.awt.CardLayout;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;

public class EditSubject extends JPanel {
	private final transient SubjectController subjectController;
	private final transient CourseController courseController;
	private final transient GetSubjects getSubjects;
	private transient SubjectDto subjectDto = new SubjectDto();
	private Integer subjectId;
	private JTextField codeTextField;
	private JTextField nameTextField;
	private JComboBox<String> coursesTextField;
	private JTextField startTimeField;
	private JComboBox<String> dayTextField;
	private JTextField hourPerDayField;

	public EditSubject(SubjectController subjectController, CourseController courseController, GetSubjects getSubjects) throws IOException {
		this.subjectController = subjectController;
		this.courseController = courseController;
		this.getSubjects = getSubjects;

		setLayout(null);

		var windowTitle = new JLabel("Editar Disciplina");
		windowTitle.setBounds(250, 10, 200, 20);
		add(windowTitle);

		var codeLabel = new JLabel("Código da Disciplina:");
		codeLabel.setBounds(30, 40, 120, 20);

		codeTextField = new JTextField();
		codeTextField.setBounds(170, 40, 400, 20);
		codeTextField.setEditable(false);

		var nameLabel = new JLabel("Nome da disciplina:");
		nameLabel.setBounds(30, 70, 120, 20);

		nameTextField = new JTextField();
		nameTextField.setBounds(170, 70, 400, 20);

		var courseLabel = new JLabel("Curso:");
		courseLabel.setBounds(30, 100, 47, 20);

		coursesTextField = new JComboBox<String>(getSubjects.getCourses());
		coursesTextField.setBounds(170, 100, 400, 20);
		coursesTextField.setEnabled(false);

		JLabel dayLabel = new JLabel("Dia da semana");
		dayLabel.setBounds(30, 130, 120, 20);

		dayTextField = new JComboBox<String>(
			new String[] { "Segunda-Feira", "Terça-Feira", "Quarta-Feira", "Quinta-Feira", "Sexta-Feira", "Sabádo", "Domingo" }
		);
		dayTextField.setBounds(170, 130, 120, 20);

		JLabel startTimeLabel = new JLabel("Horário inicial");
		startTimeLabel.setBounds(30, 160, 120, 20);

		try {
			MaskFormatter hourFormatter = new MaskFormatter("##:##");
			hourFormatter.setPlaceholderCharacter('0');
			startTimeField = new JFormattedTextField(hourFormatter);
			startTimeField.setBounds(170, 160, 47, 20);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JLabel hourPerDayLabel = new JLabel("Horas por dia");
		hourPerDayLabel.setBounds(30, 190, 120, 20);

		try {
			MaskFormatter hourFormatter = new MaskFormatter("##:##");
			hourFormatter.setPlaceholderCharacter('0');
			hourPerDayField = new JFormattedTextField(hourFormatter);
			hourPerDayField.setBounds(170, 190, 47, 20);
		} catch (Exception e) {
			e.printStackTrace();
		}

		var backButton = new JButton("voltar");
		backButton.setBounds(189, 220, 80, 20);

		var saveButton = new JButton("salvar");
		saveButton.setBounds(321, 220, 80, 20);

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
			subjectDto.setCourseCode(this.courseController.find(c -> c.getName().equals(coursesTextField.getSelectedItem().toString())).getCode());
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
		codeTextField.setText(subjectDto.getCode());
		nameTextField.setText(subjectDto.getName());
		if (subjectDto.getCourseCode() != null) {
			coursesTextField.setEnabled(true);
			coursesTextField.setModel(new DefaultComboBoxModel<String>(getSubjects.getCourses()));
			coursesTextField.setSelectedItem(courseController.find(c -> c.getCode().equals(subjectDto.getCourseCode())).getName());
			coursesTextField.setEnabled(false);
		}
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
