package fateczl.csvdb.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation to specify metadata for a CSV column.
 * <p>
 * This annotation can be used to define the index, name, and primary key status of a column in a CSV file.
 * </p>
 *
 * <pre>
 * Example usage:
 * {@code
 * @CsvColumn(columnIndex = "0", columnName = "ID", primaryKey = true)
 * private int id;
 * }
 * </pre>
 *
 * <ul>
 * <li>{@code columnIndex} - Specifies the index of the column in the CSV file.</li>
 * <li>{@code columnName} - The name of the column.</li>
 * <li>{@code primaryKey} - Specifies whether the column is a primary key. Default is {@code false}.</li>
 * </ul>
 *
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Target
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CsvColumn {
  /**
   * Specifies the index of the column in the CSV file.
   *
   * @return the index of the column as a String
   */
  String columnIndex();

  /**
   * The name of the column.
   *
   * @return the name of the column
   */
  String columnName();

  /**
   * Specifies whether the column is a primary key.
   *
   * @return true if the column is a primary key, false otherwise
   */
  boolean primaryKey() default false;
}
