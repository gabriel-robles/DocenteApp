package fateczl.docenteapp.model;

import fateczl.csvdb.annotations.CsvColumn;

public class Registration {
	@CsvColumn(columnIndex = "0", columnName = "Id", primaryKey = true)
	private final Integer id;

	@CsvColumn(columnIndex = "1", columnName = "Cpf")
	private final String cpf;

	@CsvColumn(columnIndex = "2", columnName = "SubjectCode")
	private final String subjectCode;
	  
	@CsvColumn(columnIndex = "3", columnName = "ProcessCode")
	private final String processCode;
	  
	public Registration() {
		id = null;
		cpf = null;
		subjectCode = null;
		processCode = null;
	}
	  
	private Registration(Builder builder){
	  id = builder.id;
	  cpf = builder.cpf;
	  subjectCode = builder.subjectCode;
	  processCode = builder.processCode;
	}

	public Integer getId() {
		return id;
	}

	public String getCpf() {
		return cpf;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public String getProcessCode() {
		return processCode;
	}
	  
	@Override
	public String toString() {
		return "Registrartion{"
		    + "id=" + id
		    + ", cpf='" + cpf + '\''
		    + ", subjectCode='" + subjectCode + '\''
		    + ", processCode='" + processCode + '\''
		    + '}';
	}
	  
	  
	/**
	 * Builder class for constructing instances of {@link Registration}.
	 */
	public static class Builder {
		private Integer id;
		private String cpf;
		private String subjectCode;
		private String processCode;
	
		/**
		 * Sets the id for the registration.
		 *
		 * @param id the id to set
		 * @return the Builder instance for method chaining
		 */
		public Builder id(Integer id) {
			this.id = id;
			return this;
		}
			
		/**
		 * Sets the CPF for the registration.
		 *
		 * @param cpf the CPF to set
		 * @return the Builder instance for method chaining
		 */
		public Builder cpf(String cpf) {
			this.cpf = cpf;
			return this;
		}
			
		/**
		 * Sets the subject code for the registration.
		 *
		 * @param subjectCode the code of the subject to be set
		 * @return the Builder instance for method chaining
		 */
		public Builder subjectCode(String subjectCode) {
			this.subjectCode = subjectCode;
			return this;
		}
			
		/**
		 * Sets the process code for the registration.
		 *
		 * @param processCode the process code to set
		 * @return the Builder instance for method chaining
		 */
		public Builder processCode(String processCode) {
			this.processCode = processCode;
			return this;
		}
			
		/**
		 * Builds and returns a new instance of the Registration class using the current state of the builder.
		 *
		 * @return a new Registration instance
		 */
		public Registration build() {
			return new Registration(this);
		}
	}   
}
