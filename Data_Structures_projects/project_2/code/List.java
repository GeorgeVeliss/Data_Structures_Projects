// A simple List class that implements only the capabilities necessary for the assignment (practically a stack)
public class List<T> {

    // Node class, necessary for implementing a linked-list
    private static class Node<T> {
        T data;
        Node<T> next = null;
        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head = null;
    private Node<T> tail = null;

    public boolean isEmpty() {
        return head == null;
    }

    public void insertAtFront(T data) {
        Node<T> n = new Node<>(data);

        if (isEmpty()) {
            head = n;
            tail = n;
        } else {
            n.next = head;
            head = n;
        }
    }

    public T removeFromFront() {
        if (isEmpty())
            return null;

        T front = head.data;

        if (head == tail)
            head = tail = null;
        else
            head = head.next;

        return front;
    }

}
