package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * class for intersecting points of all the shapes
 * with given ray
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public interface Intersectable {
    /**
     * calculates where are the intersecting points of the
     * shape with the given ray
     *
     * @param ray the ray
     * @return list of intersecting points
     */
    List<Point> findIntersections(Ray ray);
}
