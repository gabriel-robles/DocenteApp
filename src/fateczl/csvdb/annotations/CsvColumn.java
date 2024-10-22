package fateczl.csvdb.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify metadata for a CSV column.
 * <p>
 * This annotation can be used to define the index, name, and key constraints
 * of a field that corresponds to a column in a CSV file.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * @CsvColumn(columnIndex = "0", columnName = "id", primaryKey = true)
 * private int id;
 * }
 * </pre>
 *
 * <p>Attributes:</p>
 * <ul>
 *   <li>{@code columnIndex} - Specifies the index of the column in the CSV file.</li>
 *   <li>{@code columnName} - The name of the column.</li>
 *   <li>{@code primaryKey} - Indicates whether the annotated field is a primary key.
 *   Default is {@code false}.</li>
 *   <li>{@code foreignKey} - Indicates whether the column is a foreign key.
 *   Default is {@code false}.</li>
 * </ul>
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
   * Indicates whether the annotated field is a primary key.
   *
   * @return true if the field is a primary key, false otherwise.
   */
  boolean primaryKey() default false;

  /**
   * Indicates whether the column is a foreign key.
   *
   * @return {@code true} if the column is a foreign key, {@code false} otherwise.
   */
  boolean foreignKey() default false;
}
