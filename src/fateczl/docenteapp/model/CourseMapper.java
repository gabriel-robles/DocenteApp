package fateczl.docenteapp.model;

import fateczl.csvdb.CsvContextImpl;
import fateczl.csvdb.CsvMapper;
import fateczl.csvdb.annotations.CsvColumn;
import fateczl.util.LinkedList;

/**
 * The CourseMapper class implements the CsvMapper interface for the Course class.
 * It provides methods to map a CSV line to a Course object
 * and to unmap a Course object to a CSV line.
 * 
 * <p>This class uses reflection to dynamically
 * map fields based on the {@link CsvColumn} annotation.
 * It supports both Integer and String field types.
 * 
 * <p>Methods:
 * <ul>
 *   <li>{@link #map(String)} - Maps a CSV line to a Course object.</li>
 *   <li>{@link #unmap(Course)} - Unmaps a Course object to a CSV line.</li>
 * </ul>
 * 
 * <p>Example usage:
 * <pre>
 * {@code
 * CourseMapper mapper = new CourseMapper();
 * Course course = mapper.map("1,CS101,Computer Science,Science");
 * String csvLine = mapper.unmap(course);
 * }
 * </pre>
 *
 * @see CsvMapper
 * @see CsvColumn
 * @see Course
 */
public class CourseMapper implements CsvMapper<Course> {
  private static final Class<Course> RECORD_TYPE = Course.class;

  @Override
  public Course map(String line) {
    var fields = RECORD_TYPE.getDeclaredFields();
    var values = line.split(CsvContextImpl.DELIMITER);

    var course = new Course();

    for (var field : fields) {
      var annotation = field.getAnnotation(CsvColumn.class);
      if (annotation != null) {
        field.setAccessible(true);
        var columnIndex = Integer.parseInt(annotation.columnIndex());
        try {
          if (field.getType() == Integer.class) {
            field.set(course, Integer.parseInt(values[columnIndex]));
          } else {
            field.set(course, values[columnIndex]);
          }
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }

    return course;
  }

  @Override
  public String unmap(Integer lastIndex, Course rec) {
    var fields = RECORD_TYPE.getDeclaredFields();
    var values = new LinkedList<String>();

    for (var field : fields) {
      var annotation = field.getAnnotation(CsvColumn.class);
      if (annotation != null) {
        var columnIndex = Integer.parseInt(annotation.columnIndex());
        var columnName = annotation.columnName();
        switch (columnName) {
          case Course.ID_COLUMN -> values.add(lastIndex.toString(), columnIndex);
          case Course.CODE_COLUMN -> values.add(rec.getCode(), columnIndex);
          case Course.NAME_COLUMN -> values.add(rec.getName(), columnIndex);
          case Course.KNOWLEDGE_AREA_COLUMN -> values.add(rec.getKnowledgeArea(), columnIndex);
          default -> throw new IllegalArgumentException("Invalid column name: " + columnName);
        }
      }
    }

    var valuesList = values.toArray(new String[0]);

    return String.join(CsvContextImpl.DELIMITER, valuesList);
  }

  @Override
  public String unmap(Course rec) {
    var fields = RECORD_TYPE.getDeclaredFields();
    var values = new LinkedList<String>();

    for (var field : fields) {
      var annotation = field.getAnnotation(CsvColumn.class);
      if (annotation != null) {
        var columnIndex = Integer.parseInt(annotation.columnIndex());
        var columnName = annotation.columnName();
        switch (columnName) {
          case Course.ID_COLUMN -> values.add(rec.getId().toString(), columnIndex);
          case Course.CODE_COLUMN -> values.add(rec.getCode(), columnIndex);
          case Course.NAME_COLUMN -> values.add(rec.getName(), columnIndex);
          case Course.KNOWLEDGE_AREA_COLUMN -> values.add(rec.getKnowledgeArea(), columnIndex);
          default -> throw new IllegalArgumentException("Invalid column name: " + columnName);
        }
      }
    }

    var valuesList = values.toArray(new String[0]);

    return String.join(CsvContextImpl.DELIMITER, valuesList);
  }

  @Override
  public boolean equals(Course obj1, Course obj2) {
    return obj1.getId().equals(obj2.getId());
  }
}
