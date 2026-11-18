public class Matrix2by2Test
{
    /** Runs the matrix unit tests. */
    public static void main(String[] args)
    {
        // Test the constructor
        Matrix2by2 matrix = new Matrix2by2(1, 2, 3, 4);

        check(matrix.a == 1 && matrix.b == 2 &&
              matrix.c == 3 && matrix.d == 4,
              "Constructor test");

        // Test the getters
        check(matrix.getA() == 1 && matrix.getB() == 2 &&
              matrix.getC() == 3 && matrix.getD() == 4,
              "Getter test");

        // Test the setters
        matrix.setA(5);
        matrix.setB(6);
        matrix.setC(7);
        matrix.setD(8);

        check(matrix.getA() == 5 && matrix.getB() == 6 &&
              matrix.getC() == 7 && matrix.getD() == 8,
              "Setter test");

        // Test matrix addition
        Matrix2by2 m = new Matrix2by2(1, 1, 1, 1);
        matrix.add(m);

        check(matrix.a == 6 && matrix.b == 7 &&
              matrix.c == 8 && matrix.d == 9,
              "Addition test");

        // Test matrix multiplication
        Vector1by2 vector = new Vector1by2(3, 5);
        matrix = new Matrix2by2(1, 2, 3, 4);

        Vector1by2 result = Matrix2by2.multiply(vector, matrix);

        check(result.row == 18 && result.col == 26,
              "Multiplication test");

        // Test instance multiplication
        result = matrix.multiply(vector);

        check(result.row == 18 && result.col == 26,
              "Instance multiplication test");

        // Test 180 degree rotation
        matrix.setRotationMatrix(180);
        result = matrix.multiply(vector);

        check(result.row == -3 && result.col == -5,
              "180 degree rotation test");

        // Test 90 degree rotation
        matrix.setRotationMatrix(90);
        result = matrix.multiply(vector);

        check(result.row == -5 && result.col == 3,
              "90 degree rotation test");

        // Test negative 90 degree rotation
        matrix.setRotationMatrix(-90);
        result = matrix.multiply(vector);

        check(result.row == 5 && result.col == -3,
              "-90 degree rotation test");

        // Test zero degree rotation
        matrix.setRotationMatrix(0);
        result = matrix.multiply(vector);

        check(result.row == 3 && result.col == 5,
              "Zero degree rotation test");

        // Test an invalid angle
        boolean rejected = false;

        try
        {
            matrix.setRotationMatrix(45);
        }
        catch (IllegalArgumentException e)
        {
            rejected = true;
        }

        check(rejected, "Invalid angle test");
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