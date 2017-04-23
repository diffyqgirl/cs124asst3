public class HeapElt
{
    long num;
    int idx;
    public HeapElt(long num, int idx)
    {
        this.num = num;
        this.idx = idx;
    }
    public String toString()
    {
        return "(" + num + ", " + idx + ")";
    }

}