package fateczl.docenteapp.model;

import fateczl.csvdb.annotations.CsvColumn;

public class Course {
  @CsvColumn(columnIndex = "0", columnName = "Id", primaryKey = true)
  private final Integer id;

  @CsvColumn(columnIndex = "1", columnName = "Code")
  private final String code;

  @CsvColumn(columnIndex = "2", columnName = "Name")
  private final String name;
  
  @CsvColumn(columnIndex = "3", columnName = "KnowledgeArea")
  private final String knowledgeArea;

  public Course() {
    this.id = null;
    this.code = null;
    this.name = null;
    this.knowledgeArea = null;
  }

  Course(Builder builder) {
    this.id = builder.id;
    this.code = builder.code;
    this.name = builder.name;
    this.knowledgeArea = builder.knowledgeArea;
  }
  
  public Integer getId() {
    return id;
  }
  
  public String getCode() {
    return code;
  }
  
  public String getName() {
    return name;
  }
  
  public String getKnowledgeArea() {
    return knowledgeArea;
  }

  @Override
  public String toString() {
    return "Course{"
        + "id=" + id
        + ", code='" + code + '\''
        + ", name='" + name + '\''
        + ", knowledgeArea='" + knowledgeArea + '\''
        + '}';
  }

  /**
   * Builder class for constructing Course objects.
   */
  public static class Builder {
    private Integer id;
    private String code;
    private String name;
    private String knowledgeArea;

    /**
     * Sets the id for the course.
     *
     * @param id the id to set
     * @return the Builder instance for method chaining
     */
    public Builder id(Integer id) {
      this.id = id;
      return this;
    }

    /**
     * Sets the code for the course.
     *
     * @param code the code to set
     * @return the Builder instance for method chaining
     */
    public Builder code(String code) {
      this.code = code;
      return this;
    }

    /**
     * Sets the name of the course.
     *
     * @param name the name of the course
     * @return the Builder instance for chaining
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets the name of the course.
     *
     * @param name the name of the course
     * @return the Builder instance for chaining
     */
    public Builder knowledgeArea(String knowledgeArea) {
      this.knowledgeArea = knowledgeArea;
      return this;
    }

    /**
     * Builds and returns a new Course instance using the current state of the builder.
     *
     * @return a new Course instance
     */
    public Course build() {
      return new Course(this);
    }
  }
}
