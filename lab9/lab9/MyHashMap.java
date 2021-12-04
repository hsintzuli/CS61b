package lab9;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Hsin-Tzu Li
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    public MyHashMap(int size) {
        buckets = new ArrayMap[size];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        int index = hash(key);
        return buckets[index].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        if (loadFactor() >= MAX_LF) {
            resize(buckets.length * 2);
        }
        int index = hash(key);
        if (!buckets[index].containsKey(key)) {
            size += 1;
        }
        buckets[index].put(key, value);
    }

    private void resize(int targetSize) {
        MyHashMap<K, V> temp = new MyHashMap<>(targetSize);
        for (int i = 0; i < buckets.length; i++) {
            for (K key : buckets[i].keySet()) {
                temp.put(key, buckets[i].get(key));
            }
        }
        this.buckets = temp.buckets;
        this.size = temp.size();
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (int i = 0; i < buckets.length; i++) {
            for (K key : buckets[i].keySet()) {
                keySet.add(key);
            }
        }

        return keySet;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        V value = null;
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        int index= hash(key);
        if (buckets[index].containsKey(key)) {
            value = buckets[index].remove(key);
            size -= 1;
        }
        return value;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        V thisValue = null;
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        int index= hash(key);
        if (buckets[index].containsKey(key)) {
            if (value.equals(buckets[index].get(key))) {
                value = buckets[index].remove(key);
                size -= 1;
            }
        }
        return value;
    }

    @Override
    public Iterator<K> iterator() {
        return new HashMapIter();
    }

    /** An iterator that iterates over the keys of the map. */
    private class HashMapIter implements Iterator<K> {
        private Queue<K> keys;

        private HashMapIter() {
            keys = new LinkedList<>();
            for (int i = 0; i < buckets.length; i++) {
                for (K key : buckets[i].keySet()) {
                    keys.add(key);
                }
            }
        }

        public boolean hasNext() {
            return !keys.isEmpty();
        }

        public K next() {
            return keys.remove();
        }
    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> hashmap = new MyHashMap<>();
        hashmap.put("hello", 5);
        hashmap.put("cat", 10);
        hashmap.put("fish", 22);
        hashmap.put("zebra", 90);
        hashmap.put("elephant", 300);

        // check whether keySet() works
        System.out.println(hashmap.keySet());

        // check whether remove() works
        hashmap.remove("zebra");
        hashmap.remove("elephant", 300);

        // check whether iterator() works
        Iterator iter = hashmap.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}
