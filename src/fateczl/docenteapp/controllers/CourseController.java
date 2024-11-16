package fateczl.docenteapp.controllers;

import fateczl.csvdb.CsvContext;
import fateczl.docenteapp.model.Course;
import fateczl.docenteapp.views.dtos.CourseDto;
import fateczl.util.Queue;

public class CourseController {
  private final CsvContext<Course> csvContext;

  public CourseController(CsvContext<Course> csvContext) {
    this.csvContext = csvContext;
  }

  public Queue<Course> getAll() {
    try {
      return csvContext.readAll();
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
      csvContext.insert(course);
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
      csvContext.update(course, c -> c.getId().equals(course.getId()));
    } catch (Exception e) {
      e.printStackTrace();
      e.printStackTrace(); 
    }
  }

  public void delete(Integer id) {
    try {
      csvContext.delete(c -> c.getId().equals(id));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
