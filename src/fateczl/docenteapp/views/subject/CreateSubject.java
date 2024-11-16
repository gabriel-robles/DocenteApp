package fateczl.docenteapp.views.subject;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fateczl.docenteapp.controllers.CourseController;
import fateczl.docenteapp.controllers.SubjectController;
import fateczl.docenteapp.views.course.GetCourses;
import fateczl.docenteapp.views.dtos.SubjectDto;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class CreateSubject extends JPanel {
	private final transient SubjectController subjectController;
	private final transient GetSubjects getSubjects;

	public CreateSubject(SubjectController subjectController, GetSubjects getSubjects) {
		this.subjectController = subjectController;
		this.getSubjects = getSubjects;

		setLayout(null);

		var windowTitle = new JLabel("Cadastrar Disciplina");
		windowTitle.setBounds(250, 10, 200, 20);
		add(windowTitle);

		var codeLabel = new JLabel("Código da Disciplina:");
		codeLabel.setBounds(52, 39, 118, 20);

		var codeTextField = new JTextField();
		codeTextField.setBounds(170, 40, 400, 20);
		codeTextField.setPreferredSize(new Dimension(400, 28));

		var nameLabel = new JLabel("Nome da disciplina:");
		nameLabel.setBounds(66, 70, 105, 20);

		var nameTextField = new JTextField();
		nameTextField.setBounds(170, 70, 400, 20);
		nameTextField.setPreferredSize(new Dimension(400, 28));

		var courseLabel = new JLabel("Curso:");
		courseLabel.setBounds(124, 99, 47, 20);

		JLabel hourPerDayLabel = new JLabel("Horas por dia");
		hourPerDayLabel.setBounds(66, 142, 89, 13);

		JLabel dayLabel = new JLabel("Dia da semana");
		dayLabel.setBounds(81, 176, 89, 13);

		var courseTextField = new JTextField();
		courseTextField.setBounds(170, 100, 400, 20);
		courseTextField.setPreferredSize(new Dimension(400, 28));

		var hourPerDayField = new JTextField();
		hourPerDayField.setBounds(170, 139, 32, 19);
		hourPerDayField.setColumns(10);

		var dayTextField = new JComboBox();
		dayTextField.setModel(new DefaultComboBoxModel(new String[] { "Segunda", "Terça", "Quarta" }));
		dayTextField.setBounds(170, 172, 80, 21);

		var backButton = new JButton("voltar");
		backButton.setBounds(189, 245, 80, 20);

		var saveButton = new JButton("salvar");
		saveButton.setBounds(321, 245, 80, 20);

		add(codeLabel);
		add(codeTextField);
		add(nameLabel);
		add(nameTextField);
		add(courseLabel);
		add(hourPerDayLabel);
		add(hourPerDayField);
		add(courseTextField);
		add(dayLabel);
		add(dayTextField);
		add(backButton);
		add(saveButton);

		backButton.addActionListener(e -> {
			var parent = (JPanel) getParent();
			var cardLayout = (CardLayout) parent.getLayout();
			cardLayout.show(parent, "Menu Panel");
		});

		saveButton.addActionListener(e -> {
			var subjectDto = new SubjectDto();
			subjectDto.setCode(codeTextField.getText());
			subjectDto.setName(nameTextField.getText());
			subjectDto.setCourseId(1);
			subjectDto.setDay(dayTextField.getSelectedItem().toString());
			subjectDto.setHoursPerDay(hourPerDayField.getText());
			this.subjectController.save(subjectDto);

			nameTextField.setText("");
			codeTextField.setText("");

			// this.getSubjects.refreshListPanel();

			var parent = (JPanel) getParent();

			var cardLayout = (CardLayout) parent.getLayout();
			cardLayout.show(parent, "Menu Panel");
		});
	}
}
