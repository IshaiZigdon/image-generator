package primitives;
/**
 * This class will represent vector, inherits from point
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class Vector extends Point{
    /**
     * constructor to initialize vector with 3 number values
     * @param x first number value
     * @param y second number value
     * @param z third number value
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot create a Vector with a zero coordinate");
    }
    /**
     * constructor to initialize vector with Double3 object
     * @param xyz the coordinates
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot create a Vector with a zero coordinate");
    }
    /**
     * adds algebraically the vectors
     * @param v represents the vector to be added
     * @return new vector with the added coordinates using the father
     * add function
     */
    public Vector add(Vector v) {
       return new Vector(this.xyz.add(v.xyz));
    }
    /***
     * multiplying by a scale number
     * @param scale - the number to multiply with
     * @return new vector that is multiplied with the scale
     */
    public Vector scale(double scale) {
        return new Vector(this.xyz.scale(scale));
    }
    /**
     * multiply vector with another vector
     * @param v the vector to multiply with
     */
    public double dotProduct(Vector v) {
        return (xyz.d1*v.xyz.d1+xyz.d2*v.xyz.d2+xyz.d3*v.xyz.d3);
    }
    /***
     * doing cross-product multiplication
     * @param v the vector to make the calculations with
     * @return new vector that is vertical to both of the vectors
     */
    public Vector crossProduct(Vector v) {
        return new Vector((xyz.d2*v.xyz.d3-xyz.d3*v.xyz.d2),(xyz.d3*v.xyz.d1-xyz.d1*v.xyz.d3),(xyz.d1*v.xyz.d2-xyz.d2*v.xyz.d1));
    }
    /**
     * calculates the length of a vector squared
     * @return the squared length of the vector
     */
    public double lengthSquared(){
        return xyz.d1*xyz.d1+xyz.d2*xyz.d2+xyz.d3*xyz.d3;
    }
    /**
     * calculates the length of the vector
     * @return the length of the vector
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }
    /**
     * normalizes the current vector
     * @return the new normalized vector
     */
    public Vector normalize() {
        return scale(1/length());
    }
    @Override
    public final String toString() {
    return this.xyz.toString();
    }
    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof Vector) {
            return super.equals(obj);
        }
        return false;
    }
}
