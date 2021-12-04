package lab9;
import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Hsin-Tzu Li
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }

        if (p == null) {
            return null;
        }

        int comparator = key.compareTo(p.key);
        if (comparator > 0) {
            return getHelper(key, p.right);
        } else if (comparator < 0) {
            return getHelper(key, p.left);
        } else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            return new Node(key, value);
        }

        int comparator = key.compareTo(p.key);
        if (comparator > 0) {
            p.right = putHelper(key, value, p.right);
        } else if (comparator < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }

        root = putHelper(key, value, root);
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
        Queue<Node> queue = new LinkedList<>();
        Set<K> keys = new HashSet<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node x = queue.remove();
            if (x == null) {
                continue;
            }
            keys.add(x.key);
            queue.add(x.left);
            queue.add(x.right);
        }
        return keys;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("calls delete() with a null key");
        }
        V value = get(key);
        if (value == null) {
            return null;
        }
        root = removeHelper(root, key);
        return value;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("calls delete() with a null key");
        }
        V getValue = get(key);
        if (value.equals(getValue)) {
            root = removeHelper(root, key);
            return value;
        }
        return null;
    }

    private Node removeHelper(Node p, K key) {
        if (p == null) {
            return null;
        }
        int comparator = key.compareTo(p.key);
        if (comparator > 0) {
            p.right = removeHelper(p.right, key);
        } else if (comparator < 0) {
            p.left = removeHelper(p.left, key);
        } else {
            if (p.right == null) {
                return p.left;
            }
            if (p.left == null) {
                return p.right;
            }
            Node t = p;
            p = min(t.right);
            p.right = deleteMin(t.right);
            p.left = t.left;
        }

        return p;
    }

    /** Returns the smallest key in the BST.
     **/
    private K min() {
        if (size() == 0) {
            throw new NoSuchElementException("calls min() with empty BST");
        }
        return min(root).key;
    }

    /** Returns the smallest Node in the BST whose root is p.
     **/
    private Node min(Node p) {
        if (p.left == null) {
            return p;
        } else {
            return min(p.left);
        }
    }

    private void deleteMin() {
        if (size() == 0) {
            throw new NoSuchElementException("Underflow");
        }
        root = deleteMin(root);
    }

    private Node deleteMin(Node p) {
        if (p.left == null) {
            return p.right;
        }
        p.left = deleteMin(p.left);
        size -= 1;
        return p;
    }


    @Override
    public Iterator<K> iterator() {
        return new BSTMapIter();
    }

    /** An iterator that iterates over the keys of the map. */
    private class BSTMapIter implements Iterator<K> {
        private Queue<Node> queue;
        private Queue<K> keys;

        private BSTMapIter() {
            queue = new LinkedList<>();
            keys = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node x = queue.remove();
                if (x == null) {
                    continue;
                }
                keys.add(x.key);
                queue.add(x.left);
                queue.add(x.right);
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
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        bstmap.put("elephant", 300);

        // check whether keySet() works
        System.out.println(bstmap.keySet());

        // check whether remove() works
        bstmap.remove("zebra");
        bstmap.remove("elephant", 300);

        // check whether iterator() works
        Iterator iter = bstmap.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}
