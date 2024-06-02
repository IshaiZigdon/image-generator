package geometries;

import java.util.*;

import primitives.*;

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
    private Geometries() {
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
        if (intersectables.isEmpty())
            return null;
        List<Point> result = new LinkedList<>();
        for (Intersectable i : intersectables) {
            List<Point> d = i.findIntersections(ray);
            if (d != null)
                result.addAll(d);
        }
        return result.isEmpty() ? null : result;
    }
}
