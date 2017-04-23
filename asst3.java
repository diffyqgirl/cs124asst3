import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
public class asst3
{
    static final int N = 100;
    public static void main(String[] args) throws IOException
    {
        //System.out.println(Arrays.toString(args));
        int max_iter = 25000;//25000
        File infile = new File(args[0]);
        FileReader fr = new FileReader(infile);
        BufferedReader br = new BufferedReader(fr);
        long[] nums = new long[N];
        for (int i = 0; i < N; i++)
        {
            nums[i] = Long.parseLong(br.readLine());
        }
        //long[] test_nums = {7, 10, 5, 6, 8};
        if (args[1].equals("kk"))
            System.out.println(KK(nums));
        else if (args[1].equals("rr"))
            System.out.println(rr(nums, max_iter));
        else if (args[1].equals("hc"))
            System.out.println(hc(nums, max_iter));
        else if (args[1].equals("sa"))
            System.out.println(sa(nums, max_iter));
        else if (args[1].equals("rr_reg"))
            System.out.println(rr_reg(nums, max_iter));
        else if (args[1].equals("hc_reg"))
            System.out.println(hc_reg(nums, max_iter));
        else if (args[1].equals("sa_reg"))
            System.out.println(sa_reg(nums, max_iter));
    }

    public static long sa_reg(long[] nums, int max_iter)
    {
        int N = nums.length;
        int[] s = new int[N];
        int[] s1 = new int[N];
        int[] s2 = new int[N];
        long s_res;
        long s1_res;
        long s2_res;
        // we start with a random solution
        for (int j = 0; j < N; j++)
        {
            if (Math.random() > 0.5)
                s[j] = 1;
            else
                s[j] = -1;
        }
        s_res = residue(nums,s);
        s2_res = s_res;
        System.arraycopy(s, 0, s2, 0, N); //s2 = s
        for (int i = 1; i < max_iter; i++)
        {
            // s1 is a random neighbor of s
            System.arraycopy(s, 0, s1, 0, N); // start with s1=s
            int idx1 = (int) Math.random() * N;
            s1[idx1] *= -1;
            s1_res = residue(nums, s1);
            if (s1_res < s_res)
            {
                s_res = s1_res;
                System.arraycopy(s1, 0, s, 0, N); // s = s1
            }
            else
            {
                double prob = Math.exp(-(s1_res - s_res)/ T(i));
                if (Math.random() < prob)
                {
                    //s = s1
                    System.arraycopy(s1, 0, s, 0, N); 
                    s_res = s1_res;
                } 
            }
            if (s_res < s2_res)
            {
                s2_res = s_res;
                System.arraycopy(s, 0, s2, 0, N); //s2 = s
            }
        }
        return s2_res;
    }

    public static long hc_reg(long[] nums, int max_iter)
    {
        int N = nums.length;
        int[] signs = new int[N];
        //int[] signs_temp = new int[N];
        long s_res;
        long temp_res;
        for (int j = 0; j < N; j++)
        {
            if (Math.random() > 0.5)
                signs[j] = 1;
            else
                signs[j] = -1;
        }
        s_res = residue(nums, signs); 
        for (int i = 1; i < max_iter; i++)
        {
            int idx1 = (int) Math.random()*N;
            signs[idx1] *= -1;
            temp_res = residue(nums, signs);
            if (temp_res < s_res)
                s_res = temp_res;
            else
                signs[idx1] *= -1; //reset
        }
        return s_res;
    }

