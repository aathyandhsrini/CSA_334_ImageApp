/** Simple black-box tests for the Vector1by2 and Matrix2by2 classes. */
public class ImageAppTests {
    private static int passed = 0;

    /** Check that an actual number matches an expected number. */
    private static void check(double expected, double actual, String testName) {
        if (Math.abs(expected - actual) > 0.000001) {
            throw new AssertionError(testName + ": expected " + expected + ", got " + actual);
        }
        passed++;
        System.out.println("PASS: " + testName);
    }

    public static void main(String[] args) {
        // Test 1: Vector constructors, getters, setters, and addition.
        Vector1by2 a = new Vector1by2(2, 3);
        check(2, a.getRow(), "Vector constructor/getRow");
        check(3, a.getCol(), "Vector constructor/getCol");
        a.setRow(4);
        a.setCol(5);
        check(4, a.getRow(), "Vector setRow");
        check(5, a.getCol(), "Vector setCol");
        Vector1by2 sum = a.add(new Vector1by2(1, -2));
        check(5, sum.getRow(), "Vector add row");
        check(3, sum.getCol(), "Vector add col");
        check(0, new Vector1by2().getRow(), "Vector default constructor");

        // Test 2: Dot product, including negative and zero values.
        check(23, Vector1by2.dot(new Vector1by2(2, 3),
                                      new Vector1by2(4, 5)), "Dot product");
        check(-14, Vector1by2.dot(new Vector1by2(2, -3),
                                       new Vector1by2(5, 8)), "Dot with negatives");
        check(0, Vector1by2.dot(new Vector1by2(0, 0), a), "Dot with zero");

        // Test 3: Matrix constructors, getters, setters, and rotation setup.
        Matrix2by2 m = new Matrix2by2(1, 2, 3, 4);
        check(3, m.get(1, 0), "Matrix constructor/get");
        m.set(1, 0, 5);
        check(5, m.get(1, 0), "Matrix set");
        check(1, new Matrix2by2().get00(), "Matrix default constructor");
        m.setRotationMatrix(90);
        check(0, m.get00(), "90-degree rotation matrix [0][0]");
        check(-1, m.get10(), "90-degree rotation matrix [1][0]");

        // Test 4: Vector-matrix multiplication and rotations.
        m = new Matrix2by2(1, 2, 3, 4);
        Vector1by2 result = Matrix2by2.multiply(new Vector1by2(2, 3), m);
        check(11, result.getRow(), "Matrix multiply row");
        check(16, result.getCol(), "Matrix multiply col");

        m.setRotationMatrix(90);
        result = Matrix2by2.multiply(new Vector1by2(2, 3), m);
        check(-3, result.getRow(), "90-degree rotation row");
        check(2, result.getCol(), "90-degree rotation col");

        m.setRotationMatrix(180);
        result = Matrix2by2.multiply(new Vector1by2(2, 3), m);
        check(-2, result.getRow(), "180-degree rotation row");
        check(-3, result.getCol(), "180-degree rotation col");

        m.setRotationMatrix(-90);
        result = Matrix2by2.multiply(new Vector1by2(2, 3), m);
        check(3, result.getRow(), "Clockwise rotation row");
        check(-2, result.getCol(), "Clockwise rotation col");

        System.out.println("\nAll " + passed + " tests passed");
    }
}
