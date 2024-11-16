package fateczl.csvdb;

import fateczl.csvdb.annotations.CsvColumn;
import java.lang.reflect.Field;

public class CsvMapperImpl<T> implements CsvMapper<T> {
  /**
   * The class type that this CsvMapperImpl will handle.
   */
  private final Class<T> type;

  /**
   * Constructs a new CsvMapperImpl instance for the specified type.
   *
   * @param type the class type that this CsvMapperImpl will handle
   */
  public CsvMapperImpl(Class<T> type) {
    this.type = type;
  }

  @Override
  public T map(String line) {
    try {
      T instance = type.getDeclaredConstructor().newInstance();
      String[] values = line.split(CsvContextImpl.DELIMITER);
      Field[] fields = type.getDeclaredFields();

      for (Field field : fields) {
        var annotation = field.getAnnotation(fateczl.csvdb.annotations.CsvColumn.class);
        if (annotation != null) {
          field.setAccessible(true);
          int columnIndex = Integer.parseInt(annotation.columnIndex());
          if (columnIndex != -1 && columnIndex < values.length) {
            var value = convertValue(field.getType(), values[columnIndex]);
            field.set(instance, value);
          }
        }
      }

      return instance;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }

  @Override
  public String unmap(Integer id, T object) {
    try {
      Field[] fields = type.getDeclaredFields();
      String[] values = new String[fields.length];

      for (var field : fields) {
        var annotation = field.getAnnotation(CsvColumn.class);
        if (annotation != null) {
          field.setAccessible(true);
          var value = field.get(object);
          var columnIndex = Integer.parseInt(annotation.columnIndex());

          if (annotation.primaryKey()) {
            values[columnIndex] = id.toString();
          } else if (value != null) {
            values[columnIndex] = value.toString();
          }
        }
      }

      return String.join(CsvContextImpl.DELIMITER, values);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String unmap(T object) {
    try {
      Field[] fields = type.getDeclaredFields();

      for (Field field : fields) {
        var annotation = field.getAnnotation(CsvColumn.class);

        if (annotation != null && annotation.primaryKey()) {
          field.setAccessible(true);
          var id = field.get(object);
          return unmap((Integer) id, object);
        }      
      }

      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Converts a string value to the specified type.
   *
   * @param type  the class type to convert the value to
   * @param value the string value to be converted
   * @return the converted value as an Object of the specified type
   * @throws NumberFormatException if the string cannot be converted to the specified numeric type
   */
  private Object convertValue(Class<?> type, String value) {
    if (type == int.class || type == Integer.class) {
        return Integer.parseInt(value);
    } else if (type == long.class || type == Long.class) {
        return Long.parseLong(value);
    } else if (type == double.class || type == Double.class) {
        return Double.parseDouble(value);
    } else if (type == boolean.class || type == Boolean.class) {
        return Boolean.parseBoolean(value);
    }
    return value;
  }
}
