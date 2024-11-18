package fateczl.docenteapp.controllers;

import fateczl.csvdb.CsvContext;
import fateczl.docenteapp.model.Registration;
import fateczl.docenteapp.views.dtos.RegistrationDto;
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

	  public void save(RegistrationDto registrationDto) {
	    var registration = new Registration.Builder()
	                      .CPFRegistration(registrationDto.getCPFRegistration())
	                      .CodigoDisciplinaRegistration(registrationDto.getCodigoDisciplinaRegistration())
	                      .CodigoProcessoRegistration(registrationDto.getCodigoProcessoRegistration())
	                      .build();

	    try {
	      csvContext.insert(registration);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

	  public void edit(String id, RegistrationDto registrationDto) {
	    var registration = new Registration.Builder()
	                      .Id(Integer.parseInt(id))
	                      .CPFRegistration(registrationDto.getCPFRegistration())
	                      .CodigoDisciplinaRegistration(registrationDto.getCodigoDisciplinaRegistration())
	                      .CodigoProcessoRegistration(registrationDto.getCodigoProcessoRegistration())
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
