package fateczl.csvdb;

import fateczl.util.Queue;
import java.io.IOException;
import java.util.function.Predicate;


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
   * Inserts the specified object into the CSV database.
   *
   * @param object the object to be inserted
   * @throws IOException if an I/O error occurs during the insertion process
   */
  void insert(T object) throws IOException;

  /**
   * Updates an object in the CSV database that matches the given predicate.
   *
   * @param object the object to update in the database
   * @param predicate the condition to match the object that needs to be updated
   * @throws IOException if an I/O error occurs during the update process
   */
  void update(T object, Predicate<T> predicate) throws IOException;

  /**
   * Deletes entries from the CSV database that match the given predicate.
   *
   * @param predicate the condition to determine which entries should be deleted
   * @throws IOException if an I/O error occurs during the deletion process
   */
  void delete(Predicate<T> predicate) throws IOException;

  /**
   * Finds and returns a queue of elements that match the given predicate.
   *
   * @param predicate the condition to be matched by the elements
   * @return a queue of elements that satisfy the predicate
   * @throws IOException if an I/O error occurs
   */
  Queue<T> findMany(Predicate<T> predicate) throws IOException;

  /**
   * Finds the first record that matches the given predicate.
   *
   * @param predicate the predicate to match
   * @return the record that matches the predicate, or null if no such record is found
   * @throws IOException if an I/O error occurs during the search process
   */
  T find(Predicate<T> predicate) throws IOException;
}
