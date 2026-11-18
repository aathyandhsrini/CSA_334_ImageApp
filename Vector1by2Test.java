public class Vector1by2Test
{
    /** Runs the vector unit tests. */
    public static void main(String[] args)
    {
        // Test the constructor
        Vector1by2 vector = new Vector1by2(3, 5);

        check(vector.row == 3 && vector.col == 5,
              "Constructor test");

        // Test the getters
        check(vector.getRow() == 3 && vector.getCol() == 5,
              "Getter test");

        // Test the setters
        vector.setRow(7);
        vector.setCol(9);

        check(vector.getRow() == 7 && vector.getCol() == 9,
              "Setter test");

        // Test vector addition
        Vector1by2 a = new Vector1by2(3, 5);
        Vector1by2 b = new Vector1by2(2, 4);

        Vector1by2 sum = a.add(b);

        check(sum.getRow() == 5 && sum.getCol() == 9,
              "Addition test");

        // Test the dot product
        a = new Vector1by2(3, 5);

        check(Vector1by2.dot(a, b) == 26,
              "Dot product test");

        // Test the instance dot product
        check(a.dot(b) == 26,
              "Instance dot product test");

        // Test negative values
        Vector1by2 c = new Vector1by2(-3, 5);

        check(c.dot(b) == 14,
              "Negative vector test");

        // Test a zero vector
        Vector1by2 zero = new Vector1by2(0, 0);

        check(zero.dot(b) == 0,
              "Zero vector test");
    }

    /** Prints whether a test passed or failed. */
    public static void check(boolean result, String name)
    {
        if (result)
        {
            System.out.println("PASS: " + name);
        }
        else
        {
            System.out.println("FAIL: " + name);
        }
    }
}