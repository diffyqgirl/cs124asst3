import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
public class gen_infile
{
    public static void main(String[] args) throws IOException
    {
        int files = Integer.parseInt(args[0]);
        int N = 100;
        long min = 1;
        long max = (long) Math.pow(10, 12);
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int j = 0; j < files; j++)
        {
            String s = "testnums/nums";
            File f = new File(s + j + ".txt");
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (int i = 0; i < N; i++)
            {
                long l = r.nextLong(max) + min;
                pw.println(l); 
            }
            pw.close();
        }
    }
}