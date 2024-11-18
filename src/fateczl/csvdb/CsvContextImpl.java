package fateczl.csvdb;

import fateczl.csvdb.annotations.CsvColumn;
import fateczl.csvdb.exceptions.CreateFileException;
import fateczl.util.LinkedList;
import fateczl.util.Queue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.function.Predicate;

/**
 * CsvContextImpl is an implementation of the CsvContext interface that provides
 * methods to interact with a CSV file as a database. It supports reading, inserting,
 * updating, and deleting records in the CSV file.
 *
 * @param <T> the type of objects that this context will handle.
 */
public class CsvContextImpl<T> implements CsvContext<T> {
  /**
   * The delimiter used to separate values in the CSV file.
   */
  public static final String DELIMITER = ";";

  /**
   * The type of the class that this context is working with.
   */
  private final Class<T> type;
  /**
   * The file representing the database.
   */
  private final File dbFile;
  /**
   * The file that stores the last index used in the CSV database.
   */
  private final File lastIndexFile;
  /**
   * The CsvMapper instance used to map CSV data to objects and objects to CSV data.
   */
  private final CsvMapper<T> mapper;

  /**
   * Constructs a new CsvContextImpl instance.
   *
   * @param filename the name of the CSV file to be used
   * @param mapper the CsvMapper instance to map CSV data to objects
   * @param type the Class type of the objects to be mapped
   * @throws IOException if an I/O error occurs
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public CsvContextImpl(String filename, CsvMapper<T> mapper, Class<T> type) throws IOException {
    if (type == null) {
      throw new IllegalArgumentException("Type cannot be null");
    }

    if (filename == null) {
      throw new IllegalArgumentException("File path cannot be null");
    }

    if (mapper == null) {
      throw new IllegalArgumentException("Mapper cannot be null");
    }
    
    var currentDir = System.getProperty("user.dir");

    if (!filename.endsWith(".csv")) {
      filename += ".csv";
    }

    this.type = type;
    this.mapper = mapper;
    this.dbFile = new File(currentDir, filename);
    this.lastIndexFile = new File(currentDir, filename.replace(".csv", "last.txt"));

    createFiles(); 
  }

  @Override
  public Queue<T> readAll() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(dbFile))) {
      Queue<T> queue = new Queue<>();

      var counter = 0;

      String line;
      while ((line = reader.readLine()) != null) {
        if (counter == 0) {
          counter++;
          continue;
        }

        queue.enqueue(mapper.map(line));
      }

      return queue;
    }
  }

  @Override
  public void insert(T object) throws IOException {
    Integer lastIndex;

    try (BufferedReader reader = new BufferedReader(new FileReader(lastIndexFile))) {
      lastIndex = Integer.parseInt(reader.readLine());
    }

    lastIndex++;

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(lastIndexFile))) {
      writer.write(String.valueOf(lastIndex));
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(dbFile, true))) {
      writer.write(mapper.unmap(lastIndex, object));
      writer.newLine();
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public void update(T object, Predicate<T> predicate) throws IOException {
    Queue<T> queue = readAll();
    LinkedList<T> records = new LinkedList<>(queue.toArray(
      (T[]) Array.newInstance(type, queue.size())));

    writeHeader();

    for (int i = 0; i < records.size(); i++) {
      if (predicate.test(records.get(i))) {
        records.set(i, object);
        break;
      }
    }

    writeAll(records);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void delete(Predicate<T> predicate) throws IOException {
    Queue<T> queue = readAll();
    LinkedList<T> records = new LinkedList<>(queue.toArray(
      (T[]) Array.newInstance(type, queue.size())));

    writeHeader();

    for (int i = 0; i < records.size(); i++) {
      if (predicate.test(records.get(i))) {
        records.remove(i);
        break;
      }
    }

    writeAll(records);
  }

  @Override
  public Queue<T> findMany(Predicate<T> predicate) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(dbFile))) {
      Queue<T> queue = new Queue<>();

      var counter = 0;

      String line;
      while ((line = reader.readLine()) != null) {
        if (counter == 0) {
          counter++;
          continue;
        }

        var data = mapper.map(line);
        if (predicate.test(data)) {
          queue.enqueue(data);
        }
      }

      return queue;
    }
  }

  @Override
  public T find(Predicate<T> predicate) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(dbFile))) {

      var counter = 0;

      String line;
      while ((line = reader.readLine()) != null) {
        if (counter == 0) {
          counter++;
          continue;
        }

        var data = mapper.map(line);
        if (predicate.test(data)) {
          return data;
        }
      }
    }

    return null;
  }

  /**
   * Creates the necessary files for the CSV database context if they do not already exist.
   * If the database file does not exist, it attempts to create it. If the file is created
   * and is empty, it writes the header to the file.
   * If the last index file does not exist, it attempts to create it. If the file is created
   * and is empty, it writes "0" to the file.
   *
   * @throws IOException if an I/O error occurs while creating the files.
   * @throws CreateFileException if the files cannot be created.
   */
  private void createFiles() throws IOException {
    if (!this.dbFile.exists() && !this.dbFile.createNewFile()) {
      throw new CreateFileException(this.dbFile);
    } else if (this.dbFile.length() == 0) {
      writeHeader();
    }

    if (!this.lastIndexFile.exists() && !this.lastIndexFile.createNewFile()) {
      throw new CreateFileException(this.lastIndexFile);
    } else if (this.lastIndexFile.length() == 0) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.lastIndexFile))) {
        writer.write("0");
      }
    }
  }

  /**
   * Writes the header row to the CSV file.
   * 
   * This method retrieves the declared fields of the specified type and writes
   * the column names to the CSV file. The column names are determined by the
   * {@link CsvColumn} annotation present on the fields. Each column name is
   * followed by a delimiter. After writing all column names, a new line is added.
   * 
   * @throws IOException if an I/O error occurs while writing to the file.
   */
  private void writeHeader() throws IOException {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(dbFile))) {
      var fields = type.getDeclaredFields();
      for (var field : fields) {
        var annotation = field.getAnnotation(CsvColumn.class);
        if (annotation != null) {
          bw.write(annotation.columnName());
          bw.write(DELIMITER);
        }
      }
      bw.newLine();
    }
  }

  /**
   * Writes all records from the provided LinkedList to the CSV file.
   * Each record is unmapped using the mapper and written to the file.
   * A new line is added after each record.
   *
   * @param objects the LinkedList containing records to be written to the CSV file
   * @throws IOException if an I/O error occurs
   */
  private void writeAll(LinkedList<T> objects) throws IOException {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(dbFile, true))) {
      for (int i = 0; i < objects.size(); i++) {
        bw.write(mapper.unmap(objects.get(i)));
        bw.newLine();
      }
    }
  }
}
