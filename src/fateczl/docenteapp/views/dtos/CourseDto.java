package fateczl.docenteapp.views.dtos;

public class CourseDto {
  private String code;
  private String name;
  private String knowledgeArea;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getKnowledgeArea() {
    return knowledgeArea;
  }

  public void setKnowledgeArea(String knowledgeArea) {
    this.knowledgeArea = knowledgeArea;
  }
}
