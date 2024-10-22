package fateczl.csvdb;

import fateczl.util.LinkedList;
import fateczl.util.Queue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * CsvContextImpl is an implementation of the CsvContext interface that provides
 * methods to interact with a CSV file as a database. It supports reading, inserting,
 * updating, and deleting records in the CSV file.
 *
 * @param <T> the type of objects that this context will handle.
 */
public class CsvContextImpl<T> implements CsvContext<T> {
  public static final String DELIMITER = ";";

  private final Class<T> type;
  private final File dbFile;
  private final File lastIndexFile;
  private final CsvMapper<T> mapper;

  /**
   * Constructs a new CsvContextImpl instance with the specified filename and mapper.
   *
   * @param filename the name of the CSV file to be used. Must not be null.
   * @param mapper the CsvMapper instance used to map CSV data to objects.
   * @throws IllegalArgumentException if the filename is null or if the file cannot be created.
   */
  public CsvContextImpl(String filename, CsvMapper<T> mapper, Class<T> type) {
    if (type == null) {
      throw new IllegalArgumentException("Type cannot be null");
    }

    this.type = type;

    if (filename == null) {
      throw new IllegalArgumentException("File path cannot be null");
    }

    var currentDir = System.getProperty("user.dir");

    this.dbFile = new File(currentDir, filename);
    this.lastIndexFile = new File(currentDir, filename.replace(".csv", "last.txt"));

    try {
      if (!dbFile.exists() && !dbFile.createNewFile()) {
        throw new IOException("Failed to create file");
      }
      if (!lastIndexFile.exists() && !lastIndexFile.createNewFile()) {
        throw new IOException("Failed to create file");
      } else if (lastIndexFile.length() == 0) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(lastIndexFile))) {
          writer.write("0");
        }
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to create file " + filename);
    }

    this.mapper = mapper;
  }

  @Override
  public Queue<T> readAll() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(dbFile))) {
      Queue<T> queue = new Queue<>();
      String line;
      while ((line = reader.readLine()) != null) {
        queue.enqueue(mapper.map(line));
      }
      return queue;
    }
  }

  @Override
  public void insert(T rec) throws IOException {
    Integer lastIndex;

    try (BufferedReader reader = new BufferedReader(new FileReader(lastIndexFile))) {
      lastIndex = Integer.parseInt(reader.readLine());
    }

    lastIndex++;

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(lastIndexFile))) {
      writer.write(String.valueOf(lastIndex));
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(dbFile, true))) {
      writer.write(mapper.unmap(lastIndex, rec));
      writer.newLine();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void update(T rec) throws IOException {
    Queue<T> queue = readAll();
    LinkedList<T> records = new LinkedList<>(queue.toArray((T[]) Array.newInstance(type, 0)));

    for (int i = 0; i < records.size(); i++) {
      if (mapper.equals(records.get(i), rec)) {
        records.set(i, rec);
        break;
      }
    }

    writeAll(records);
    
  }

  @SuppressWarnings("unchecked")
  @Override
  public void delete(T rec) throws IOException {
    Queue<T> queue = readAll();
    LinkedList<T> records = new LinkedList<>(queue.toArray((T[]) Array.newInstance(type, 0)));

    for (int i = 0; i < records.size(); i++) {
      if (mapper.equals(records.get(i), rec)) {
        records.remove(i);
        break;
      }
    }

    writeAll(records);
  }

  private void writeAll(LinkedList<T> rec) throws IOException {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(dbFile))) {
      for (int i = 0; i < rec.size(); i++) {
        bw.write(mapper.unmap(rec.get(i)));
        bw.newLine();
      }
    }
  }
}
