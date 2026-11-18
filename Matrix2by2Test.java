public class Matrix2by2Test
{
    public static void main(String[] args)
    {
        // Test the matrix constructor
        Matrix2by2 matrix = new Matrix2by2(1, 2, 3, 4);

        if (matrix.a == 1 && matrix.b == 2 &&
            matrix.c == 3 && matrix.d == 4)
        {
            System.out.println("PASS: Matrix constructor test");
        }
        else
        {
            System.out.println("FAIL: Matrix constructor test");
        }

        // Test matrix multiplication
        Vector1by2 vector = new Vector1by2(3, 5);
        Matrix2by2 testMatrix = new Matrix2by2(1, 2, 3, 4);

        Vector1by2 result = Matrix2by2.multiply(vector, testMatrix);

        if (result.row == 18 && result.col == 26)
        {
            System.out.println("PASS: Matrix multiplication test");
        }
        else
        {
            System.out.println("FAIL: Matrix multiplication test");
        }
    }
}