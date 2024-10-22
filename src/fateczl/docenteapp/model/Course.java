package fateczl.docenteapp.model;

import fateczl.csvdb.annotations.CsvColumn;

/**
 * The Course class represents a course with an ID, code, name, and knowledge area.
 * It provides methods to create, update, and convert Course objects to and from Record objects.
 * 
 * <p>Example usage:
 * <pre>
 *     Course course = Course.create(
 *          "CS101", "Introduction to Computer Science", "Computer Science");
 *     System.out.println(course);
 * </pre>
 * </p>
 * 
 * <p>Methods:
 * <ul>
 *     <li>{@link #create(String, String, String)} - Creates a new Course instance.</li>
 *     <li>
 *      {@link #update(Course, String, String, String)} - Updates an existing Course instance.
 *     </li>
 *     <li>{@link #getId()} - Returns the ID of the course.</li>
 *     <li>{@link #getCode()} - Returns the code of the course.</li>
 *     <li>{@link #getName()} - Returns the name of the course.</li>
 *     <li>{@link #getKnowledgeArea()} - Returns the knowledge area of the course.</li>
 *     <li>{@link #toString()} - Returns a string representation of the Course instance.</li>
 * </ul>
 * </p>
 * 
 * <p>Note: The constructor is private and should be accessed via the static methods 
 * {@link #create(String, String, String)} and {@link #update(Course, String, String, String)}.</p>
 *
 * @see Record
 */
public class Course {
  public static final String ID_COLUMN = "Id";
  public static final String CODE_COLUMN = "Code";
  public static final String NAME_COLUMN = "Name";
  public static final String KNOWLEDGE_AREA_COLUMN = "KnowledgeArea";

  @CsvColumn(columnIndex = "0", columnName = ID_COLUMN, primaryKey = true)
  private Integer id;

  @CsvColumn(columnIndex = "1", columnName = CODE_COLUMN)
  private String code;

  @CsvColumn(columnIndex = "2", columnName = NAME_COLUMN)
  private String name;
  
  @CsvColumn(columnIndex = "3", columnName = KNOWLEDGE_AREA_COLUMN)
  private String knowledgeArea;

  /**
   * Default constructor for the Course class.
   * Initializes a new instance of the Course class.
   */
  public Course() {
  }

  private Course(String code, String name, String knowledgeArea) {
    this.code = code;
    this.name = name;
    this.knowledgeArea = knowledgeArea;
  }

  private Course(Integer id, String code, String name, String knowledgeArea) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.knowledgeArea = knowledgeArea;
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

  /**
   * Creates a new Course instance with the specified code, name, and knowledge area.
   *
   * @param code the unique code of the course
   * @param name the name of the course
   * @param knowledgeArea the knowledge area to which the course belongs
   * @return a new Course instance
   */
  public static Course create(String code, String name, String knowledgeArea) {
    return new Course(code, name, knowledgeArea);
  }

  /**
   * Updates the details of an existing Course object.
   *
   * @param course The existing Course object to be updated.
   * @param code The new code for the course.
   * @param name The new name for the course.
   * @param knowledgeArea The new knowledge area for the course.
   * @return A new Course object with the updated details.
   */
  public static Course update(Course course, String code, String name, String knowledgeArea) {
    return new Course(course.id, code, name, knowledgeArea);
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
}
