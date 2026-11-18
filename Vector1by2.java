/** A row vector of two coordinates (row, column). */
public class Vector1by2 {
    private double row;
    private double col;

    public Vector1by2() { this(0, 0); }
    public Vector1by2(double row, double col) { this.row = row; this.col = col; }
    public Vector1by2(Vector1by2 other) { this(other.row, other.col); }

    public double getRow() { return row; }
    public double getCol() { return col; }
    public double getX() { return row; }
    public double getY() { return col; }
    public void setRow(double row) { this.row = row; }
    public void setCol(double col) { this.col = col; }
    public void setX(double x) { this.row = x; }
    public void setY(double y) { this.col = y; }
    public void set(double row, double col) { this.row = row; this.col = col; }

    /** Add a vector to this vector, returning the result without modifying either input. */
    public Vector1by2 add(Vector1by2 other) { return add(this, other); }
    public static Vector1by2 add(Vector1by2 first, Vector1by2 second) {
        return new Vector1by2(first.row + second.row, first.col + second.col);
    }
    public Vector1by2 subtract(Vector1by2 other) {
        return new Vector1by2(row - other.row, col - other.col);
    }
    public Vector1by2 scale(double factor) { return new Vector1by2(row * factor, col * factor); }

    /** Dot product of two row vectors. */
    public double dot(Vector1by2 other) { return dot(this, other); }
    public static double dot(Vector1by2 a, Vector1by2 b) {
        return a.row * b.row + a.col * b.col;
    }
    @Override public String toString() { return "[" + row + ", " + col + "]"; }
}
