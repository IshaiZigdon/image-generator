package primitives;

public class Vector extends Point{
    public Vector(Double3 xyz) {
        super(xyz);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot create a Vector with a zero coordinate");
        
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
