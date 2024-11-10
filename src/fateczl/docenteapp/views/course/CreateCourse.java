package fateczl.docenteapp.views.course;

import fateczl.docenteapp.controllers.CourseController;
import fateczl.docenteapp.views.dtos.CourseDto;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateCourse extends JPanel {
  private final transient CourseController courseController;
  private final transient GetCourses getCourses;

  public CreateCourse(CourseController courseController, GetCourses getCourses) {
    this.courseController = courseController;
    this.getCourses = getCourses;

    setLayout(null);

    var windowTitle = new JLabel("Cadastrar Curso");
    windowTitle.setBounds(250, 10, 200, 20);
    add(windowTitle);

    var codeLabel = new JLabel("Código do Curso:");
    codeLabel.setBounds(10, 40, 200, 20);

    var codeTextField = new JTextField();
    codeTextField.setBounds(170, 40, 400, 20);
    codeTextField.setPreferredSize(new Dimension(400, 28));

    var nameLabel = new JLabel("Nome do Curso:");
    nameLabel.setBounds(10, 70, 200, 20);

    var nameTextField = new JTextField();
    nameTextField.setBounds(170, 70, 400, 20);
    nameTextField.setPreferredSize(new Dimension(400, 28));

    var knowledgeAreaLabel = new JLabel("Área do Conhecimento:");
    knowledgeAreaLabel.setBounds(10, 100, 200, 20);

    var knowledgeAreaTextField = new JTextField();
    knowledgeAreaTextField.setBounds(170, 100, 400, 20);
    knowledgeAreaTextField.setPreferredSize(new Dimension(400, 28));

    var backButton = new JButton("voltar");
    backButton.setBounds(175, 130, 80, 20);
    
    var saveButton = new JButton("salvar");
    saveButton.setBounds(305, 130, 80, 20);

    add(codeLabel);
    add(codeTextField);
    add(nameLabel);
    add(nameTextField);
    add(knowledgeAreaLabel);
    add(knowledgeAreaTextField);
    add(backButton);
    add(saveButton);

    backButton.addActionListener(e -> {
      var parent = (JPanel) getParent();
      var cardLayout = (CardLayout) parent.getLayout();
      cardLayout.show(parent, "Menu Panel");
    });

    saveButton.addActionListener(e -> {
      var courseDto = new CourseDto();
      courseDto.setCode(codeTextField.getText());
      courseDto.setName(nameTextField.getText());
      courseDto.setKnowledgeArea(knowledgeAreaTextField.getText());

      this.courseController.save(courseDto);

      nameTextField.setText("");
      codeTextField.setText("");
      knowledgeAreaTextField.setText("");

      this.getCourses.refreshListPanel();
      
      var parent = (JPanel) getParent();

      var cardLayout = (CardLayout) parent.getLayout();
      cardLayout.show(parent, "Menu Panel");
    });
  }
}
