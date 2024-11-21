package fateczl.docenteapp.controllers.dtos;

public class ProcessDto {
  private String courseName;
  private String process;
  private String subjectName;
  private String knowledgeArea;
  private String day;
  private String startTime;
  private String hoursPerDay;

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getProcess() {
    return process;
  }

  public void setProcess(String process) {
    this.process = process;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
  }

  public String getKnowledgeArea() {
    return knowledgeArea;
  }

  public void setKnowledgeArea(String knowledgeArea) {
    this.knowledgeArea = knowledgeArea;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getHoursPerDay() {
    return hoursPerDay;
  }

  public void setHoursPerDay(String hoursPerDay) {
    this.hoursPerDay = hoursPerDay;
  }
}
