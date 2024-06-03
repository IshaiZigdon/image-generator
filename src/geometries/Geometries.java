package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * this class is for all the geometries that can intersect with a ray ,altogether
 */
public class Geometries implements Intersectable {
    /**
     * the list that will hold what geometries are inside
     */
    private final List<Intersectable> intersectables = new LinkedList<>();

    /**
     * empty constructor for now
     */
    public Geometries() {
    }

    /**
     * a constructor that get some geometries that are on the field and adds them
     *
     * @param geometries a group of unknown size geometries
     */
    public Geometries(Intersectable... geometries) {
        if (geometries != null)
            this.add(geometries);
    }

    /**
     * the function that adds the geometries given
     *
     * @param geometries a group of unknown size geometries
     */
    public void add(Intersectable... geometries) {
        intersectables.addAll(Arrays.asList(geometries));
    }

    /**
     * calculates where are the intersecting points of the
     * shape with the given ray
     *
     * @param ray the ray
     * @return list of intersecting points
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = null;
        for (Intersectable i : intersectables) {
            List<Point> points = i.findIntersections(ray);
            if (points != null) {
                if (result == null)
                    result = new LinkedList<>(points);
                else
                    result.addAll(points);
            }
        }
        return result;
    }
}
