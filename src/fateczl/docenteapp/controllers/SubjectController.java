package fateczl.docenteapp.controllers;

import java.io.IOException;

import fateczl.csvdb.CsvContext;
import fateczl.csvdb.CsvContextFactory;
import fateczl.csvdb.CsvMapperFactory;
import fateczl.docenteapp.model.Course;
import fateczl.docenteapp.model.Subject;
import fateczl.docenteapp.views.dtos.CourseDto;
import fateczl.docenteapp.views.dtos.SubjectDto;
import fateczl.util.Queue;

public class SubjectController {

	private final CsvContext<Subject> csvContext;
	
	private CourseController courseController;
	
	public SubjectController(CsvContext<Subject> csvContext) throws IOException {
		this.csvContext = csvContext;
		
		var courseMapper = CsvMapperFactory.create(Course.class);
	    var courseContext = CsvContextFactory.create("courses", courseMapper, Course.class);
	    this.courseController = new CourseController(courseContext);
		
	}

	public Queue<Subject> getAll() {
		try {
			return csvContext.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void save(SubjectDto subjectDto) {
		var subject = new Subject.Builder().code(subjectDto.getCode()).name(subjectDto.getName())
				.day(subjectDto.getDay()).courseId(subjectDto.getCourseId()).hoursPerDay(subjectDto.getHoursPerDay())
				.startTime(subjectDto.getStartTime()).build();

		try {
			csvContext.insert(subject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void edit(String id, SubjectDto subjectDto) {
		Subject course = new Subject.Builder().id(Integer.parseInt(id)).code(subjectDto.getCode()).name(subjectDto.getName())
				.day(subjectDto.getDay()).courseId(subjectDto.getCourseId()).hoursPerDay(subjectDto.getHoursPerDay())
				.startTime(subjectDto.getStartTime()).build();

		try {
			csvContext.update(course, c -> c.getId().equals(course.getId()));
		} catch (Exception e) {
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
	
	public String[] getCourses() {
		
		Queue<Course> courses = courseController.getAll();

		int tamanho = courses.size();

		String[] array = new String[tamanho];

		int x = 0;

		while (!courses.isEmpty()) {
			array[x] = courses.dequeue().getName();
			x++;
		}
		return array;
	}
	
	public Integer searchCourse(String courseName) {

		Queue<Course> courses = courseController.getAll();
		
		Integer codeSearched = 0;
		
		while(!courses.isEmpty()) {
			Course curso = courses.dequeue();
			codeSearched = curso.getId();
			if(courseName.equals(curso.getName())) {
				break;
			}
		}


		return codeSearched;
	}
	
	public String getCourseName(Integer id) {
		
		Queue<Course> courses = courseController.getAll();
		
		String nameSearched = "";
		
		while(!courses.isEmpty()) {
			Course course = courses.dequeue();
			nameSearched = course.getName();
			if(id.equals(course.getId())) {
				break;
			}
		}
		return nameSearched;
		
	}
	
}
