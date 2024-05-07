package primitives;

public class Vector extends Point{
    public Vector(Double3 xyz) {
        super(xyz);
    }
    @Override
    public final String toString() {
    return this.xyz.toString();
    }
    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof Vector) {
            Vector v = (Vector) obj;
            return this.xyz.equals(v.xyz);
        }
        return false;
    }
}
