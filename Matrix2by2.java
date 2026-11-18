public class Matrix2by2
{
    public int a;
    public int b;
    public int c;
    public int d;

    // Constructor
    public Matrix2by2(int a, int b, int c, int d)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    // Multiply a row vector by a 2x2 matrix
    public static Vector1by2 multiply(Vector1by2 v, Matrix2by2 m)
    {
        Vector1by2 col1 = new Vector1by2(m.a, m.c);
        Vector1by2 col2 = new Vector1by2(m.b, m.d);

        int newRow = (int) Vector1by2.dot(v, col1);
        int newCol = (int) Vector1by2.dot(v, col2);

        return new Vector1by2(newRow, newCol);
    }

}