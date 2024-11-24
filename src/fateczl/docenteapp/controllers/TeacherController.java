package fateczl.docenteapp.controllers;

import java.util.function.Predicate;

import fateczl.csvdb.CsvContext;
import fateczl.docenteapp.controllers.dtos.TeacherDto;
import fateczl.docenteapp.models.Teacher;
import fateczl.docenteapp.models.Registration;
import fateczl.util.Queue;

public class TeacherController {
	private final CsvContext<Teacher> teacherContext;
	private final CsvContext<Registration> registrationContext;
	  
	public TeacherController(CsvContext<Teacher> teacherContext, CsvContext<Registration> registrationContext) {
	    this.teacherContext = teacherContext;
			this.registrationContext = registrationContext;
	}

 	public Queue<Teacher> getAll() {
	  try {
	    return teacherContext.readAll();
	  } catch (Exception e) {
	    e.printStackTrace();
	    return null;
	  }
	}
	
	public Teacher find(Predicate<Teacher> predicate) {
	  try {
	    return teacherContext.find(predicate);
	  } catch (Exception e) {
	    e.printStackTrace();
	    return null;
	  }
	}
					
	public void save(TeacherDto teacherDto) {
	  var teacher = new Teacher.Builder()
	                    	.name(teacherDto.getNomeTeacher())
	                    	.cpf(teacherDto.getCPFTeacher())
	                    	.area(teacherDto.getAreaTeacher())
	                    	.score(teacherDto.getPontuacaoTeacher())
	                    	.build();

	  try {
	    teacherContext.insert(teacher);
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	}

	public void edit(String id, TeacherDto teacherDto) {
	  var teacher = new Teacher.Builder()
			  								.id(Integer.parseInt(id))
			  								.name(teacherDto.getNomeTeacher())
                  			.cpf(teacherDto.getCPFTeacher())
                  			.area(teacherDto.getAreaTeacher())
                  			.score(teacherDto.getPontuacaoTeacher())
                  			.build();

	  try {
	    teacherContext.update(teacher, c -> c.getId().equals(teacher.getId()));
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	}

	public void delete(Integer id) {
	  try {
			var teacher = teacherContext.find(t -> t.getId().equals(id));
	    teacherContext.delete(t -> t.getId().equals(id));
			registrationContext.delete(r -> r.getCpf().equals(teacher.getCpf()));
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	}
}
