import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringQueueWithOnePointer <Item> implements StringQueue <Item> {

    private class Node {
        Item item;
        Node next;
        Node(Item item) {
            this.item = item;
            this.next = null;
        }
    }

    private Node tail = null;
    private int size = 0;

    public boolean isEmpty() {
        return tail == null;
    }

    public void put(Item item) {
        if (isEmpty()) {
            tail = new Node(item);
            tail.next = tail;
        }
        else {
            Node newTail = new Node(item);
            newTail.next = tail.next;
            tail.next = newTail;
            tail = newTail;
        }
        size++;
    }

    public Item get() throws NoSuchElementException {
        try {
            Item item;
            if (tail.next == tail) { // If queue size is one
                item = tail.item;
                tail = null;
            }
            else {
                item = tail.next.item;
                tail.next = tail.next.next;
            }
            size--;
            return item;
        } catch (Exception e) {
            System.out.println();
            throw new NoSuchElementException();
        }
    }

    public Item peek() throws NoSuchElementException {
        try {
            return tail.next.item;
        } catch (Exception e) {
            System.out.println();
            throw new NoSuchElementException();
        }
    }

    public void printQueue(PrintStream stream) {
        if (tail == null)
            return;
        for (Node traverser = tail.next; traverser != tail; traverser = traverser.next) {
            stream.print(traverser.item);
            stream.print(" ");
        }
        stream.print(tail.item);
        stream.print(" ");
    }

    public int size() { return size; }

}
