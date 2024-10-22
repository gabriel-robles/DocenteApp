package fateczl.csvdb;

import fateczl.util.Queue;
import java.io.IOException;


/**
 * Interface representing a CSV database with basic CRUD operations.
 */
public interface CsvContext<T> {
  /**
   * Reads all records from the database.
   *
   * @return A queue containing all records.
   * @throws IOException If an I/O error occurs.
   */
  Queue<T> readAll() throws IOException;


  /**
   * Inserts a new record into the CSV database.
   *
   * @param rec the record to be inserted
   * @throws IOException if an I/O error occurs during the insertion process
   */
  void insert(T rec) throws IOException;

  /**
   * Updates an existing record in the CSV database.
   *
   * @param rec the record to be updated
   * @throws IOException if an I/O error occurs during the update process
   */
  void update(T rec) throws IOException;

  /**
   * Deletes a record from the CSV database.
   *
   * @param rec the record to be deleted
   * @throws IOException if an I/O error occurs during the deletion process
   */
  void delete(T rec) throws IOException;
}
