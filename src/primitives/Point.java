package primitives;

public class Point {
    protected Double3 xyz;
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point p = (Point) obj;
            return xyz.equals(p.xyz);
        }
        return false;
    }
    @Override
    public String toString() {
        return "x,y,z=" + xyz;
    }

}
