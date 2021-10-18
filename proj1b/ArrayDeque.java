public class ArrayDeque<T> implements Deque<T> {
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

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            expandLength(2);
        }
        items[nextFirst] = item;
        nextFirst = circulateIndex(nextFirst, -1);
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            expandLength(2);
        }
        items[nextLast] = item;
        nextLast = circulateIndex(nextLast, 1);
        size++;
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
        for (int i = 0; i < size; i++) {
            System.out.print(items[circulateIndex(nextFirst, i + 1)] + " ");
        }
        System.out.println();
    }

    @Override
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

    @Override
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

    @Override
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
        length = length * expandFactor;
        nextFirst = length - 1;
        nextLast = size;
    }

    /** Shrink the length of items to half*/
    private void shrinkLength() {
        resize(length / 2);
        length = length / 2;
        nextFirst = length - 1;
        nextLast = size;
    }

    private boolean checkLoitering() {
        return ((length >= basisCapacity) && ((double) size / length < usageFactor));
    }
}
