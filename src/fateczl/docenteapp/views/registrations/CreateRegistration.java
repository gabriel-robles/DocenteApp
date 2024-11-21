package fateczl.docenteapp.views.registrations;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fateczl.docenteapp.controllers.RegistrationController;
import fateczl.docenteapp.controllers.SubjectController;
import fateczl.docenteapp.controllers.TeacherController;
import fateczl.docenteapp.controllers.dtos.RegistrationDto;

public class CreateRegistration extends JPanel {
	private final transient RegistrationController registrationController;
	private final transient GetRegistration getRegistration;
	private final transient SubjectController subjectController;
	private final transient TeacherController teacherController;
	private JComboBox<String> candidatesComboBox;
	private JComboBox<String> subjectComboBox;
	private JTextField tfProcessCode;

	public CreateRegistration(RegistrationController registrationController, GetRegistration getRegistration,
		SubjectController subjectController, TeacherController teacherController) {
		
	  this.registrationController = registrationController;
	  this.getRegistration = getRegistration;
		this.subjectController = subjectController;
		this.teacherController = teacherController;

	  setLayout(null);

	  var windowTitle = new JLabel("Fazer Inscrição");
	  windowTitle.setBounds(250, 10, 200, 20);
	  add(windowTitle);

		var lblProcessCode = new JLabel("Código do Processo:");
	  lblProcessCode.setBounds(10, 40, 200, 20);

	  tfProcessCode = new JTextField();
		tfProcessCode.setEditable(false);
	  tfProcessCode.setBounds(170, 40, 400, 20);
	  tfProcessCode.setPreferredSize(new Dimension(400, 28));

	  var lblDisciplina = new JLabel("Código da disciplina:");
	  lblDisciplina.setBounds(10, 70, 200, 20);

	  subjectComboBox = new JComboBox<String>();
	  subjectComboBox.setBounds(170, 70, 400, 20);
	  subjectComboBox.setPreferredSize(new Dimension(400, 28));

		var lblCpfCandidato = new JLabel("CPF do Candidato:");
	  lblCpfCandidato.setBounds(10, 100, 200, 20);

	  candidatesComboBox = new JComboBox<String>();
	  candidatesComboBox.setBounds(170, 100, 400, 20);
	  candidatesComboBox.setPreferredSize(new Dimension(400, 28));

	  var backButtonRegistrationCreate = new JButton("voltar");
	  backButtonRegistrationCreate.setBounds(175, 130, 80, 20);
	    
	  var saveButtonRegistrationCreate = new JButton("salvar");
	  saveButtonRegistrationCreate.setBounds(305, 130, 80, 20);

	  add(lblCpfCandidato);
	  add(candidatesComboBox);
	  add(lblDisciplina);
	  add(subjectComboBox);
	  add(lblProcessCode);
	  add(tfProcessCode);
	  add(backButtonRegistrationCreate);
	  add(saveButtonRegistrationCreate);

	  backButtonRegistrationCreate.addActionListener(e -> {
	    var parent = (JPanel) getParent();
	    var cardLayout = (CardLayout) parent.getLayout();
	    cardLayout.show(parent, "Menu Panel");
	  });

	  saveButtonRegistrationCreate.addActionListener(e -> {
	    var registrationDto = new RegistrationDto();
	    registrationDto.setCpf(candidatesComboBox.getSelectedItem().toString());
	    registrationDto.setSubjectCode(subjectComboBox.getSelectedItem().toString());
	    registrationDto.setProcessCode(tfProcessCode.getText());

	    this.registrationController.save(registrationDto);

			tfProcessCode.setText("");

	    this.getRegistration.refreshListPanel();
	      
	    var parent = (JPanel) getParent();

	    var cardLayout = (CardLayout) parent.getLayout();
	    cardLayout.show(parent, "Menu Panel");
	  });
	}

	public void loadData() {
		var teachers = this.teacherController.getAll();
		var subject = "";
		if (tfProcessCode.getText() != null && !tfProcessCode.getText().isEmpty()) {
			subject = this.subjectController.find(s -> s.getProcess().equals(tfProcessCode.getText())).getCode();
		}

		var teachersCpf = new String[teachers.size()];

		int i = 0;

		while (!teachers.isEmpty()) {
			var teacher = teachers.dequeue();
			teachersCpf[i] = teacher.getCpf();
			i++;
		}

		candidatesComboBox.setModel(new DefaultComboBoxModel<String>(teachersCpf));
		subjectComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { subject }));
		subjectComboBox.setEnabled(false);
	}

	public void setProcessCode(String processCode) {
		tfProcessCode.setText(processCode);
	}
}
