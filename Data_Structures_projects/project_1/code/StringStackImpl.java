import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringStackImpl <Item> implements StringStack <Item> {

    private class Node {
        Item item;
        Node next;
        Node(Item item) {
            this.item = item;
            this.next = null;
        }
    }

    private Node head = null;
    private int size = 0;

    public boolean isEmpty() {
        return head == null;
    }

    public void push(Item item) {
        Node oldHead = head;
        head = new Node(item);
        head.next = oldHead;
        size++;
    }

    public Item pop() throws NoSuchElementException{
        try {
            Item item = head.item;
            head = head.next;
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

    public void printStack(PrintStream stream) {
        for (Node traverser = head; traverser != null; traverser = traverser.next) {
            stream.print(traverser.item);
            stream.print(" ");
        }
    }

    public int size() { return size; }

}
