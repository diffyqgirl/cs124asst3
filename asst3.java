import java.io.*;
public class asst3
{
    static final int N = 100;
    public static void main(String[] args) throws IOException
    {
        File infile = new File(args[0]);
        FileReader fr = new FileReader(infile);
        BufferedReader br = new BufferedReader(fr);
        long[] nums = new long[N];
        for (int i = 0; i < N; i++)
        {
            nums[i] = Long.parseLong(br.readLine());
        }
        //long[] test_nums = {7, 10, 5, 6, 8};
        System.out.println(KK(nums));
    }
    public static long sum(long[] nums)
    {
        long sum = 0;
        for (int i = 0; i < nums.length; i++)
        {
            sum += nums[i];
        }
        return sum;
    }
    public static long KK(long[] nums)
    {
        MaxHeap heap = new MaxHeap(nums.length);
        for (int i = 0; i < nums.length; i ++) 
        {
            heap.insert(nums[i], i);
        } 
        //System.out.println("heap is: " + heap);
        HeapElt HE1;
        HeapElt HE2;
        while(true)
        {
            HE1 = heap.deleteMax();
            //System.out.println("HE1: " + HE1);

            HE2 = heap.deleteMax();
            //System.out.println("HE2: " + HE2);
            if (HE2.num == 0)
                return HE1.num;
            heap.insert(HE1.num-HE2.num, HE1.idx);
            heap.insert(0, HE2.idx); 
        }  
    }
}