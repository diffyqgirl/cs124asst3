import java.util.Arrays;
public class MaxHeap
{
    private long[] heap;
    private int empty; // denotes the first empty location in heap
    public MaxHeap(int N)
    {
        heap = new long[N] ;
        empty = 0;
        for (int i = 0; i < N; i++)
            heap[i] = 0;
    }

    public boolean insert(long l)
    {
        if (empty >= heap.length)
            return false;
        heap[empty] = l;
        int child = empty;
        int parent = parentIdx(child);
        while (parent != -1)
        {
            if (max(child, parent) == child)
            {
                swap(child, parent);
                child = parent;
                parent = parentIdx(parent);
            }
            else
                break;
        }
        empty ++;
        return true;
    }

    public long deleteMax()
    {
        if (empty == 0)
            return -1;
        long HE = heap[0];
        heap[0] = 0;
        int parent = 0 ;
        int child = maxchild(parent);
        while (child != -1)
        {
            if (max(child, parent) == child)
            {
                swap(child, parent);
                parent = child;
                child = maxchild(parent);
            }
            else
                break;
        }
        empty--;
        return HE;
    }
    private int maxchild(int parent)
    {
        int r = rightChildIdx(parent);
        int l = leftChildIdx(parent);
        if (r >= heap.length && l >= heap.length)
            return -1;
        if (r >= heap.length)
            return l;
        if (l >= heap.length)
            return r;
        return max(r, l);
    }
    //returns the maximum of whichever heap elements is at the two valid indices
    private int max(int idx1, int idx2)
    {
        /*
        if (heap[idx1] == 0)
            return idx2;
        if (heap[idx2] == 0)
            return idx1;
            */
        if (heap[idx1] >= heap[idx2])
            return idx1;
        return idx2;
    }
    private void swap(int idx1, int idx2)
    {
        long temp = heap[idx1];
        heap[idx1] = heap[idx2];
        heap[idx2] = temp;
    }

    public boolean isEmpty()
    {
        return this.empty == 0 ;
    }

    private int leftChildIdx(int idx)
    {
        return 2*idx + 1;
    }

    private int rightChildIdx(int idx)
    {
        return 2*idx + 2;
    }

    private int parentIdx(int idx)
    {
        if (idx == 0)
            return -1; // signal that we've reached the root of the heap
        return (idx-1)/2;
    }

    public String toString()
    {
        return Arrays.toString(this.heap);
    }

    public boolean testInvariant()
    {
        for (int i = 0; i < empty; i ++)
        {
            int r = rightChildIdx(i);
            if (r < empty)
                if (heap[i] < heap[r])
                    return false;
            int l = leftChildIdx(i);
            if (l < empty)
                if (heap[i] < heap[l])
                    return false;
        }
        return true;
    }
}