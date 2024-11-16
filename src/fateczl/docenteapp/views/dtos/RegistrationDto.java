package fateczl.docenteapp.views.dtos;

public class RegistrationDto {

	private String nCPFRegistration;
	private String codigoDisciplinaRegistration;
	private String codigoProcessoRegistration;
	
	public String getCPFRegistration() {
		return nCPFRegistration;
	}
	public void setCPFRegistration(String nCPFRegistration) {
		this.nCPFRegistration = nCPFRegistration;
	}
	public String getCodigoDisciplinaRegistration() {
		return codigoDisciplinaRegistration;
	}
	public void setCodigoDisciplinaRegistration(String codigoDisciplinaRegistration) {
		this.codigoDisciplinaRegistration = codigoDisciplinaRegistration;
	}
	public String getCodigoProcessoRegistration() {
		return codigoProcessoRegistration;
	}
	public void setCodigoProcessoRegistration(String codigoProcessoRegistration) {
		this.codigoProcessoRegistration = codigoProcessoRegistration;
	}
}
