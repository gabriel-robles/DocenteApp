package fateczl.docenteapp.controllers;

import java.util.function.Predicate;

import fateczl.csvdb.CsvContext;
import fateczl.docenteapp.controllers.dtos.TeacherDto;
import fateczl.docenteapp.models.Teacher;
import fateczl.util.Queue;

public class TeacherController {
	private final CsvContext<Teacher> csvContext;
	  
	public TeacherController(CsvContext<Teacher> csvContext) {
	    this.csvContext = csvContext;
	}

 	public Queue<Teacher> getAll() {
	  try {
	    return csvContext.readAll();
	  } catch (Exception e) {
	    e.printStackTrace();
	    return null;
	  }
	}
	
	public Teacher find(Predicate<Teacher> predicate) {
	  try {
	    return csvContext.find(predicate);
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
	    csvContext.insert(teacher);
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
	    csvContext.update(teacher, c -> c.getId().equals(teacher.getId()));
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
