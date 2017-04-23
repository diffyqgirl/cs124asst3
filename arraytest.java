public class arraytest
{
    public static void main(String[] args)
    {
        int[] a = {1, 2, 3};
        int[] b = a;
        b[1] = 7;
        System.out.println(a[1]);
    }
}