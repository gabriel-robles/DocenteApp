package fateczl.docenteapp.views.teacher;

import java.awt.CardLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;

import fateczl.docenteapp.controllers.TeacherController;
import fateczl.docenteapp.controllers.dtos.TeacherDto;



public class EditTeacher extends JPanel
{
	private JTextField tfNomeTeacherEdit;
	private JTextField tfTeacherCPFEdit;
	private JTextField tfAreaTeacherEdit;
	private JTextField tfPontuacaoTeacherEdit;
	private final transient TeacherController teacherController;
	private final transient GetTeacher getTeacher;
	private transient TeacherDto teacherDto = new TeacherDto();
	private Integer teacherId;
	
	public EditTeacher(TeacherController teacherController, GetTeacher getTeacher) {
	    this.teacherController = teacherController;
	    this.getTeacher = getTeacher;
		setLayout(null);
		
		 	JLabel lblwindowTeacherEdit = new JLabel("Editar Professor");
		 	lblwindowTeacherEdit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 	lblwindowTeacherEdit.setBounds(250, 10, 200, 20);
		    add(lblwindowTeacherEdit);
		    
		    JLabel lblNomeTeacherEdit = new JLabel("Nome:");
		    lblNomeTeacherEdit.setBounds(10, 40, 106, 20);
		    add(lblNomeTeacherEdit);
		    
		    JLabel lblCPFTeacherEdit = new JLabel("CPF:");
		    lblCPFTeacherEdit.setBounds(10, 70, 200, 20);
		    add(lblCPFTeacherEdit);
		    
		    JLabel lblAreaTeacherEdit = new JLabel("Area Atuante:");
		    lblAreaTeacherEdit.setBounds(10, 100, 106, 20);
		    add(lblAreaTeacherEdit);
		    
		    JLabel lblPontuacaoTeacherEdit = new JLabel("Pontuação:");
		    lblPontuacaoTeacherEdit.setBounds(10, 130, 99, 25);
		    add(lblPontuacaoTeacherEdit);
		    		    
		    tfNomeTeacherEdit = new JTextField();
		    tfNomeTeacherEdit.setBounds(114, 40, 314, 19);
		    add(tfNomeTeacherEdit);
		    tfNomeTeacherEdit.setColumns(10);

				try {
					MaskFormatter cpfFormatter = new MaskFormatter("###.###.###-##");
					cpfFormatter.setPlaceholderCharacter('0');
					tfTeacherCPFEdit = new JFormattedTextField(cpfFormatter);
					tfTeacherCPFEdit.setBounds(114, 70, 314, 19);
				} catch (Exception e) {
					e.printStackTrace();
				}

		    tfTeacherCPFEdit.setColumns(10);
		    add(tfTeacherCPFEdit);
		    
		    tfAreaTeacherEdit = new JTextField();
		    tfAreaTeacherEdit.setColumns(10);
		    tfAreaTeacherEdit.setBounds(114, 100, 314, 19);
		    add(tfAreaTeacherEdit);
		    
		    tfPontuacaoTeacherEdit = new JTextField();
		    tfPontuacaoTeacherEdit.setBounds(114, 130, 314, 19);
		    add(tfPontuacaoTeacherEdit);
		    tfPontuacaoTeacherEdit.setColumns(10);
		    
		    JButton btnSalvaTeacherEdit = new JButton("Salva");
		    btnSalvaTeacherEdit.setBounds(305, 200, 80, 20);
		    add(btnSalvaTeacherEdit);
		    
		    btnSalvaTeacherEdit.addActionListener(e -> {
		        var teacherDto = new TeacherDto();
		        teacherDto.setNomeTeacher(tfNomeTeacherEdit.getText());
		        teacherDto.setCPFTeacher(tfTeacherCPFEdit.getText());
		        teacherDto.setAreaTeacher(tfAreaTeacherEdit.getText());
		        teacherDto.setPontuacaoTeacher(Integer.parseInt(tfPontuacaoTeacherEdit.getText()));
		        
		        this.teacherController.edit(teacherId.toString(), teacherDto);

		        tfNomeTeacherEdit.setText("");
		        tfTeacherCPFEdit.setText("");
		        tfAreaTeacherEdit.setText("");
		        tfPontuacaoTeacherEdit.setText("");
		        
		        this.getTeacher.refreshListPanel();
		        
		        var parent = (JPanel) getParent();

		        var cardLayout = (CardLayout) parent.getLayout();
		        cardLayout.show(parent, "Menu Panel");
		    });
		    
		    JButton btnVoltaTeacherEdit =  new JButton("Volta");
		    btnVoltaTeacherEdit.setBounds(175, 200, 80, 20);
		    add(btnVoltaTeacherEdit);
		    
		    btnVoltaTeacherEdit.addActionListener(e -> {
		        var parent = (JPanel) getParent();
		        var cardLayout = (CardLayout) parent.getLayout();
		        cardLayout.show(parent, "Menu Panel");
		      });
	}	
	
	public void loadData() {
		tfNomeTeacherEdit.setText(teacherDto.getNomeTeacher());
		tfTeacherCPFEdit.setText(teacherDto.getCPFTeacher());
		tfAreaTeacherEdit.setText(teacherDto.getAreaTeacher());
		tfPontuacaoTeacherEdit.setText(Integer.toString(teacherDto.getPontuacaoTeacher()));
	  }

	  public void setTeacherId(Integer teacherId) {
	    this.teacherId = teacherId;
	  }

	  public void setTeacherDto(TeacherDto teacherDto) {
	    this.teacherDto = teacherDto;
	  }
}
