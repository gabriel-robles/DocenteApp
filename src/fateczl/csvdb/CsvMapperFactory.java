package fateczl.csvdb;

/**
 * Factory class for creating instances of {@link CsvMapper}.
 * This class cannot be instantiated.
 */
public final class CsvMapperFactory {
  /**
   * Private constructor to prevent instantiation.
   */
  private CsvMapperFactory() {
  }

  /**
   * Creates a new instance of CsvMapper for the specified type.
   *
   * @param <T> the type of the objects to be mapped
   * @param type the class of the type to be mapped
   * @return a new instance of CsvMapper for the specified type
   */
  public static <T> CsvMapper<T> create(Class<T> type) {
    return new CsvMapperImpl<>(type);
  }
}