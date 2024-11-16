package fateczl.csvdb.exceptions;

import java.io.File;
import java.io.IOException;

/**
 * Exception thrown when a file creation operation fails.
 */
public class CreateFileException extends IOException {
  /**
   * Constructs a new CreateFileException with the specified file.
   *
   * @param file the file that failed to be created.
   */
  public CreateFileException(File file) {
    super("Failed to create file: " + file.getAbsolutePath());
  }
}
