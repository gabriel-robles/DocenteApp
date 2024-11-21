package fateczl.docenteapp.controllers.dtos;

public class TeacherDto {
	
	private String nomeTeacher;
	private String nCPFTeacher;
	private String areaTeacher;
	private Integer pontuacaoTeacher;
	
	public String getNomeTeacher() {
		return nomeTeacher;
	}
	public void setNomeTeacher(String nomeTeacher) {
		this.nomeTeacher = nomeTeacher;
	}
	public String getCPFTeacher() {
		return nCPFTeacher;
	}
	public void setCPFTeacher(String nCPFTeacher) {
		this.nCPFTeacher = nCPFTeacher;
	}
	public String getAreaTeacher() {
		return areaTeacher;
	}
	public void setAreaTeacher(String areaCandidate) {
		this.areaTeacher = areaCandidate;
	}
	public Integer getPontuacaoTeacher() {
		return pontuacaoTeacher;
	}
	public void setPontuacaoTeacher(Integer pontuacaoTeacher) {
		this.pontuacaoTeacher = pontuacaoTeacher;
	}
	
	
	
}
