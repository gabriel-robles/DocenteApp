package fateczl.docenteapp.controllers;

import fateczl.csvdb.CsvContext;
import fateczl.docenteapp.model.Course;
import fateczl.docenteapp.model.Subject;
import fateczl.docenteapp.views.dtos.CourseDto;
import fateczl.docenteapp.views.dtos.SubjectDto;
import fateczl.util.Queue;

public class SubjectController {

	private final CsvContext<Subject> csvContext;
	
	public SubjectController(CsvContext<Subject> csvContext) {
		this.csvContext = csvContext;
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
				.name(subjectDto.getDay()).courseId(subjectDto.getCourseId()).hoursPerDay(subjectDto.getHoursPerDay())
				.startTime(subjectDto.getStartTime()).build();

		try {
			csvContext.insert(subject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void edit(String id, SubjectDto subjectDto) {
		Subject course = new Subject.Builder().id(Integer.parseInt(id)).code(subjectDto.getCode()).name(subjectDto.getName())
				.name(subjectDto.getDay()).courseId(subjectDto.getCourseId()).hoursPerDay(subjectDto.getHoursPerDay())
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
}
