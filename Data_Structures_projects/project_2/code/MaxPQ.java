public class MaxPQ<T extends Comparable<T>> {

    private T[] heap = (T[]) new Comparable[2];
    private int size = 0;

    public void insert(T item) {
        if (size == heap.length-1)
            resize(heap.length*2);
        heap[++size] = item;
        swim(size);
    }

    public T getmax() {
        if (size == 0)
            return null;
        T max = heap[1];
        heap[1] = heap[size];
        size--;
        if (size == heap.length/4)
            resize(heap.length/2);
        sink(1);
        return max;
    }

    public T peekmax() {
        if (size == 0)
            return null;
        return heap[1];
    }

    public int getPQSize() {
        return size;
    }

    private void swim(int index) {
        while (index > 1 && less(index/2, index)) {
                swap(index, index / 2);
                index = index / 2;
            }
    }

    private void sink(int index) {
        while (index*2 <= size) {
            int maxChild = index*2;
            if (maxChild+1 <= size && less(maxChild, maxChild+1))
                maxChild++;
            if (!less(index, maxChild))
                break;
            swap(index, maxChild);
            index = maxChild;
        }
    }

    private void swap(int index1, int index2) {
        T temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    private void resize(int newLength) {
        T[] newHeap = (T[]) new Comparable[newLength];

        for (int i = 0; i < size+1; i++)
            newHeap[i] = heap[i];

        heap = newHeap;
    }

    private boolean less(int index1, int index2) {
        return heap[index1].compareTo(heap[index2]) < 0;
    }

}
