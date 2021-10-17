/** An SLList is a list of integers, which hides the terrible truth
 * of the nakedness within. */
public class SLList<Blah> implements List61B<Blah>{
    private class StuffNode {
        public Blah item;
        public StuffNode next;

        public StuffNode(Blah i, StuffNode n) {
            item = i;
            next = n;
        }
    }

    /* The first item (if it exists) is at sentinel.next. */
    private StuffNode sentinel;
    private int size;

    /** Creates an empty SLList. */
    public SLList() {
        sentinel = new StuffNode(null, null);
        size = 0;
    }

    public SLList(Blah x) {
        sentinel = new StuffNode(null, null);
        sentinel.next = new StuffNode(x, null);
        size = 1;
    }

    @Override
    /** Adds x to the front of the list. */
    public void addFirst(Blah x) {
        sentinel.next = new StuffNode(x, sentinel.next);
        size = size + 1;
    }

    @Override
    /** Returns the first item in the list. */
    public Blah getFirst() {
        return sentinel.next.item;
    }

    @Override
    /** Adds x to the end of the list. */
    public void addLast(Blah x) {
        size = size + 1;

        StuffNode p = sentinel;

        /* Advance p to the end of the list. */
        while (p.next != null) {
            p = p.next;
        }

        p.next = new StuffNode(x, null);
    }

    @Override
    /** Returns the size of the list. */
    public int size() {
        return size;
    }

    /** Returns the back node of our list. */
    private StuffNode getLastNode() {
        StuffNode p = sentinel;

        /* Advance p to the end of the list. */
        while (p.next != null) {
            p = p.next;
        }
        return p;
    }

    @Override
    /** Returns last item */
    public Blah getLast() {
        StuffNode lastNode = getLastNode();
        return lastNode.item;
    }

    @Override
    /** Deletes and returns last item. */
    public Blah removeLast() {
        StuffNode lastNode = getLastNode();
        if (lastNode == sentinel) {
            return null;
        }

        StuffNode p = sentinel;

        while(p.next != lastNode){
            p = p.next;
        }
        p.next = null;
        return lastNode.item;
    }

    @Override
    /** Gets the positionth item of the list. */
    public Blah get(int position) {
        if (position == 0) {
            return getFirst();
        }

        StuffNode currentNode = sentinel.next.next;
        while (position > 1 && currentNode.next != null) {
            position = position - 1;
            currentNode = currentNode.next;
        }
        return currentNode.item;
    }

    /** Inserts item into given position.
     * Code from discussion #3 */
    public void insert(Blah item, int position) {
        size++;
        if (sentinel.next == null || position == 0) {
            addFirst(item);
            return;
        }

        StuffNode currentNode = sentinel.next;
        while (position > 1 && currentNode.next != null) {
            position = position - 1;
            currentNode = currentNode.next;
        }
        StuffNode newNode = new StuffNode(item, currentNode.next);
        currentNode.next = newNode;

    }

    /** TODO: Add a print method that overrides List61B's inefficient print method. */
    @Override
    public void print() {
        for (StuffNode p = sentinel.next; p != null; p = p.next) {
            System.out.print(p.item + " ");
        }
    }
}