    public static long rr_reg(long[] nums, int max_iter)
    {
        int N = nums.length;
        int[] signs = new int[N];
        long s_res;
        long temp_res;
        for (int j = 0; j < N; j++)
        {
            if (Math.random() >0.5)
                signs[j] = 1;
            else
                signs[j] = -1;
        }
        s_res = residue(nums, signs);
        for (int i = 1; i < max_iter; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if (Math.random() >0.5)
                    signs[j] = 1;
                else
                    signs[j] = -1;
            }
            temp_res = residue(nums, signs);
            if (temp_res < s_res)
                s_res = temp_res;
        }
        return s_res;
    }

    public static long residue(long[] nums, int[] signs)
    {
        long pos_sum = 0;
        long neg_sum = 0;
        for (int i = 0; i < nums.length; i ++)
        {
            assert(signs[i] == 1 || signs[i] == -1);
            if (signs[i]==1)
                pos_sum += nums[i];
            else
                neg_sum += nums[i];
        }
        return Math.abs(pos_sum - neg_sum);

    }

    public static long sa(long[] nums, int max_iter)
    {
        int N = nums.length;
        int[] s = new int[N];
        int[] s1 = new int[N];
        int[] s2 = new int[N];
        long s_res;
        long s1_res;
        long s2_res;
        long[] nums_temp = new long[N];

        //generate starting point solution 
        for (int j = 0; j < N; j++)
            s[j] = (int) (Math.random()*N);
        //calculate residue of s
        for (int j = 0; j < N; j++)
            nums_temp[s[j]] += nums[j];
        s_res = KK(nums_temp);

        //s2 = s
        s2_res = s_res;
        System.arraycopy(s, 0, s2, 0, N);

        for (int i = 1; i < max_iter; i++)
        {
            //S' = random neighbor of S
            System.arraycopy(s, 0, s1, 0, N); //copy s into s1
            int idx1;
            int num2;
            do
            {
                idx1 = (int) (Math.random()*N);
                num2 = (int) (Math.random()*N);
            } while (s1[idx1] == num2);
            s1[idx1] = num2;
            //clear out nums_temp
            Arrays.fill(nums_temp, 0);
            //calculate s1_res
            for (int j = 0; j < N; j++)
                nums_temp[s1[j]] += nums[j];
            s1_res = KK(nums_temp);
            if (s1_res < s_res)
            {
                //s = s1
                System.arraycopy(s1, 0, s, 0, N);
                s_res = s1_res;
            }
            else
            {
                double prob = Math.exp(-(s1_res - s_res)/ T(i));
                if (Math.random() < prob)
                {
                    //s = s1
                    System.arraycopy(s1, 0, s, 0, N);
                    s_res = s1_res;
                }

            }
            if (s_res < s2_res)
            {
                //s2 = s
                System.arraycopy(s, 0, s2, 0, N);
                s2_res = s_res;
            }
        } // end outer for
        return s2_res;
    }

    /*
    //simulated annealing
    public static long sa(long[] nums, int max_iter)
    {
        int N = nums.length;
        int[] s = new int[N];
        int[] s1 = new int[N];
        int[] s2 = new int[N];
        long s_res;
        long s1_res;
        long s2_res;
        long[] nums_temp = new long[N];
        //get starting point solution
        for (int i = 0; i < N; i++)
            s[i] = (int) Math.random()*N;
        for (int i = 0; i < N; i++)
            nums_temp[s[i]] += nums[i];
        s_res = KK(nums_temp);

        //s2 = s
        System.arraycopy(s, 0, s2, 0, N);
        s2_res = s_res;

        for (int i = 1; i < max_iter; i++)
        {
            //generate a random move
            int idx1;
            int num2;
            do
            {
                idx1 = (int) (Math.random()*N);
                num2 = (int) (Math.random()*N);
            } while (s[idx1] == num2);
            int num1 = s[idx1];
            System.arraycopy(s, 0, s1, 0, N); // s1 = s
            s1[idx1] = num2; // actually make our random move
            
            //move from s to s1
            nums_temp[num1] -= nums[idx1];
            nums_temp[num2] += nums[idx1];
            s1_res = KK(nums_temp);
            if (s1_res < s_res)
            {
                System.arraycopy(s1, 0, s, 0, N); //s = s1
                s_res = s1_res;
            }
            else
            {
                double prob = Math.exp(-(s1_res - s_res)/ T(i));
                if (Math.random() < prob)
                {
                    //s = s1
                    System.arraycopy(s1, 0, s, 0, N); 
                    s_res = s1_res;
                }
            }
            if (s_res < s2_res)
            {
                //s2 = s
                System.arraycopy(s, 0, s2, 0, N);
                s2_res = s_res;
            }
        }
        return s2_res;
    }
    */

    public static double T(int i)
    {
        return Math.pow(10, 10)*Math.pow(0.8, (int) i/300);
    }

    //attempt 2
    public static long hc(long[] nums, int max_iter)
    {
        int N = nums.length;
        int[] s = new int[N];
        int[] s1 = new int[N];
        long s_res;
        long s1_res;
        long[] nums_temp = new long[N];       

        //start with random solution s
        for (int j = 0; j < N; j++)
        {
            s[j] = (int) (Math.random()*N);
        }
        //calculate residue
        Arrays.fill(nums_temp, 0);
        for (int j = 0; j < N; j++)
        {
            nums_temp[s[j]] += nums[j];
        }
        s_res = KK(nums_temp);

        for (int i = 1; i < max_iter; i++)
        {
            //s1 is a random neighbor of s
            System.arraycopy(s, 0, s1, 0, N); //s1 = s
            int idx1;
            int num2;
            do
            {
                idx1 = (int) (Math.random()*N);
                num2 = (int) (Math.random()*N);
            } while (s1[idx1] == num2);
            //calculate s1_res
            Arrays.fill(nums_temp, 0);
            for (int j = 0; j < N; j++)
            {
                nums_temp[s1[j]] += nums[j];
            }
            s1_res = KK(nums_temp);
            if (s1_res < s_res)
            {
                //s = s1
                System.arraycopy(s1, 0, s, 0, N);
                s_res = s1_res;
            }
        }
        return s_res;
    }

    /*
    //hill climbing
    public static long hc(long[] nums, int max_iter)
    {
        int N = nums.length;
        long s_res;
        int[] temp = new int[N];
        long temp_res;
        long[] nums_temp = new long[N];
        Arrays.fill(nums_temp, 0);
        for (int j = 0; j < N; j++)
            temp[j] = (int) (Math.random()*N);
        for (int j = 0; j < N; j++)
        {
            nums_temp[temp[j]] += nums[j];
        }
        s_res = KK(nums_temp); // initial value
        for (int i = 1; i < max_iter; i ++)
        {
            //generate a random move
            int idx1;
            int num2;
            do
            {
                idx1 = (int) (Math.random()*N);
                num2 = (int) (Math.random()*N);
            } while (temp[idx1] == num2);
            //int num1 = temp[idx1];
            temp[idx1] = num2; // actually make our random move
          
            
            
            Arrays.fill(nums_temp, 0);           
            for (int j = 0; j < N; j++)
            {
                nums_temp[temp[j]] += nums[j];
            }
            
            
            
            temp_res = KK(nums_temp);
            if (temp_res < s_res)
            {
                s_res = temp_res;
            }
        }
        return s_res;
    }
    */
    //repeated random
    public static long rr(long[] nums, int max_iter)
    {
        int N = nums.length;
        int[] s = new int[N];//best randomly generated permutation so far
        long s_res; //the corresponding residue
        int[] temp = new int[N]; //our next randomly generated permutation
        long temp_res;
        long[] nums_temp = new long[N]; //used for running kk on our permutation temp
        Arrays.fill(nums_temp, 0);
        for (int j = 0; j < N; j++)
            s[j] = (int) (Math.random()*N);
        for (int j = 0; j < N; j++)
        {
            nums_temp[s[j]] += nums[j];
        }
        s_res = KK(nums_temp);
        for (int i = 1; i < max_iter; i++)
        {
            Arrays.fill(nums_temp, 0);
            for (int j = 0; j < N; j++)
            {
                temp[j] = (int) (Math.random()*N);
            }
            for (int j = 0; j < N; j++)
            {
                nums_temp[temp[j]] += nums[j];
            }
            temp_res = KK(nums_temp);
            if (temp_res < s_res)
            {
                s_res = temp_res;
                s = temp;
            }
        }
        return s_res;
    }
    /*
    public static long sum(long[] nums)
    {
        long sum = 0;
        for (int i = 0; i < nums.length; i++)
        {
            sum += nums[i];
        }
        return sum;
    }
    */
    public static long KK(long[] nums)
    {
        PriorityQueue<Long> altheap = new PriorityQueue<Long>(100, Collections.reverseOrder());
        MaxHeap heap = new MaxHeap(nums.length);
        for (int i = 0; i < nums.length; i ++) 
        {
            heap.insert(nums[i]);
            altheap.add(nums[i]);
        } 
        //System.out.println("heap is: " + heap);
        long HE1;
        long HE2;
        long l1;
        long l2;
        while(true)
        {
            //HE1 = heap.deleteMax();
            //System.out.println("HE1: " + HE1);
            l1 = altheap.poll();
            //HE2 = heap.deleteMax();
            l2 = altheap.poll();
            if (l2 == 0)
                return l1;
            //System.out.println("HE2: " + HE2);
                /*
            if (HE2 == 0)
                return HE1;
                */
                altheap.add(l1-l2);
                altheap.add((long) 0);
                /*
            heap.insert(HE1-HE2);
            heap.insert(0);
            */ 
        }  
    }
}