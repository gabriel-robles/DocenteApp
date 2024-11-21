package fateczl.docenteapp.controllers;

import java.util.function.Predicate;

import fateczl.csvdb.CsvContext;
import fateczl.docenteapp.controllers.dtos.RegistrationDto;
import fateczl.docenteapp.models.Registration;
import fateczl.util.Queue;

public class RegistrationController {
	private final CsvContext<Registration> csvContext;

	public RegistrationController(CsvContext<Registration> csvContext) {
	  this.csvContext = csvContext;
	}

	public Queue<Registration> getAll() {
	  try {
	    return csvContext.readAll();
	  } catch (Exception e) {
	    e.printStackTrace();
	    return null;
	  }
	}

	public Queue<Registration> findMany(Predicate<Registration> predicate) {
	  try {
	    return csvContext.findMany(predicate);
	  } catch (Exception e) {
	    e.printStackTrace();
	    return null;
	  }
	}

	public void save(RegistrationDto registrationDto) {
	  var registration = new Registration.Builder()
	                    			.cpf(registrationDto.getCpf())
	                    			.subjectCode(registrationDto.getSubjectCode())
	                    			.processCode(registrationDto.getProcessCode())
	                    			.build();

	  try {
	    csvContext.insert(registration);
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	}

	public void edit(String id, RegistrationDto registrationDto) {
	  var registration = new Registration.Builder()
	                    			.id(Integer.parseInt(id))
	                    			.cpf(registrationDto.getCpf())
	                    			.subjectCode(registrationDto.getSubjectCode())
	                    			.processCode(registrationDto.getProcessCode())
	                    			.build();

	  try {
	    csvContext.update(registration, c -> c.getId().equals(registration.getId()));
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
