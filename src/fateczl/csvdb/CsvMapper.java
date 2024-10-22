package fateczl.csvdb;

/**
 * The CsvMapper interface provides methods to map a CSV line to an object of type T
 * and to unmap an object of type T back to a CSV line.
 *
 * @param <T> the type of the object that this mapper will handle
 */
public interface CsvMapper<T> {
  /**
   * Maps a line of text from a CSV file to an instance of type T.
   *
   * @param line the line of text from the CSV file
   * @return an instance of type T representing the data in the given line
   */
  T map(String line);

  /**
   * Converts a record of type T and its associated ID into a CSV string representation.
   *
   * @param id  the unique identifier of the record
   * @param rec the record to be converted to a CSV string
   * @return a CSV string representation of the record
   */
  String unmap(Integer id, T rec);

  /**
   * Converts the given record of type T into its corresponding string representation.
   *
   * @param rec the record to be converted into a string
   * @return the string representation of the given record
   */
  String unmap(T rec);

  /**
   * Compares two objects of type T for equality.
   *
   * @param obj1 the first object to be compared
   * @param obj2 the second object to be compared
   * @return true if the objects are considered equal, false otherwise
   */
  boolean equals(T obj1, T obj2);
}
