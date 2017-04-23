import java.util.Arrays;
public class heaptest
{
    public static void main(String[] args)
    {
        int N = 10;
        MaxHeap heap = new MaxHeap(N);
        long[] elts = new long[N];
        for (int i = 0; i < N; i++)
        {
            elts[i] = (long) (Math.random() * N);
            heap.insert(elts[i], i);
        }
        System.out.println("Elts are: " + Arrays.toString(elts));
        System.out.println("heap is" + heap);
    }
}