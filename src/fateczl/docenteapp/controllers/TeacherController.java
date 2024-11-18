package fateczl.docenteapp.controllers;

import fateczl.csvdb.CsvContext;
import fateczl.docenteapp.model.Teacher;
import fateczl.docenteapp.views.dtos.TeacherDto;
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
		  
					
		  public void save(TeacherDto teacherDto) {
		    var teacher = new Teacher.Builder()
		                      .NomeTeacher(teacherDto.getNomeTeacher())
		                      .CPFTeacher(teacherDto.getCPFTeacher())
		                      .AreaTeacher(teacherDto.getAreaTeacher())
		                      .Pontuacao(teacherDto.getPontuacaoTeacher())
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
					  .NomeTeacher(teacherDto.getNomeTeacher())
                      .CPFTeacher(teacherDto.getCPFTeacher())
                      .AreaTeacher(teacherDto.getAreaTeacher())
                      .Pontuacao(teacherDto.getPontuacaoTeacher())
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
