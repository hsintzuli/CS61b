public class ArrayDeque<T> {
    private T[] items;
    private int length;
    private int size;
    private int nextFirst = 0;
    private int nextLast = 1;
    private double usageFactor = 0.25;
    private int resizeRatio = 2;
    private int basisCapacity = 16;

    /** Creates an empty array deque. */
    public ArrayDeque() {

        this(8);
    }

    /** Creates an empty array deque with a given length. */
    private ArrayDeque(int length) {
        items = (T[]) new Object[8];
        size = 0;
        this.length = length;
    }

    /** Resizes the underlying array to the targeting capacity. */
    private void resize(int targetCapacity) {
        T[] newItems = (T[]) new Object[targetCapacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[circulateIndex(nextFirst, i + 1)];
        }
        items = newItems;
        length = targetCapacity;

    }

    /** Circulate a new index which is an existing index plus or minus i. */
    private int circulateIndex(int index, int i) {
        index = index + i;
        if (index < 0) {
            index = length + index;
        } else if (index >= length) {
            index = index - length;
        }
        return index;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        if (size == items.length) {
            expandLength(resizeRatio);
        }
        items[nextFirst] = item;
        nextFirst = circulateIndex(nextFirst, -1);
        size++;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        if (size == items.length) {
            expandLength(resizeRatio);
        }
        items[nextLast] = item;
        nextLast = circulateIndex(nextLast, 1);
        size++;
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
        for (int i = 0; i < size; i++) {
            System.out.print(items[circulateIndex(nextFirst, i + 1)] + " ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst() {
        if (!isEmpty()) {
            nextFirst = circulateIndex(nextFirst, 1);
            T firstItem = items[nextFirst];
            items[nextFirst] = null;
            size = size - 1;
            if (checkLoitering()) {
                shrinkLength();
            }
            return firstItem;
        } else {
            return null;
        }
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public T removeLast() {
        if (!isEmpty()) {
            nextLast = circulateIndex(nextLast, -1);
            T lastItem = items[nextLast];
            items[nextLast] = null;
            size = size - 1;
            if (checkLoitering()) {
                shrinkLength();
            }

            return lastItem;
        } else {
            return null;
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index) {
        if (index >= size) {
            return null;
        } else {
            return items[circulateIndex(nextFirst, index + 1)];
        }
    }

    /** Expand the length of items by a particular expandFactor*/
    private void expandLength(int expandFactor) {
        resize(length * expandFactor);
        nextFirst = length - 1;
        nextLast = size;
    }

    /** Shrink the length of items to half*/
    private void shrinkLength() {
        resize(length / 2);
        nextFirst = length - 1;
        nextLast = size;
    }

    private boolean checkLoitering() {
        return ((length >= basisCapacity) && ((double) size / length < usageFactor));
    }
}
