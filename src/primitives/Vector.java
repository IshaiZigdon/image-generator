package primitives;

/**
 * This class will represent vector, inherits from point
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class Vector extends Point {
    /**
     * the x-axis
     */
    public static final Vector X = new Vector(1, 0, 0);
    /**
     * the y-axis
     */
    public static final Vector Y = new Vector(0, 1, 0);
    /**
     * opposite to the y-axis
     */
    public static final Vector MINUS_Y = new Vector(0, -1, 0);
    /**
     * the z-axis
     */
    public static final Vector Z = new Vector(0, 0, 1);
    /**
     * opposite to the z-axis
     */
    public static final Vector MINUS_Z = new Vector(0, 0, -1);

    /**
     * constructor to initialize vector with 3 number values
     *
     * @param x first number value
     * @param y second number value
     * @param z third number value
     * @throws IllegalArgumentException if x,y,z values are 0
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot create a Vector with a zero coordinate");
    }

    /**
     * constructor to initialize vector with Double3 object
     *
     * @param xyz the coordinates
     * @throws IllegalArgumentException if all xyz values are 0
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot create a Vector with a zero coordinate");
    }

    /**
     * adds algebraically the vectors
     *
     * @param v represents the vector to be added
     * @return new vector with the added coordinates using the father
     * add function
     */
    public Vector add(Vector v) {
        return new Vector(xyz.add(v.xyz));
    }

    /**
     * multiplying by a scale number
     *
     * @param scale - the number to multiply with
     * @return new vector that is multiplied with the scale
     */
    public Vector scale(double scale) {
        return new Vector(xyz.scale(scale));
    }

    /**
     * multiply vector with another vector
     *
     * @param v the vector to multiply with
     * @return the result
     */
    public double dotProduct(Vector v) {
        return (xyz.d1 * v.xyz.d1 + xyz.d2 * v.xyz.d2 + xyz.d3 * v.xyz.d3);
    }

    /**
     * doing cross-product multiplication
     *
     * @param v the vector to make the calculations with
     * @return new vector that is vertical to both of the vectors
     */
    public Vector crossProduct(Vector v) {
        return new Vector((xyz.d2 * v.xyz.d3 - xyz.d3 * v.xyz.d2),
                (xyz.d3 * v.xyz.d1 - xyz.d1 * v.xyz.d3),
                (xyz.d1 * v.xyz.d2 - xyz.d2 * v.xyz.d1));
    }

    /**
     * calculates the length of a vector squared
     *
     * @return the squared length of the vector
     */
    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    /**
     * calculates the length of the vector
     *
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * normalizes the current vector
     *
     * @return the new normalized vector
     */
    public Vector normalize() {
        return scale(1 / length());
    }

    /**
     * method to generate a vertical vector
     *
     * @return a vertical vector
     */
    public Vector verticalVector() {
        double x, y, z;

        if (xyz.d3 == 0) {
            // If a3 (z component) is zero, choose x and calculate y accordingly
            x = 1;  // Arbitrary value for x
            if (xyz.d2 == 0) return Vector.Y;
            y = -(xyz.d1 * x) / xyz.d2;
            z = 0;  // Set z to 0
        } else {
            // If a3 is not zero, choose x = 0 and y = 1, then calculate z
            x = 0;  // Set x to 0
            y = 1;  // Arbitrary value for y
            z = -(xyz.d2 * y) / xyz.d3;
        }
        return new Vector(x, y, z).normalize();
    }

    @Override
    public final String toString() {
        return "Vector: " + xyz;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector && super.equals(obj);
    }
}
