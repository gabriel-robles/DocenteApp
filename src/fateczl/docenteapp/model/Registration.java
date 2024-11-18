package fateczl.docenteapp.model;

import fateczl.csvdb.annotations.CsvColumn;



public class Registration {
	
	@CsvColumn(columnIndex = "0", columnName = "id", primaryKey = true)
	  private final Integer id;

	  @CsvColumn(columnIndex = "1", columnName = "CPFRegistration")
	  private final String nCPFRegistration;

	  @CsvColumn(columnIndex = "2", columnName = "CodigoDisciplinaRegistration")
	  private final String codigoDisciplinaRegistration;
	  
	  @CsvColumn(columnIndex = "3", columnName = "CodigoProcessoRegistration")
	  private final String codigoProcessoRegistration;
	  
	  public Registration() {
		  this.id = null;
		  this.nCPFRegistration = null;
		  this.codigoDisciplinaRegistration = null;
		  this.codigoProcessoRegistration = null;
	  }
	  
	  Registration(Builder builder){
		  this.id = builder.id;
		  this.nCPFRegistration = builder.nCPFRegistration;
		  this.codigoDisciplinaRegistration = builder.codigoDisciplinaRegistration;
		  this.codigoProcessoRegistration = builder.codigoProcessoRegistration;
	  }

	public Integer getId() {
		return id;
	}

	public String getCPFRegistration() {
		return nCPFRegistration;
	}

	public String getCodigoDisciplinaRegistration() {
		return codigoDisciplinaRegistration;
	}

	public String getCodigoProcessoRegistration() {
		return codigoProcessoRegistration;
	}
	  
	  @Override
	public String toString() {
		  return "Registrartion{"
			        + "id=" + id
			        + ", nCPFRegistration='" + nCPFRegistration + '\''
			        + ", codigoDisciplinaRegistration='" + codigoDisciplinaRegistration + '\''
			        + ", codigoProcessoRegistration='" + codigoProcessoRegistration + '\''
			        + '}';
	}
	  
	  
	public static class Builder {
		private Integer id;
		private String nCPFRegistration;
		private String codigoDisciplinaRegistration;
		private String codigoProcessoRegistration;
	
		
			public Builder Id(Integer id) {
				this.id = id;
				return this;
			}
			
			public Builder CPFRegistration(String nCPFRegistration) {
				this.nCPFRegistration = nCPFRegistration;
				return this;
			}
			
			public Builder CodigoDisciplinaRegistration(String codigoDisciplinaRegistration) {
				this.codigoDisciplinaRegistration = codigoDisciplinaRegistration;
				return this;
			}
			
			public Builder CodigoProcessoRegistration(String codigoProcessoRegistration) {
				this.codigoProcessoRegistration = codigoProcessoRegistration;
				return this;
			}
			
			public Registration build() {
				return new Registration(this);
			}
	}   
}
