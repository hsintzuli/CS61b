public class LinkedListDeque<T> implements Deque<T> {
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

    @Override
    public void addFirst(T item) {
        Node nextItem = sentinel.next;
        sentinel.next =  new Node(sentinel, item, sentinel.next);
        nextItem.prev = sentinel.next;
        size = size + 1;
    }

    @Override
    public void addLast(T item) {
        Node prevItem = sentinel.prev;
        sentinel.prev =  new Node(sentinel.prev, item, sentinel);
        prevItem.next = sentinel.prev;
        size = size + 1;
    }

    @Override
    public boolean isEmpty() {

        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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
