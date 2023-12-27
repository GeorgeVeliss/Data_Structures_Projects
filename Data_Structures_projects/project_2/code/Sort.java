public class Sort {

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {

        for (int i = lo; i <= hi; i++)
            aux[i] = a[i];

        int i = lo, j = mid+1;

        for (int k = lo; k <= hi; k++) {
            if (i > mid)
                a[k] = aux[j++];
            else if (j > hi)
                a[k] = aux[i++];
            else if (aux[i] > aux[j])
                a[k] = aux[i++];
            else
                a[k] = aux[j++];
        }
    }

    private static void innerSort(int[] a, int[] aux, int lo, int hi) {
        if (lo >= hi) return;
        int mid = (lo + hi) / 2;
        innerSort(a, aux, lo, mid);
        innerSort(a, aux, mid+1, hi);
        if (a[mid] > a[mid+1]) return;
        merge(a, aux, lo, mid, hi);
    }

    public static void mergeSort(int[] input) {
        int[] aux = new int[input.length];
        innerSort(input, aux, 0, input.length-1);
    }


}
