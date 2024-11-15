package fateczl.docenteapp.views;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import fateczl.docenteapp.views.course.GetCourses;

public class Window extends JFrame {
  public Window() throws IOException {
    setTitle("DocenteApp");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setBounds(100, 100, 720, 480);

    var contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(2, 2, 2, 2));

    setContentPane(contentPane);

    var tabbedPane = new JTabbedPane(SwingConstants.TOP);
    tabbedPane.setBounds(10, 10, 700, 425);
    contentPane.add(tabbedPane);

    var coursePanel = new GetCourses();
    tabbedPane.addTab("Cursos", null, coursePanel, "Cursos");

    pack();
  }
}
