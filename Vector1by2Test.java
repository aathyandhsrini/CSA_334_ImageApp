
public class Vector1by2Test
{
    public static void main(String[] args)
    {
        // Test the vector constructor
        Vector1by2 vector = new Vector1by2(3, 5);

        if (vector.row == 3 && vector.col == 5)
        {
            System.out.println("PASS: Constructor test");
        }
        else
        {
            System.out.println("FAIL: Constructor test");
        }

        // Test the dot product
        Vector1by2 a = new Vector1by2(3, 5);
        Vector1by2 b = new Vector1by2(2, 4);

        double result = Vector1by2.dot(a, b);

        if (result == 26)
        {
            System.out.println("PASS: Dot product test");
        }
        else
        {
            System.out.println("FAIL: Dot product test");
        }
    }
}
