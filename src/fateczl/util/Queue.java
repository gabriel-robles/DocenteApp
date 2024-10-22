package fateczl.util;

import java.lang.reflect.Array;

/**
 * A generic queue implementation.
 *
 * @param <T> the type of elements held in this queue
 */
public class Queue<T> {
  /**
   * The number of elements in the queue.
   */
  private int size = 0;


  private Node<T> first;
  private Node<T> last;

  /**
   * Adds an element to the end of the queue.
   *
   * @param data the element to add
   */
  public void enqueue(T data) {
    Node<T> l = last;
    Node<T> newNode = new Node<>(data, null);
    last = newNode;
    if (l == null) {
      first = newNode;
    } else {
      l.next = newNode;
    }
    size++;
  }

  /**
   * Removes the element at the front of the queue.
   *
   * @return the element that was removed from the queue
   * @throws IllegalStateException if the queue is empty
   */
  public T dequeue() {
    if (isEmpty()) {
      throw new IllegalStateException("Queue is empty");
    }

    final T data = first.data;
    first = first.next;
    size--;

    if (isEmpty()) {
      last = null;
    }

    return data;
  }

  /**
   * Returns the number of elements in the queue.
   *
   * @return the number of elements in the queue
   */
  public int size() {
    return size;
  }

  /**
   * Checks if the queue is empty.
   *
   * @return true if the queue is empty, false otherwise.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Converts the queue to an array.
   *
   * @return an array containing all of the elements in this queue in proper
   *         sequence.
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
   * Converts the elements of the queue into an array.
   *
   * @param array the array into which the elements of the queue are to be stored,
   *              if it is big enough; otherwise, a new array of the same runtime
   *              type is allocated for this purpose.
   * @return an array containing all of the elements in this queue
   * @throws ArrayStoreException  if the runtime type of the specified array is
   *                              not
   *                              a supertype of the runtime type of every element
   *                              in this queue
   * @throws NullPointerException if the specified array is null
   */
  @SuppressWarnings("unchecked")
  public T[] toArray(T[] array) {
    if (array == null) {
      throw new NullPointerException("The specified array is null");
    }

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
   * A generic node class used in the Queue implementation.
   *
   * @param <T> the type of the data stored in the node
   */
  private static class Node<T> {
    T data;
    Node<T> next;

    /**
     * Constructs a new Node with the specified data and a reference to the next
     * node.
     *
     * @param data the data to be stored in this node
     * @param next the reference to the next node in the queue
     */
    Node(T data, Node<T> next) {
      this.data = data;
    }
  }
}
