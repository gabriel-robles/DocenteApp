package fateczl.util.sort;

/**
 * Enum representing different types of pivot selection strategies for quicksort.
 */
public enum PivotType {
    /**
     * Selects the first element as the pivot.
     */
    FIRST,

    /**
     * Selects the last element as the pivot.
     */
    LAST,

    /**
     * Selects the middle element as the pivot.
     */
    MIDDLE,

    /**
     * Selects the median of three elements as the pivot.
     */
    MIDDLE_OF_THREE,

    /**
     * Selects a random element as the pivot.
     */
    RANDOM
}
