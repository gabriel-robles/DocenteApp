package fateczl.docenteapp.model;

import fateczl.csvdb.annotations.CsvColumn;

public class Teacher {
	@CsvColumn(columnIndex = "0", columnName = "Id", primaryKey = true)
	private final Integer id;

  @CsvColumn(columnIndex = "1", columnName = "Name")
  private final String name;

  @CsvColumn(columnIndex = "2", columnName = "Cpf")
  private final String cpf;
	  
  @CsvColumn(columnIndex = "3", columnName = "Area")
  private final String area;
	  
  @CsvColumn(columnIndex = "4", columnName = "Score")
  private final Integer score;
	   
  public Teacher() {
    this.id = null;
    this.name = null;
    this.cpf = null;
    this.area = null;
    this.score = null;
  }

  private Teacher(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.cpf = builder.cpf;
    this.area = builder.area;
    this.score = builder.score;
  }
	  
	public Integer getId() {
	  return id;
	}
	  
  public String getName() {
    return name;
  }
	  
  public String getCpf() {
    return cpf;
  }
	  
  public String getArea() {
    return area;
  }
	  
  public Integer getScore() {
	  return score;
	}

	@Override
	public String toString() {
	  return "Course{"
	      + "id=" + id
	      + ", name='" + name + '\''
	      + ", cpf='" + cpf + '\''
	      + ", area='" + area + '\''
	      + ", score='" + score + '\''
	      + '}';
	}

	/**
	 * Builder class for constructing instances of {@link Teacher}.
	 */
	public static class Builder {
	  private Integer id;
	  private String name;
	  private String cpf;
		private String area;
		private Integer score;
			 
		/**
		 * Sets the id of the teacher.
		 *
		 * @param id the id to set
		 * @return the Builder instance for method chaining
		 */
	  public Builder id(Integer id) {
	    this.id = id;
	    return this;
	  }
	
		/**
		 * Sets the name of the teacher.
		 *
		 * @param name the name of the teacher
		 * @return the Builder instance for chaining
		 */
	  public Builder name(String name) {
	    this.name = name;
	    return this;
	  }
	
		/**
		 * Sets the CPF for the teacher.
		 *
		 * @param cpf the CPF of the teacher
		 * @return the Builder instance for method chaining
		 */
	  public Builder cpf(String cpf) {
	    this.cpf = cpf;
	    return this;
	  }
	 
		/**
		 * Sets the area of expertise for the teacher.
		 *
		 * @param area the area of expertise to set
		 * @return the Builder instance for method chaining
		 */
	  public Builder area(String area) {
	    this.area = area;
	    return this;
	  }
	    
		/**
		 * Sets the score for the teacher.
		 *
		 * @param score the score to be assigned to the teacher
		 * @return the Builder instance for method chaining
		 */
	  public Builder score(Integer score) {
		  this.score = score;
		  return this;
		}
	    
		/**
		 * Builds and returns a new Teacher instance using the current state of the builder.
		 *
		 * @return a new Teacher instance
		 */
	  public Teacher build() {
	    return new Teacher(this);
	  }
	}
}
