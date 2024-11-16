package fateczl.csvdb;

import java.io.IOException;

/**
 * Factory class for creating instances of {@link CsvContext}.
 * This class cannot be instantiated.
 */
public final class CsvContextFactory {

  /**
   * Private constructor to prevent instantiation.
   */
  private CsvContextFactory() {
  }

  /**
   * Creates a new {@link CsvContext} instance.
   *
   * @param <T> the type of the objects to be mapped
   * @param filename the name of the CSV file
   * @param mapper the {@link CsvMapper} to map CSV data to objects
   * @param type the class type of the objects to be mapped
   * @return a new {@link CsvContext} instance
   * @throws IOException if an I/O error occurs
   */
  public static <T> CsvContext<T> create(String filename, CsvMapper<T> mapper, Class<T> type) throws IOException {
    return new CsvContextImpl<>(filename, mapper, type);
  }
}
