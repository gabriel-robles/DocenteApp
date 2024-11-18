package fateczl.docenteapp.views.registrations;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fateczl.docenteapp.controllers.RegistrationController;
import fateczl.docenteapp.views.registrations.GetRegistration;
import fateczl.docenteapp.views.dtos.RegistrationDto;

public class CreateRegistration extends JPanel
{
	private final transient RegistrationController registrationController;
	  private final transient GetRegistration getRegistration;

	  public CreateRegistration(RegistrationController registrationController, GetRegistration getRegistration) {
	    this.registrationController = registrationController;
	    this.getRegistration = getRegistration;

	    setLayout(null);

	    var windowTitleRegistrationCreate = new JLabel("Cadastrar Inscrição");
	    windowTitleRegistrationCreate.setBounds(250, 10, 200, 20);
	    add(windowTitleRegistrationCreate);

	    var lblCPFRegistrationCreate = new JLabel("CPF do Candidato:");
	    lblCPFRegistrationCreate.setBounds(10, 40, 200, 20);

	    var tfCPFRegistrationCreate = new JTextField();
	    tfCPFRegistrationCreate.setBounds(170, 40, 400, 20);
	    tfCPFRegistrationCreate.setPreferredSize(new Dimension(400, 28));

	    var lblCodDisciplinaRegistrationCreate = new JLabel("Código da disciplina:");
	    lblCodDisciplinaRegistrationCreate.setBounds(10, 70, 200, 20);

	    var tfCodDisciplinaRegistrationCreate = new JTextField();
	    tfCodDisciplinaRegistrationCreate.setBounds(170, 70, 400, 20);
	    tfCodDisciplinaRegistrationCreate.setPreferredSize(new Dimension(400, 28));

	    var lblCodProcessoRegistrationCreate = new JLabel("Código do Processo:");
	    lblCodProcessoRegistrationCreate.setBounds(10, 100, 200, 20);

	    var tfCodProcessoRegistrationCreate = new JTextField();
	    tfCodProcessoRegistrationCreate.setBounds(170, 100, 400, 20);
	    tfCodProcessoRegistrationCreate.setPreferredSize(new Dimension(400, 28));

	    var backButtonRegistrationCreate = new JButton("voltar");
	    backButtonRegistrationCreate.setBounds(175, 130, 80, 20);
	    
	    var saveButtonRegistrationCreate = new JButton("salvar");
	    saveButtonRegistrationCreate.setBounds(305, 130, 80, 20);

	    add(lblCPFRegistrationCreate);
	    add(tfCPFRegistrationCreate);
	    add(lblCodDisciplinaRegistrationCreate);
	    add(tfCodDisciplinaRegistrationCreate);
	    add(lblCodProcessoRegistrationCreate);
	    add(tfCodProcessoRegistrationCreate);
	    add(backButtonRegistrationCreate);
	    add(saveButtonRegistrationCreate);

	    backButtonRegistrationCreate.addActionListener(e -> {
	      var parent = (JPanel) getParent();
	      var cardLayout = (CardLayout) parent.getLayout();
	      cardLayout.show(parent, "Menu Panel");
	    });

	    saveButtonRegistrationCreate.addActionListener(e -> {
	      var registrationDto = new RegistrationDto();
	      registrationDto.setCPFRegistration(tfCPFRegistrationCreate.getText());
	      registrationDto.setCodigoDisciplinaRegistration(tfCodDisciplinaRegistrationCreate.getText());
	      registrationDto.setCodigoProcessoRegistration(tfCodProcessoRegistrationCreate.getText());

	      this.registrationController.save(registrationDto);

	      tfCPFRegistrationCreate.setText("");
	      tfCodDisciplinaRegistrationCreate.setText("");
	      tfCodProcessoRegistrationCreate.setText("");

	      this.getRegistration.refreshListPanel();
	      
	      var parent = (JPanel) getParent();

	      var cardLayout = (CardLayout) parent.getLayout();
	      cardLayout.show(parent, "Menu Panel");
	    });
	  }
}
