import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringQueueImpl <Item> implements StringQueue <Item> {

    private class Node {
        Item item;
        Node next;
        Node(Item item) {
            this.item = item;
            this.next = null;
        }
    }

    private Node head = null, tail = null;
    private int size = 0;

    public boolean isEmpty() {
        return head == null;
    }

    public void put(Item item) {
        if (isEmpty()) {
            tail = new Node(item);
            head = tail;
        }
        else {
            Node oldTail = tail;
            tail = new Node(item);
            oldTail.next = tail;
        }
        size++;
    }

    public Item get() throws NoSuchElementException {
        try {
            Item item = head.item;
            head = head.next;
            // If head is null, make tail also null to let garbage collector collect the last node
            if (isEmpty()) tail = null;
            size--;
            return item;
        } catch (Exception e) {
            System.out.println();
            throw new NoSuchElementException();
        }
    }

    public Item peek() throws NoSuchElementException {
        try {
            Item item = head.item;
            return item;
        } catch (Exception e) {
            System.out.println();
            throw new NoSuchElementException();
        }
    }

    public void printQueue(PrintStream stream) {
        for (Node traverser = head; traverser != null; traverser = traverser.next) {
            stream.print(traverser.item);
            stream.print(" ");
        }
    }

    public int size() { return size; }

}
