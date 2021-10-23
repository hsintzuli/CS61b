public class LinkedListDeque<T> {
    private class Node {
        private Node prev;
        private T item;
        private Node next;

        public Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }
    private Node sentinel;
    private int size;

    /** Creates an empty LinkedListDeque. */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        Node nextItem = sentinel.next;
        sentinel.next =  new Node(sentinel, item, sentinel.next);
        nextItem.prev = sentinel.next;
        size = size + 1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        Node prevItem = sentinel.prev;
        sentinel.prev =  new Node(sentinel.prev, item, sentinel);
        prevItem.next = sentinel.prev;
        size = size + 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return (size == 0);
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     *  Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        int count = 0;
        Node pointer = sentinel.next;
        while (count < size) {
            System.out.print(pointer.item + " ");
            pointer = pointer.next;
            count++;
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst() {
        if (!isEmpty()) {
            Node firstItem = sentinel.next;
            sentinel.next = firstItem.next;
            sentinel.next.prev = sentinel;
            size--;
            return firstItem.item;
        } else {
            return null;
        }
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public T removeLast() {
        if (!isEmpty()) {
            Node lastItem = sentinel.prev;
            sentinel.prev = lastItem.prev;
            sentinel.prev.next = sentinel;
            size--;
            return lastItem.item;
        } else {
            return null;
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index) {
        Node pointer = sentinel.next;
        if (index >= size) {
            return null;
        }
        for (int i = 0; i < index; i++) {
            pointer = pointer.next;
        }
        return pointer.item;
    }

    /** Gets the item at the given index. Implement the same function of get but use recursive!
     */
    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        } else {
            return getRecursive(index, sentinel.next);
        }
    }

    private T getRecursive(int index, Node node) {
        if (index == 0) {
            return node.item;
        } else {
            return getRecursive(index - 1, node.next);
        }
    }
}
