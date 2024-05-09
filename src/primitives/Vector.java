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
        if(x==0 && y==0 && z==0)
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
     *adds algebraically the vectors
     * @param v represents the vector to be added
     * @return new vector with the added coordinates using the father
     * add function
     */
    public Vector add(Vector v) {
       return new Vector(this.xyz.add(v.xyz));
    }
    /***/
    public Vector scale(double scale) {
        return new Vector(this.xyz.scale(scale));
    }
    /***/
    public double dotProduct(Vector v) {
        return (xyz.d1*v.xyz.d1+xyz.d2*v.xyz.d2+xyz.d3*v.xyz.d3);
    }
    /***/
    public Vector crossProduct(Vector v) {
        return new Vector((xyz.d2*v.xyz.d3-xyz.d3*v.xyz.d2),(xyz.d3*v.xyz.d1-xyz.d1*v.xyz.d3),(xyz.d1*v.xyz.d2-xyz.d2*v.xyz.d1));
    }
    /***/
    public double lengthSquared(){
        return xyz.d1*xyz.d1+xyz.d2*xyz.d2+xyz.d3*xyz.d3;
    }
    /***/
    public double length(){
        return Math.sqrt(lengthSquared());
    }
    /***/
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
