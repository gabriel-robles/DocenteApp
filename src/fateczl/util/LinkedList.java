package fateczl.util;

import java.lang.reflect.Array;
import java.util.Objects;

/**
 * A doubly linked list implementation.
 *
 * @param <T> the type of elements held in this list
 */
public class LinkedList<T> {
  private static final String OUT_OF_BOUNDS_MESSAGE = "Index is out of range";

  /**
   * The number of elements in the linked list.
   */
  private int size = 0;

  /**
   * The first node in the linked list.
   */
  private Node<T> first;

  /**
   * The last node in the linked list.
   */
  private Node<T> last;

  /**
   * Constructs an empty LinkedList.
   */
  public LinkedList() {
  }

  /**
   * Constructs a LinkedList from an array of elements.
   *
   * @param array the array of elements to be added to the LinkedList
   */
  public LinkedList(T[] array) {
    for (T data : array) {
      addLast(data);
    }
  }

  /**
   * Retrieves the element at the specified position in this linked list.
   *
   * @param index the index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range
   */
  public T get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(OUT_OF_BOUNDS_MESSAGE);
    }

    return getNode(index).data;
  }

  /**
   * Replaces the element at the specified
   * position in this list with the specified element.
   *
   * @param index the index of the element to replace
   * @param data  the element to be stored at the specified position
   * @throws IndexOutOfBoundsException if the index is out of range
   */
  public void set(int index, T data) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(OUT_OF_BOUNDS_MESSAGE);
    }

    getNode(index).data = data;
  }

  /**
   * Checks if the linked list contains a specific element.
   *
   * @param data the element to be checked for presence in the linked list
   * @return true if the element is found in the linked list, false otherwise
   */
  public boolean contains(T data) {
    if (data == null) {
      throw new IllegalArgumentException("Data cannot be null");
    }

    Node<T> node = first;
    while (node != null) {
      if (Objects.equals(node.data, data)) {
        return true;
      }
      node = node.next;
    }

    return false;
  }

  /**
   * Checks if the linked list is empty.
   *
   * @return true if the linked list contains no elements, false otherwise.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Returns the number of elements in the linked list.
   *
   * @return the size of the linked list
   */
  public int size() {
    return size;
  }

  /**
   * Removes all elements from the linked list, effectively clearing it.
   */
  public void clear() {
    first = null;
    last = null;
    size = 0;
  }

  /**
   * Returns an array containing all of the elements
   * in this linked list in proper sequence.
   *
   * @return an array containing all of the elements
   *         in this linked list in proper sequence
   */
  public Object[] toArray() {
    Object[] array = new Object[size];
    Node<T> node = first;
    for (int i = 0; i < size; i++) {
      array[i] = node.data;
      node = node.next;
    }

    return array;
  }

  /**
   * Converts the linked list to an array of the specified type.
   *
   * @param array the array into which the elements of the list are
   *              to be stored, if it is big enough; otherwise, a new array
   *              of the same runtime type is allocated for this purpose.
   * @return an array containing all of the elements in this list
   * @throws ArrayStoreException  if the runtime type of the specified array
   *                              is not a supertype of the runtime type of
   *                              every element in this list
   * @throws NullPointerException if the specified array is null
   */
  @SuppressWarnings("unchecked")
  public T[] toArray(T[] array) {
    if (array.length < size) {
      array = (T[]) Array.newInstance(
              array.getClass().getComponentType(), size);
    }

    Node<T> node = first;
    for (int i = 0; i < size; i++) {
      array[i] = node.data;
      node = node.next;
    }

    if (array.length > size) {
      array[size] = null;
    }

    return array;
  }

  /**
   * Adds an element to the linked list at the specified index.
   *
   * @param data  the element to be added to the list
   * @param index the position at which the element is to be inserted
   * @throws IndexOutOfBoundsException if the index is out of range
   */
  public void add(T data, int index) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException(OUT_OF_BOUNDS_MESSAGE);
    }

    if (index == size) {
      addLast(data);
    } else if (index == 0) {
      addFirst(data);
    } else {
      Node<T> node = getNode(index);
      Node<T> newNode = new Node<>(node.previous, data, node);
      node.previous.next = newNode;
      node.previous = newNode;
      size++;
    }
  }

  /**
   * Inserts the specified element at the beginning of this linked list.
   *
   * @param data the element to add
   */
  public void addFirst(T data) {
    Node<T> f = first;
    Node<T> newNode = new Node<>(null, data, f);
    first = newNode;
    if (f == null) {
      last = newNode;
    } else {
      f.previous = newNode;
    }
    size++;
  }

  /**
   * Adds the specified element to the end of the linked list.
   *
   * @param data the element to be added to the end of the list
   */
  public void addLast(T data) {
    Node<T> l = last;
    Node<T> newNode = new Node<>(l, data, null);
    last = newNode;
    if (l == null) {
      first = newNode;
    } else {
      l.next = newNode;
    }
    size++;
  }

  /**
   * Removes the element at the specified position in this linked list.
   *
   * @param index the index of the element to be removed
   * @throws IndexOutOfBoundsException if the index is out of range
   */
  public void remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(OUT_OF_BOUNDS_MESSAGE);
    }

    if (index == 0) {
      removeFirst();
    } else if (index == size - 1) {
      removeLast();
    } else {
      Node<T> node = getNode(index);
      Node<T> previous = node.previous;
      Node<T> next = node.next;
      previous.next = next;
      next.previous = previous;
      node.data = null;
      node.previous = null;
      node.next = null;
      size--;
    }
  }

  /**
   * Removes the first element from the linked list.
   *
   * @throws IndexOutOfBoundsException if the list is empty
   */
  public void removeFirst() {
    if (size == 0) {
      throw new IndexOutOfBoundsException("List is empty");
    }

    Node<T> f = first;
    Node<T> next = f.next;
    f.data = null;
    f.next = null;
    first = next;
    if (next == null) {
      last = null;
    } else {
      next.previous = null;
    }
    size--;
  }

  /**
   * Removes the last element from the linked list.
   *
   * @throws IndexOutOfBoundsException if the list is empty.
   */
  public void removeLast() {
    if (size == 0) {
      throw new IndexOutOfBoundsException("List is empty");
    }

    Node<T> l = last;
    Node<T> previous = l.previous;
    l.data = null;
    l.previous = null;
    last = previous;
    if (previous == null) {
      first = null;
    } else {
      previous.next = null;
    }
    size--;
  }

  /**
   * Retrieves the node at the specified index in the linked list.
   *
   * @param index the position of the node to retrieve
   * @return the node at the specified index
   */
  private Node<T> getNode(int index) {
    Node<T> node;
    if (index < size / 2) {
      node = first;
      for (int i = 0; i < index; i++) {
        node = node.next;
      }
    } else {
      node = last;
      for (int i = size - 1; i > index; i--) {
        node = node.previous;
      }
    }

    return node;
  }

  /**
   * A node in a doubly linked list.
   *
   * @param <T> the type of the element stored in the node
   * @see LinkedList
   */
  private static class Node<T> {
    /**
     * The data stored in this node.
     */
    private T data;

    /**
     * The previous node in the linked list.
     */
    private Node<T> previous;

    /**
     * The next node in the linked list.
     */
    private Node<T> next;

    /**
     * Constructs a new Node with the specified
     * previous node, data, and next node.
     *
     * @param prev the previous node in the linked list
     * @param data the data to be stored in this node
     * @param next the next node in the linked list
     */
    Node(final Node<T> previous, final T data, final Node<T> next) {
      this.previous = previous;
      this.data = data;
      this.next = next;
    }
  }
}
