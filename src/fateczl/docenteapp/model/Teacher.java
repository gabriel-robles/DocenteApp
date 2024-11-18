package fateczl.docenteapp.model;

import fateczl.csvdb.annotations.CsvColumn;

public class Teacher 
{
	@CsvColumn(columnIndex = "0", columnName = "Id", primaryKey = true)
	  private final Integer id;

	  @CsvColumn(columnIndex = "1", columnName = "NomeTeacher")
	  private final String nomeTeacher;

	  @CsvColumn(columnIndex = "2", columnName = "nCPFTeacher")
	  private final String nCPFTeacher;
	  
	  @CsvColumn(columnIndex = "3", columnName = "AreaTeacher")
	  private final String areaTeacher;
	  
	  @CsvColumn(columnIndex = "4", columnName = "Pontuacao")
	  private final Integer pontuacao;
	   
	  public Teacher() {
	    this.id = null;
	    this.nomeTeacher = null;
	    this.nCPFTeacher = null;
	    this.areaTeacher = null;
	    this.pontuacao = null;
	  }

	  Teacher(Builder builder) {
	    this.id = builder.id;
	    this.nomeTeacher = builder.nomeTeacher;
	    this.nCPFTeacher = builder.nCPFTeacher;
	    this.areaTeacher = builder.areaTeacher;
	    this.pontuacao = builder.pontuacao;
	    
	  }
	  
	  public Integer getId() {
	    return id;
	  }
	  
	  public String getNomeTeacher() {
	    return nomeTeacher;
	  }
	  
	  public String getCPFTeacher() {
	    return nCPFTeacher;
	  }
	  
	  public String getAreaTeacher() {
	    return areaTeacher;
	  }
	  
	  public Integer getPontuacaoTeacher() {
		    return pontuacao;
		  }


	  @Override
	  public String toString() {
	    return "Course{"
	        + "id=" + id
	        + ", nomeTeacher='" + nomeTeacher + '\''
	        + ", nCPFTeacher='" + nCPFTeacher + '\''
	        + ", areaTeacher='" + areaTeacher + '\''
	        + ", pontuacao='" + pontuacao + '\''
	        + '}';
	  }

	  public static class Builder {
	    private Integer id;
	    private String nomeTeacher;
	    private String nCPFTeacher;
		private String areaTeacher;
		private Integer pontuacao;
		
			 
	    public Builder id(Integer id) {
	      this.id = id;
	      return this;
	    }

	
	    public Builder NomeTeacher(String nomeTeacher) {
	      this.nomeTeacher = nomeTeacher;
	      return this;
	    }

	
	    public Builder CPFTeacher(String nCPFTeacher) {
	      this.nCPFTeacher = nCPFTeacher;
	      return this;
	    }

	 
	    public Builder AreaTeacher(String areaTeacher) {
	      this.areaTeacher = areaTeacher;
	      return this;
	    }

	    
	    public Builder Pontuacao(Integer pontuacao) {
		      this.pontuacao = pontuacao;
		      return this;
		    }
	    
	    public Teacher build() {
	      return new Teacher(this);
	    }
	  }
}
