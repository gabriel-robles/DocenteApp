package fateczl.util.sort;

/**
 * The QuickSort class provides a method to sort an array using the QuickSort algorithm.
 */
public class QuickSort {
  /**
   * Sorts an array of integers using the QuickSort algorithm.
   *
   * @param array the array to be sorted
   * @param pivotType the type of pivot to be used in the sorting process
   */
  public static void sort(int[] array, PivotType pivotType) {
    quickSort(array, 0, array.length-1, pivotType);
  }

  /**
   * Sorts a two-dimensional array of objects using the QuickSort algorithm.
   *
   * @param data the two-dimensional array to be sorted
   * @param column the column to be used as the sorting key
   * @param pivotType the type of pivot to be used in the sorting process
   */
  public static void sort(Object[][] data, int column, PivotType pivotType) {
    quickSort(data, 0, data.length-1, column, pivotType);
  }

  private static void quickSort(Object[][] data, int begin, int end, int column, PivotType pivotType) {
    if (begin < end) {
      int partitionIndex = partition(data, begin, end, column, pivotType);
 
      quickSort(data, begin, partitionIndex-1, column, pivotType);
      quickSort(data, partitionIndex+1, end, column, pivotType);
    }
  }

  private static void quickSort(int[] array, int begin, int end, PivotType pivotType) {
    if (begin < end) {
      int partitionIndex = partition(array, begin, end, pivotType);
 
      quickSort(array, begin, partitionIndex-1, pivotType);
      quickSort(array, partitionIndex+1, end, pivotType);
    }
  }

  private static int partition(Object[][] data, int begin, int end, int column, PivotType pivotType) {
    int pivotIndex = getPivotIndex(data, begin, end, column, pivotType);
    int pivot = (int) data[pivotIndex][column];

    int i = begin;
    int j = end;

    while (i < j) {
      if ((int) data[i][column] > pivot) {
          i++;
      } else if ((int) data[j][column] < pivot) {
          j--;
      } else {
        swap(data, i, j);

        if (i == pivotIndex) {
            pivotIndex = j;
        } else if (j == pivotIndex) {
            pivotIndex = i;
        }

        if (i != pivotIndex) {
            i++;
        } else if (j != pivotIndex) {
            j--;
        }
      }
    }

    return i;
  }

  private static int partition(int[] array, int begin, int end, PivotType pivotType) {
    int pivotIndex = getPivotIndex(array, begin, end, pivotType);
    int pivot = array[pivotIndex];

    int i = begin;
    int j = end;

    while (i < j) {
      if (array[i] < pivot) {
          i++;
      } else if (array[j] > pivot) {
          j--;
      } else {
        swap(array, i, j);

        if (i == pivotIndex) {
            pivotIndex = j;
        } else if (j == pivotIndex) {
            pivotIndex = i;
        }

        if (i != pivotIndex) {
            i++;
        } else if (j != pivotIndex) {
            j--;
        }
      }
    }

    return i;
  }

  private static void swap(Object[][] data, int i, int j) {
    Object[] temp = data[i];
    data[i] = data[j];
    data[j] = temp;
  }

  private static void swap(int[] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  private static int getPivotIndex(Object[][] data, int begin, int end, int column, PivotType pivotType) {
    switch (pivotType) {
      case FIRST:
        return begin;
      case LAST:
        return end;
      case MIDDLE:
        return (begin + end) / 2;
      case MIDDLE_OF_THREE:
        return getMiddleOfThreePivotIndex(data, begin, end, column);
      case RANDOM:
        return getRandomPivotIndex(begin, end);
      default:
        throw new IllegalArgumentException("Invalid pivot type: " + pivotType);
    }
  }

  private static int getPivotIndex(int[] array, int begin, int end, PivotType pivotType) {
    switch (pivotType) {
      case FIRST:
        return begin;
      case LAST:
        return end;
      case MIDDLE:
        return (begin + end) / 2;
      case MIDDLE_OF_THREE:
        return getMiddleOfThreePivotIndex(array, begin, end);
      case RANDOM:
        return getRandomPivotIndex(begin, end);
      default:
        throw new IllegalArgumentException("Invalid pivot type: " + pivotType);
    }
  }

  private static int getMiddleOfThreePivotIndex(Object[][] data, int begin, int end, int column) {
    int middle = (begin + end) / 2;

    if ((int) data[begin][column] > (int) data[middle][column]) {
      if ((int) data[middle][column] > (int) data[end][column]) {
          return middle;
      } else if ((int) data[begin][column] > (int) data[end][column]) {
          return end;
      } else {
          return begin;
      }
    } else {
      if ((int) data[begin][column] > (int) data[end][column]) {
          return begin;
      } else if ((int) data[middle][column] > (int) data[end][column]) {
          return end;
      } else {
          return middle;
      }
    }
  }

  private static int getMiddleOfThreePivotIndex(int[] array, int begin, int end) {
    int middle = (begin + end) / 2;

    if (array[begin] > array[middle]) {
      if (array[middle] > array[end]) {
          return middle;
      } else if (array[begin] > array[end]) {
          return end;
      } else {
          return begin;
      }
    } else {
      if (array[begin] > array[end]) {
          return begin;
      } else if (array[middle] > array[end]) {
          return end;
      } else {
          return middle;
      }
    }
  }

  private static int getRandomPivotIndex(int begin, int end) {
    return (int) (Math.random() * (end - begin + 1)) + begin;
  }
}
