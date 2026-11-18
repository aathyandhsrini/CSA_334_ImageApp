/** Two-by-two matrix used on the RIGHT of a row vector [row, col]. */
public class Matrix2by2 {
    private double m00, m01, m10, m11;

    public Matrix2by2() { this(1, 0, 0, 1); }
    public Matrix2by2(double m00, double m01, double m10, double m11) {
        set(m00, m01, m10, m11);
    }
    public Matrix2by2(Matrix2by2 other) {
        this(other.m00, other.m01, other.m10, other.m11);
    }
    public double get00() { return m00; }
    public double get01() { return m01; }
    public double get10() { return m10; }
    public double get11() { return m11; }
    public double get(int row, int col) {
        if (row == 0 && col == 0) return m00;
        if (row == 0 && col == 1) return m01;
        if (row == 1 && col == 0) return m10;
        if (row == 1 && col == 1) return m11;
        throw new IndexOutOfBoundsException("Matrix index must be 0 or 1");
    }
    public void set00(double v) { m00 = v; }
    public void set01(double v) { m01 = v; }
    public void set10(double v) { m10 = v; }
    public void set11(double v) { m11 = v; }
    public void set(int row, int col, double value) {
        if (row == 0 && col == 0) m00 = value;
        else if (row == 0 && col == 1) m01 = value;
        else if (row == 1 && col == 0) m10 = value;
        else if (row == 1 && col == 1) m11 = value;
        else throw new IndexOutOfBoundsException("Matrix index must be 0 or 1");
    }
    public void set(double a, double b, double c, double d) {
        m00 = a; m01 = b; m10 = c; m11 = d;
    }
    /** Set the exact quarter-turn matrix for RIGHT-multiplied [row, col] vectors. */
    public void setRotationMatrix(int degrees) {
        int angle = ((degrees % 360) + 360) % 360;
        if (angle == 0) set(1, 0, 0, 1);
        else if (angle == 90) set(0, 1, -1, 0);  // counterclockwise on a screen
        else if (angle == 180) set(-1, 0, 0, -1);
        else if (angle == 270) set(0, -1, 1, 0); // clockwise on a screen
        else throw new IllegalArgumentException("Angle must be a multiple of 90 degrees");
    }
    public static Matrix2by2 rotation(int degrees) {
        Matrix2by2 result = new Matrix2by2();
        result.setRotationMatrix(degrees);
        return result;
    }
    /** Multiply a row vector on the left by this matrix. */
    public Vector1by2 multiply(Vector1by2 vector) { return multiply(vector, this); }
    public static Vector1by2 multiply(Vector1by2 vector, Matrix2by2 matrix) {
        return new Vector1by2(
            Vector1by2.dot(vector, new Vector1by2(matrix.m00, matrix.m10)),
            Vector1by2.dot(vector, new Vector1by2(matrix.m01, matrix.m11)));
    }
    /** Matrix multiplication, returning a new matrix. */
    public Matrix2by2 multiply(Matrix2by2 other) {
        return new Matrix2by2(
            m00 * other.m00 + m01 * other.m10, m00 * other.m01 + m01 * other.m11,
            m10 * other.m00 + m11 * other.m10, m10 * other.m01 + m11 * other.m11);
    }
    @Override public String toString() {
        return "[[" + m00 + ", " + m01 + "], [" + m10 + ", " + m11 + "]]";
    }
}
