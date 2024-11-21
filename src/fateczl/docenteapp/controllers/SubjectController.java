package fateczl.docenteapp.controllers;

import java.io.IOException;
import java.util.Random;
import java.util.function.Predicate;

import fateczl.csvdb.CsvContext;
import fateczl.docenteapp.controllers.dtos.SubjectDto;
import fateczl.docenteapp.models.Registration;
import fateczl.docenteapp.models.Subject;
import fateczl.util.Queue;

public class SubjectController {
	private final CsvContext<Subject> subjectContext;
	private final CsvContext<Registration> registrationContext;
	
	public SubjectController(CsvContext<Subject> subjectContext, CsvContext<Registration> registrationContext) throws IOException {
		this.subjectContext = subjectContext;
		this.registrationContext = registrationContext;
	}

	public Queue<Subject> getAll() {
		try {
			return subjectContext.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Subject find(Predicate<Subject> predicate) {
		try {
			return subjectContext.find(predicate);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void save(SubjectDto subjectDto) {
		var number = new Random().nextInt(1000);
		var processCode = subjectDto.getCode() + "-" + number + "/" + subjectDto.getCourseCode();

		var subject = new Subject.Builder()
												.code(subjectDto.getCode())
												.process(processCode)
												.name(subjectDto.getName())
												.courseCode(subjectDto.getCourseCode())
												.day(subjectDto.getDay())
												.startTime(subjectDto.getStartTime())
												.hoursPerDay(subjectDto.getHoursPerDay())
												.build();

		try {
			subjectContext.insert(subject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void edit(String id, SubjectDto subjectDto) {
		Subject course = new Subject.Builder()
													.id(Integer.parseInt(id))
													.process(subjectDto.getProcess())
													.code(subjectDto.getCode())
													.name(subjectDto.getName())
													.courseCode(subjectDto.getCourseCode())
													.day(subjectDto.getDay())
													.startTime(subjectDto.getStartTime())
													.hoursPerDay(subjectDto.getHoursPerDay())
													.build();

		try {
			subjectContext.update(course, c -> c.getId().equals(course.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(Integer id, String process) {
		try {
			subjectContext.delete(c -> c.getId().equals(id));
			registrationContext.delete(r -> r.getProcessCode().equals(process));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
