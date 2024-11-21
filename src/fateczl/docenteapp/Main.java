package fateczl.docenteapp;

import fateczl.csvdb.CsvMapperFactory;
import fateczl.csvdb.CsvContextFactory;
import fateczl.docenteapp.controllers.CourseController;
import fateczl.docenteapp.controllers.RegistrationController;
import fateczl.docenteapp.controllers.SubjectController;
import fateczl.docenteapp.controllers.TeacherController;
import fateczl.docenteapp.models.Course;
import fateczl.docenteapp.models.Registration;
import fateczl.docenteapp.models.Subject;
import fateczl.docenteapp.models.Teacher;
import fateczl.docenteapp.views.Window;

import java.awt.EventQueue;

public class Main {
  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      try {
        var registrationMapper = CsvMapperFactory.create(Registration.class);
        var teacherMapper = CsvMapperFactory.create(Teacher.class);
        var subjectMapper = CsvMapperFactory.create(Subject.class);
        var courseMapper = CsvMapperFactory.create(Course.class);

        var registrationContext = CsvContextFactory.create("registrations", registrationMapper, Registration.class);
        var teacherContext = CsvContextFactory.create("teachers", teacherMapper, Teacher.class);
        var subjectContext = CsvContextFactory.create("subjects", subjectMapper, Subject.class);
        var courseContext = CsvContextFactory.create("courses", courseMapper, Course.class);

        var courseController = new CourseController(courseContext, subjectContext, registrationContext);
        var subjectController = new SubjectController(subjectContext, registrationContext);
        var teacherController = new TeacherController(teacherContext);
        var registrationController = new RegistrationController(registrationContext);

        new Window(courseController, subjectController, teacherController, registrationController).setVisible(true);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }
}
