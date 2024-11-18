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

public class EditRegistration extends JPanel {
	private final transient RegistrationController registrationController;
	  private final transient GetRegistration getRegistration;
	  private transient RegistrationDto registrationDto = new RegistrationDto();
	  private Integer registrationId;
	  private JTextField tfCPFRegistrationEdit;
	  private JTextField tfCodDisciplinaRegistrationEdit;
	  private JTextField tfCodProcessoRegistrationEdit;

	  public EditRegistration(RegistrationController registrationController, GetRegistration getRegistration) {
	    this.registrationController = registrationController;
	    this.getRegistration = getRegistration;

	    setLayout(null);

	    var windowTitleRegistrationEdit = new JLabel("Cadastrar Inscrição");
	    windowTitleRegistrationEdit.setBounds(250, 10, 200, 20);
	    add(windowTitleRegistrationEdit);

	    var lblCPFRegistrationEdit = new JLabel("CPF do Candidato:");
	    lblCPFRegistrationEdit.setBounds(10, 40, 200, 20);

	    tfCPFRegistrationEdit = new JTextField();
	    tfCPFRegistrationEdit.setBounds(170, 40, 400, 20);
	    tfCPFRegistrationEdit.setPreferredSize(new Dimension(400, 28));

	    var lblCodDisciplinaRegistrationEdit = new JLabel("Código da disciplina:");
	    lblCodDisciplinaRegistrationEdit.setBounds(10, 70, 200, 20);

	    tfCodDisciplinaRegistrationEdit = new JTextField();
	    tfCodDisciplinaRegistrationEdit.setBounds(170, 70, 400, 20);
	    tfCodDisciplinaRegistrationEdit.setPreferredSize(new Dimension(400, 28));

	    var lblCodProcessoRegistrationEdit = new JLabel("Código do Processo:");
	    lblCodProcessoRegistrationEdit.setBounds(10, 100, 200, 20);

	    tfCodProcessoRegistrationEdit = new JTextField();
	    tfCodProcessoRegistrationEdit.setBounds(170, 100, 400, 20);
	    tfCodProcessoRegistrationEdit.setPreferredSize(new Dimension(400, 28));

	    var backButtonRegistrationEdit = new JButton("voltar");
	    backButtonRegistrationEdit.setBounds(175, 130, 80, 20);
	    
	    var saveButtonRegistrationEdit = new JButton("salvar");
	    saveButtonRegistrationEdit.setBounds(305, 130, 80, 20);

	    add(lblCPFRegistrationEdit);
	    add(tfCPFRegistrationEdit);
	    add(lblCodDisciplinaRegistrationEdit);
	    add(tfCodDisciplinaRegistrationEdit);
	    add(lblCodProcessoRegistrationEdit);
	    add(tfCodProcessoRegistrationEdit);
	    add(backButtonRegistrationEdit);
	    add(saveButtonRegistrationEdit);
	    
	    backButtonRegistrationEdit.addActionListener(e -> {
	    	var parent = (JPanel) getParent();
		    var cardLayout = (CardLayout) parent.getLayout();
		    cardLayout.show(parent, "Menu Panel");
		    });

	    saveButtonRegistrationEdit.addActionListener(e -> {
		    var registrationDto = new RegistrationDto();
		    registrationDto.setCPFRegistration(tfCPFRegistrationEdit.getText());
		    registrationDto.setCodigoDisciplinaRegistration(tfCodDisciplinaRegistrationEdit.getText());
		    registrationDto.setCodigoProcessoRegistration(tfCodProcessoRegistrationEdit.getText());

		    this.registrationController.edit(registrationId.toString(), registrationDto);

		    tfCPFRegistrationEdit.setText("");
		    tfCodDisciplinaRegistrationEdit.setText("");
		    tfCodProcessoRegistrationEdit.setText("");

		    this.getRegistration.refreshListPanel();
		      
		    var parent = (JPanel) getParent();

		    var cardLayout = (CardLayout) parent.getLayout();
		    cardLayout.show(parent, "Menu Panel");
		  });
	  }
	  public void loadData() {
		  tfCPFRegistrationEdit.setText(registrationDto.getCPFRegistration());
		  tfCodDisciplinaRegistrationEdit.setText(registrationDto.getCodigoDisciplinaRegistration());
		  tfCodProcessoRegistrationEdit.setText(registrationDto.getCodigoProcessoRegistration());
	  }

	  public void setRegistrationId(Integer registrationId) {
	    this.registrationId = registrationId;
	  }

	  public void setRegistrationDto(RegistrationDto registrationDto) {
	    this.registrationDto = registrationDto;
	  }
	  
}
