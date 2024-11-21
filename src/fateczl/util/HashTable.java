package fateczl.util;

import java.util.Objects;

/**
 * A simple implementation of a hash table data structure that maps keys to values.
 * This implementation uses separate chaining for collision resolution.
 *
 * @param <K> the type of keys maintained by this hash table
 * @param <V> the type of mapped values
 */
public class HashTable<K, V> {
  /**
   * The default initial capacity of the hash table.
   */
  private static final int DEFAULT_CAPACITY = 16;

  /**
   * The load factor used to determine when to resize the hash table.
   */
  private static final float LOAD_FACTOR = 0.75f;

  /**
   * The array of nodes representing the hash table.
   */
  private Node<K, V>[] table;

  /**
   * The number of key-value pairs currently stored in the hash table.
   */
  private int size;

  /**
   * The threshold at which the hash table should be resized.
   */
  private int threshold;

  /**
   * Constructs a new hash table with the default initial capacity and load factor.
   */
  @SuppressWarnings("unchecked")
  public HashTable() {
    table = new Node[DEFAULT_CAPACITY];
    threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
  }

  /**
   * Associates the specified value with the specified key in this hash table.
   * If the hash table previously contained a mapping for the key, the old value is replaced.
   *
   * @param key   the key with which the specified value is to be associated
   * @param value the value to be associated with the specified key
   */
  public void put(K key, V value) {
    if (key == null) {
      throw new IllegalArgumentException("Key cannot be null");
    }

    int hash = hash(key);
    int index = hash & (table.length - 1);

    Node<K, V> node = table[index];
    if (node == null) {
      table[index] = new Node<>(hash, key, value, null);
      size++;
    } else {
      while (node != null) {
        if (node.hash == hash && Objects.equals(node.key, key)) {
          node.value = value;
          return;
        }
        if (node.next == null) {
          node.next = new Node<>(hash, key, value, null);
          size++;
          break;
        }
        node = node.next;
      }
    }

    if (size >= threshold) {
      resize();
    }
  }

  /**
   * Gets the value associated with the specified key in this hash table.
   *
   * @param key the key whose associated value is to be returned
   * @return the value to which the specified key is mapped, or {@code null} if this hash table contains no mapping for the key
   */
  public V get(K key) {
    if (key == null) {
      throw new IllegalArgumentException("Key cannot be null");
    }

    int hash = hash(key);
    int index = hash & (table.length - 1);

    Node<K, V> node = table[index];
    while (node != null) {
      if (node.hash == hash && Objects.equals(node.key, key)) {
        return node.value;
      }
      node = node.next;
    }

    return null;
  }

  /**
   * Removes the mapping for the specified key from this hash table if present.
   *
   * @param key the key whose mapping is to be removed from the hash table
   * @return the previous value associated with the specified key, or {@code null} if there was no mapping for the key
   */
  public V remove(K key) {
    if (key == null) {
      throw new IllegalArgumentException("Key cannot be null");
    }

    int hash = hash(key);
    int index = hash & (table.length - 1);

    Node<K, V> node = table[index];
    Node<K, V> prev = null;
    while (node != null) {
      if (node.hash == hash && Objects.equals(node.key, key)) {
        if (prev == null) {
          table[index] = node.next;
        } else {
          prev.next = node.next;
        }
        size--;
        return node.value;
      }
      prev = node;
      node = node.next;
    }

    return null;
  }

  /**
    * Returns an array containing all of the key-value mappings in this hash table.
   */
  public Entry<K, V>[] toArray() {
    @SuppressWarnings("unchecked")
    Entry<K, V>[] array = new Entry[size];
    int index = 0;
    for (Node<K, V> node : table) {
        while (node != null) {
            array[index++] = new Entry<>(node.key, node.value);
            node = node.next;
        }
    }
    
    return array;
  }

  /**
   * Returns {@code true} if this hash table contains a mapping for the specified key.
   *
   * @param key the key whose presence in this hash table is to be tested
   * @return {@code true} if this hash table contains a mapping for the specified key, {@code false} otherwise
   */
  public boolean containsKey(K key) {
    return get(key) != null;
  }

  /**
   * Returns the number of key-value mappings in this hash table.
   *
   * @return the number of key-value mappings in this hash table
   */
  public int size() {
    return size;
  }

  /**
   * Computes the hash code for the given key.
   * This method uses a combination of the key's hash code and a right shift
   * of the hash code to distribute the hash values more uniformly.
   *
   * @param key the key for which the hash code is to be computed
   * @return the computed hash code for the given key
   */
  private int hash(K key) {
    return key.hashCode() ^ (key.hashCode() >>> 16);
  }

  /**
   * Resizes the hash table when the number of elements exceeds the threshold.
   * This method doubles the capacity of the table and rehashes all the existing
   * entries to the new table.
   *
   * <p>It creates a new table with double the capacity of the old table, then
   * iterates through each node in the old table, rehashing and placing each node
   * into the new table. The threshold is also updated based on the new capacity
   * and load factor.</p>
   *
   * @throws NullPointerException if the table is null
   */
  @SuppressWarnings("unchecked")
  private void resize() {
    Node<K, V>[] oldTable = table;
    int newCapacity = oldTable.length * 2;
    Node<K, V>[] newTable = new Node[newCapacity];
    threshold = (int) (newCapacity * LOAD_FACTOR);

    for (Node<K, V> node : oldTable) {
      while (node != null) {
        Node<K, V> next = node.next;
        int index = node.hash & (newCapacity - 1);
        node.next = newTable[index];
        newTable[index] = node;
        node = next;
      }
    }

    table = newTable;
  }

  /**
   * A node in the hash table, representing a key-value pair.
   *
   * @param <K> the type of keys maintained by this node
   * @param <V> the type of mapped values
   */
  private static class Node<K, V> {
    /**
     * The hash value for the current entry in the hash table.
     * This value is typically computed based on the key and is used to determine
     * the index at which the entry should be stored in the hash table.
     */
    final int hash;

    /**
     * The key associated with this entry in the hash table.
     * This key is used to identify and access the corresponding value.
     */
    final K key;

    /**
     * The value associated with a specific key in the hash table.
     */
    V value;

    /**
     * The next node in the linked list for this hash table entry.
     */
    Node<K, V> next;

    /**
     * Constructs a new Node with the specified hash, key, value, and next node.
     *
     * @param hash  the hash code of the key
     * @param key   the key associated with this node
     * @param value the value associated with this node
     * @param next  the next node in the chain
     */
    Node(int hash, K key, V value, Node<K, V> next) {
      this.hash = hash;
      this.key = key;
      this.value = value;
      this.next = next;
    }
  }
}
