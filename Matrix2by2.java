public class Matrix2by2
{
    public int a;
    public int b;
    public int c;
    public int d;

    /** Constructs a 2x2 matrix. */
    public Matrix2by2(int a, int b, int c, int d)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /** Gets the matrix values. */
    public int getA()
    {
        return a;
    }

    public int getB()
    {
        return b;
    }

    public int getC()
    {
        return c;
    }

    public int getD()
    {
        return d;
    }

    /** Sets the matrix values. */
    public void setA(int a)
    {
        this.a = a;
    }

    public void setB(int b)
    {
        this.b = b;
    }

    public void setC(int c)
    {
        this.c = c;
    }

    public void setD(int d)
    {
        this.d = d;
    }

    /** Adds another matrix to this matrix. */
    public Matrix2by2 add(Matrix2by2 m)
    {
        a = a + m.a;
        b = b + m.b;
        c = c + m.c;
        d = d + m.d;

        return this;
    }

    /** Sets a rotation matrix using an angle in degrees. */
    public void setRotationMatrix(int angle)
    {
        if (angle % 90 != 0)
        {
            throw new IllegalArgumentException(
                "Angle must be a multiple of 90");
        }

        double radians = Math.toRadians(angle);

        a = (int) Math.round(Math.cos(radians));
        b = (int) Math.round(Math.sin(radians));
        c = (int) Math.round(-Math.sin(radians));
        d = (int) Math.round(Math.cos(radians));
    }

    /** Multiplies a row vector by a matrix. */
    public static Vector1by2 multiply(Vector1by2 v, Matrix2by2 m)
    {
        Vector1by2 col1 = new Vector1by2(m.a, m.c);
        Vector1by2 col2 = new Vector1by2(m.b, m.d);

        int newRow = (int) Vector1by2.dot(v, col1);
        int newCol = (int) Vector1by2.dot(v, col2);

        return new Vector1by2(newRow, newCol);
    }

    /** Multiplies a vector using this matrix. */
    public Vector1by2 multiply(Vector1by2 v)
    {
        return multiply(v, this);
    }
}