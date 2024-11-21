package fateczl.docenteapp.views;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fateczl.docenteapp.controllers.CourseController;
import fateczl.docenteapp.controllers.RegistrationController;
import fateczl.docenteapp.controllers.SubjectController;
import fateczl.docenteapp.controllers.TeacherController;
import fateczl.docenteapp.views.course.GetCourses;
import fateczl.docenteapp.views.subject.GetSubjects;
import fateczl.docenteapp.views.registrations.GetRegistration;
import fateczl.docenteapp.views.teacher.GetTeacher;

public class Window extends JFrame {
  public Window(CourseController courseController, SubjectController subjectController,
    TeacherController teacherController, RegistrationController registrationController) throws IOException {

    setTitle("DocenteApp");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setBounds(100, 100, 720, 480);

    var contentPane = new JPanel();
    contentPane.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

    setContentPane(contentPane);

    var tabbedPane = new JTabbedPane(SwingConstants.TOP);
    tabbedPane.setBounds(10, 10, 700, 425);
    contentPane.add(tabbedPane);

    var coursePanel = new GetCourses(courseController);

    var subjectPanel = new GetSubjects(subjectController, courseController);
    
    var teacherPanel = new GetTeacher(teacherController);

    var registrationPanel = new GetRegistration(courseController, registrationController, subjectController, teacherController);

    tabbedPane.addTab("Cursos", null, coursePanel, "Cursos");

    tabbedPane.addTab("Disciplinas", null, subjectPanel, "Disciplinas");
    
    tabbedPane.addTab("Professores", null, teacherPanel, "Professores");
    
    tabbedPane.addTab("Inscrições", null, registrationPanel, "Inscrições");

    tabbedPane.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        var index = tabbedPane.getSelectedIndex();
        var title = tabbedPane.getTitleAt(index);

        if (title.equals("Cursos")) {
          coursePanel.refreshListPanel();
        } else if (title.equals("Disciplinas")) {
          subjectPanel.refreshListPanel();
        } else if (title.equals("Inscrições")) {
          registrationPanel.refreshListPanel();
        }
      }
    });
    
    pack();
  }
}
