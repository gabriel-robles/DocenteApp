package fateczl.docenteapp.views.teacher;

import javax.swing.JPanel;
import fateczl.docenteapp.controllers.TeacherController;
import fateczl.docenteapp.controllers.dtos.TeacherDto;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;

import java.awt.CardLayout;
import javax.swing.JButton;

public class CreateTeacher extends JPanel {
	  private final transient TeacherController teacherController;
	  private final transient GetTeacher getTeacher;
	  
	  private JTextField tfNomeTeacherCreate;
	  private JTextField tfTeacherCPFCreate;
	  private JTextField tfAreaTeacherCreate;
	  private JTextField tfPontuacaoTeacherCreate;

	  public CreateTeacher(TeacherController teacherController, GetTeacher getTeacher) {
	    this.teacherController = teacherController;
	    this.getTeacher = getTeacher;
	    setLayout(null);
	    
	    JLabel lblwindowTeacherCreate = new JLabel("Cadastrar Professor");
	    lblwindowTeacherCreate.setFont(new Font("Tahoma", Font.PLAIN, 11));
	    lblwindowTeacherCreate.setBounds(250, 10, 200, 20);
	    add(lblwindowTeacherCreate);
	    
	    JLabel lblNomeTeacherCreate = new JLabel("Nome:");
	    lblNomeTeacherCreate.setBounds(10, 40, 200, 20);
	    add(lblNomeTeacherCreate);
	    
	    JLabel lblTeacherCPFCreate = new JLabel("CPF:");
	    lblTeacherCPFCreate.setBounds(10, 70, 200, 20);
	    add(lblTeacherCPFCreate);
	    
	    JLabel lblAreaTeacherCreate = new JLabel("Area de interesse:");
	    lblAreaTeacherCreate.setBounds(10, 100, 106, 20);
	    add(lblAreaTeacherCreate);
	    
	    JLabel lblPontuacaoTeacherCreate = new JLabel("Pontuação:");
	    lblPontuacaoTeacherCreate.setBounds(10, 130, 99, 25);
	    add(lblPontuacaoTeacherCreate);
	    
	    tfNomeTeacherCreate = new JTextField();
	    tfNomeTeacherCreate.setBounds(114, 40, 314, 19);
	    add(tfNomeTeacherCreate);
	    tfNomeTeacherCreate.setColumns(10);
	    
			try {
				MaskFormatter cpfFormatter = new MaskFormatter("###.###.###-##");
				cpfFormatter.setPlaceholderCharacter('0');
				tfTeacherCPFCreate = new JFormattedTextField(cpfFormatter);
	    	tfTeacherCPFCreate.setBounds(114, 70, 314, 19);
			} catch (Exception e) {
				e.printStackTrace();
			}

	    add(tfTeacherCPFCreate);
	    tfTeacherCPFCreate.setColumns(10);
	    
	    tfAreaTeacherCreate = new JTextField();
	    tfAreaTeacherCreate.setColumns(10);
	    tfAreaTeacherCreate.setBounds(114, 100, 314, 19);
	    add(tfAreaTeacherCreate);
	    
	    tfPontuacaoTeacherCreate = new JTextField();
	    tfPontuacaoTeacherCreate.setBounds(114, 130, 314, 19);
	    tfPontuacaoTeacherCreate.setColumns(10);
	    add(tfPontuacaoTeacherCreate); 
	    
	    JButton btnSalvaTeacherCreate = new JButton("salvar");
	    btnSalvaTeacherCreate.setBounds(305, 200, 80, 20);
	    add(btnSalvaTeacherCreate);
	    
	    btnSalvaTeacherCreate.addActionListener(e -> {
	        var teacherDto = new TeacherDto();
	        teacherDto.setNomeTeacher(tfNomeTeacherCreate.getText());
	        teacherDto.setCPFTeacher(tfTeacherCPFCreate.getText());
	        teacherDto.setAreaTeacher(tfAreaTeacherCreate.getText());
	        teacherDto.setPontuacaoTeacher(Integer.parseInt(tfPontuacaoTeacherCreate.getText()));
	        
	        this.teacherController.save(teacherDto);

	        tfNomeTeacherCreate.setText("");
	        tfTeacherCPFCreate.setText("");
	        tfAreaTeacherCreate.setText("");
	        tfPontuacaoTeacherCreate.setText("");
	        
	        this.getTeacher.refreshListPanel();
	        
	        var parent = (JPanel) getParent();

	        var cardLayout = (CardLayout) parent.getLayout();
	        cardLayout.show(parent, "Menu Panel");
	    });
	    	    
	    JButton btnVoltaTeacherCreate =  new JButton("voltar");
	    btnVoltaTeacherCreate.setBounds(175, 200, 80, 20);
	    add(btnVoltaTeacherCreate);
	    
	    btnVoltaTeacherCreate.addActionListener(e -> {
	        var parent = (JPanel) getParent();
	        var cardLayout = (CardLayout) parent.getLayout();
	        cardLayout.show(parent, "Menu Panel");
	      });
	  }
}
