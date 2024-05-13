package primitives;

/**
 * This class will represent point with 3 coordinates
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class Point {
    /**
     * Coordinates for the point
     */
    protected final Double3 xyz;
    /**
     * Represents the origin point
     */
    public static Point ZERO = new Point(Double3.ZERO);

    /**
     * Constructor to initialize Point based object with its three number values
     *
     * @param x first number value
     * @param y second number value
     * @param z third number value
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructor to initialize Point based object with its
     * numbers value as Double3 object
     *
     * @param xyz the coordinates
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Adds two floating point triads into a new triad where each couple of
     * numbers is added
     *
     * @param v right hand side operand for addition
     * @return result of add
     */
    public Point add(Vector v) {
        return new Point(xyz.add(v.xyz));
    }

    /**
     * Subtract two floating point triads into a new triad where each couple of
     * numbers is subtracted
     *
     * @param p right hand side operand for addition
     * @return vector of the result of substructure
     */
    public Vector subtract(Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }

    /**
     * Calculates the distance squared between 2 points
     *
     * @param p the other point
     * @return distance squared
     */
    public double distanceSquared(Point p) {
        // d^2 = (x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2
        double dx = xyz.d1 - p.xyz.d1;
        double dy = xyz.d2 - p.xyz.d2;
        double dz = xyz.d3 - p.xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }
    /**
     * Calculates the distance between 2 points
     *
     * @param p the other point
     * @return distance
     */
    public double distance(Point p) {
        return Math.sqrt(distanceSquared(p));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Point p && xyz.equals(p.xyz);
    }

    @Override
    public String toString() {
        return "Point: " + xyz;
    }
}
