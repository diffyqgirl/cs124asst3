public class asst3
{
    static final int N = 100;
    public static void main(String[] args)
    {
        File infile = new File(args[0]);
        FileReader fr = new FileReader(infile);
        BufferedReader br = new BufferedReader(fr);
        long[] nums = new long[N];
        for (int i = 0; i < N; i++)
        {
            nums[i] = Long.parseLong(br.readLine());
        }
    }
}