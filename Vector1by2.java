public class Vector1by2
{
    public int row;
    public int col;

    /** Constructor */
    public Vector1by2(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    /** Gets the row value. */
    public int getRow()
    {
        return row;
    }

    /** Gets the column value. */
    public int getCol()
    {
        return col;
    }

    /** Sets the row value. */
    public void setRow(int row)
    {
        this.row = row;
    }

    /** Sets the column value. */
    public void setCol(int col)
    {
        this.col = col;
    }

    /** Adds another vector. */
    public Vector1by2 add(Vector1by2 v)
    {
        row = row + v.row;
        col = col + v.col;
        return this;
    }

    /** Calculates the dot product. */
    public static double dot(Vector1by2 a, Vector1by2 b)
    {
        return a.row * b.row + a.col * b.col;
    }

    /** Calculates the dot product with another vector. */
    public double dot(Vector1by2 v)
    {
        return dot(this, v);
    }
}