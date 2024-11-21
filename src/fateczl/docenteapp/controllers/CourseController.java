package fateczl.docenteapp.controllers;

import fateczl.csvdb.CsvContext;
import fateczl.docenteapp.controllers.dtos.CourseDto;
import fateczl.docenteapp.controllers.dtos.ProcessDto;
import fateczl.docenteapp.models.Course;
import fateczl.docenteapp.models.Registration;
import fateczl.docenteapp.models.Subject;
import fateczl.util.Queue;
import fateczl.util.HashTable;

import java.util.function.Predicate;

public class CourseController {
  private final CsvContext<Course> courseContext;
  private final CsvContext<Subject> subjectContext;
  private final CsvContext<Registration> registrationContext;

  public CourseController(CsvContext<Course> courseContext, CsvContext<Subject> subjectContext, CsvContext<Registration> registrationContext) {
    this.courseContext = courseContext;
    this.subjectContext = subjectContext;
    this.registrationContext = registrationContext;
  }

  public Queue<Course> getAll() {
    try {
      return courseContext.readAll();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public HashTable<String, ProcessDto> getProcesses() {
    try {
      var processes = new HashTable<String, ProcessDto>();
      var subjects = subjectContext.readAll();

      while (!subjects.isEmpty()) {
        var subject = subjects.dequeue();
			  var course = courseContext.find(c -> c.getCode().equals(subject.getCourseCode()));
			  var courseName = course.getName();
			  var courseKnowledgeArea = course.getKnowledgeArea();

        var process = new ProcessDto();
        process.setCourseName(courseName);
        process.setProcess(subject.getProcess());
        process.setSubjectName(subject.getName());
        process.setKnowledgeArea(courseKnowledgeArea);
        process.setDay(subject.getDay());
        process.setStartTime(subject.getStartTime());
        process.setHoursPerDay(subject.getHoursPerDay());

        processes.put(subject.getProcess(), process);
      }

      return processes;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public Course find(Predicate<Course> predicate) {
    try {
      return courseContext.find(predicate);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public void save(CourseDto courseDto) {
    var course = new Course.Builder()
                      .code(courseDto.getCode())
                      .name(courseDto.getName())
                      .knowledgeArea(courseDto.getKnowledgeArea())
                      .build();

    try {
      courseContext.insert(course);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void edit(String id, CourseDto courseDto) {
    var course = new Course.Builder()
                      .id(Integer.parseInt(id))
                      .code(courseDto.getCode())
                      .name(courseDto.getName())
                      .knowledgeArea(courseDto.getKnowledgeArea())
                      .build();

    try {
      courseContext.update(course, c -> c.getId().equals(course.getId()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void delete(Integer id, CourseDto courseDto) {
    try {
      courseContext.delete(c -> c.getId().equals(id));
      var subject = subjectContext.find(s -> s.getCourseCode().equals(courseDto.getCode()));
      if (subject == null) return;
      subjectContext.delete(s -> s.getCourseCode().equals(courseDto.getCode()));
      registrationContext.delete(r -> r.getProcessCode().equals(subject.getProcess()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
