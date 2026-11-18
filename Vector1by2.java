
public class Vector1by2
{
    public int row;
    public int col;

    // Constructor
    public Vector1by2(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    // Find the dot product of two vectors
    public static double dot(Vector1by2 a, Vector1by2 b)
    {
        return a.row * b.row + a.col * b.col;
    }
}
