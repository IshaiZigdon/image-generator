package geometries;

import primitives.*;

/**
 * this interface will serve all geometry shapes in the program
 * 2D and 3D
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public interface Geometry {
    /**
     * this function calculates the
     * vertical (normal) vector for the various shapes at a point
     * on the surface of the shape
     *
     * @param p a point on the shape
     * @return a vertical vector for the shape
     */
    public Vector getNormal(Point p);

}
