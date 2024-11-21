package fateczl.docenteapp.controllers.dtos;

public class RegistrationDto {

	private String cpf;
	private String subjectCode;
	private String processCode;
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getProcessCode() {
		return processCode;
	}
	
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
}
